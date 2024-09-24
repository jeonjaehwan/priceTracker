package com.pricewatcher.price_alert_service.repository;

import com.pricewatcher.common_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
