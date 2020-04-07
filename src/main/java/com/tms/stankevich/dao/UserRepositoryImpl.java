package com.tms.stankevich.dao;

import com.tms.stankevich.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public class UserRepositoryImpl implements UserRepository {

    @Autowired
    @Lazy
    UserRepositoryJpa userRepository;

    @Override
    public List<User> findAllUsersByRole(String role) {
        return null;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return findById(userId);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user) {

    }
}
