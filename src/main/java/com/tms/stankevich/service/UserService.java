package com.tms.stankevich.service;


import com.tms.stankevich.domain.movie.Movie;
import com.tms.stankevich.domain.movie.Seance;
import com.tms.stankevich.domain.user.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User findUserById(Long userId);

    List<User> getAllUsers();

    boolean saveUser(User user);

    List<User> getUsersByRole(String roleName);

    void updateAdminRole(Long userId, String action);
}
