package com.tms.stankevich.service;


import com.tms.stankevich.domain.user.FriendRequest;
import com.tms.stankevich.domain.user.User;
import com.tms.stankevich.exception.FriendRequestException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User findUserById(Long userId);

    List<User> getAllUsers();

    boolean saveUser(User user);

    List<User> getUsersByRole(String roleName);

    void updateAdminRole(Long userId, String action);

    void saveOrUpdate(User user);

    List<User> findUsersByName(String userName);

    void sendFriendRequest(User currentUser, User friend) throws FriendRequestException;

    Optional<FriendRequest> checkFriendRequest(User userRequest, User userResponse) throws FriendRequestException;
}
