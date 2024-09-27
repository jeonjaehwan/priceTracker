package com.pricewatcher.main_service.service;

import com.pricewatcher.common_service.dto.UserReq;
import com.pricewatcher.common_service.dto.UserRes;
import com.pricewatcher.common_service.entity.User;
import com.pricewatcher.common_service.exception.SmsCertificationNumberMismatchException;
import com.pricewatcher.common_service.exception.UserNotFoundException;
import com.pricewatcher.main_service.config.SmsUtil;
import com.pricewatcher.main_service.dto.CheckCodeReq;
import com.pricewatcher.main_service.dto.VerifyCodeReq;
import com.pricewatcher.main_service.repository.SmsCertificationDao;
import com.pricewatcher.main_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final SmsUtil smsUtil;
    private final SmsCertificationDao smsCertificationDao;

    @Transactional
    public UserRes createUser(UserReq userReq) {
        userReq.setPassword(encoder.encode(userReq.getPassword()));
        User user = User.from(userReq);
        userRepository.save(user);

        return UserRes.from(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        //user 삭제
        userRepository.delete(user);
    }

    @Transactional
    public void sendSmsToFindEmail(VerifyCodeReq verifyCodeReq) {
        String name = verifyCodeReq.getName();
        String phoneNum = verifyCodeReq.getPhoneNumber().replaceAll("-", "");

        userRepository.findByNameAndPhoneNumber(name, phoneNum)
                .orElseThrow(() -> new UserNotFoundException("회원이 존재하지 않습니다."));

        String verificationCode = UUID.randomUUID().toString().substring(0, 6); // 무작위 인증 코드 생성
        log.info("verificationCode={}", verificationCode);
        smsUtil.sendOne(phoneNum, verificationCode);
        //생성된 인증번호를 Redis에 저장
        smsCertificationDao.createSmsCertification(phoneNum,verificationCode);
    }

    public String getUsername(String name, String phoneNumber) {
        User user = userRepository.findByNameAndPhoneNumber(name, phoneNumber)
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));

        return user.getUsername();
    }

    public UserRes findUserForPasswordReset(String name, String username, String email) {
        User user = userRepository.findByNameAndUsernameAndEmail(name, username, email)
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));

        return UserRes.from(user);

    }

    public void verifySms(CheckCodeReq checkCodeReq) {
        if (!isVerify(checkCodeReq)) {
            throw new SmsCertificationNumberMismatchException("인증번호가 일치하지 않습니다.");
        } else {
            smsCertificationDao.removeSmsCertification(checkCodeReq.getPhoneNumber());
        }
    }

    private boolean isVerify(CheckCodeReq findUsernameReq) {
        return smsCertificationDao.hasKey(findUsernameReq.getPhoneNumber()) &&
                smsCertificationDao.getSmsCertification(findUsernameReq.getPhoneNumber())
                        .equals(findUsernameReq.getCertificationNumber());
    }

    @Transactional
    public void updatePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.updatePassword(encoder.encode(newPassword));
    }
}
