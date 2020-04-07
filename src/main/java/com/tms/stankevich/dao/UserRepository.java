package com.tms.stankevich.dao;

import com.tms.stankevich.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAllUsersByRole(String role);

    User findByUsername(String username);

    Optional<User> findById(Long userId);

    List<User> findAll();

    void save(User user);
}
