package com.pricewatcher.price_alert_service.service;

import com.pricewatcher.common_service.entity.User;
import com.pricewatcher.common_service.exception.UserNotFoundException;
import com.pricewatcher.price_alert_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public String getUserPhoneNumber(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return user.getPhoneNumber();
    }

}
