package com.pricewatcher.main_service.service;

import com.pricewatcher.common_service.dto.UserReq;
import com.pricewatcher.common_service.dto.UserRes;
import com.pricewatcher.common_service.entity.User;
import com.pricewatcher.main_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public UserRes createUser(UserReq userReq) {
        userReq.setPassword(encoder.encode(userReq.getPassword()));
        User user = User.from(userReq);
        userRepository.save(user);

        return UserRes.from(user);
    }
}
