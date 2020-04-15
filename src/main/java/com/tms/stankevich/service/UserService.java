package com.tms.stankevich.service;


import com.tms.stankevich.domain.user.User;

import java.util.List;

public interface UserService {
    User findUserById(Long userId);

    List<User> getAllUsers();

    boolean saveUser(User user);

    List<User> getUsersByRole(String roleName);

    void updateAdminRole(Long userId, String action);
}
