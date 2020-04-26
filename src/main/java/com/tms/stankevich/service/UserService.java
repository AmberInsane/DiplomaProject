package com.tms.stankevich.service;


import com.tms.stankevich.domain.user.FriendRequest;
import com.tms.stankevich.domain.user.User;
import com.tms.stankevich.domain.user.UserInfo;
import com.tms.stankevich.exception.FriendRequestException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User findUserById(Long userId);

    List<User> getAllUsers();

    User addUser(User user);

    List<User> getUsersByRole(String roleName);

    void updateAdminRole(Long userId, String action);

    User saveOrUpdate(User user);

    List<User> findUsersByName(String userName);

    void sendFriendRequest(User currentUser, User friend) throws FriendRequestException;

    FriendRequest checkAndGetFriendRequest(User userRequest, User userResponse) throws FriendRequestException;

    void blockUser(User currentUser, User userToBlock);

    void acceptFriendRequest(User currentUser, FriendRequest friendRequest);

    void denyFriendRequest(User currentUser, FriendRequest friendRequest);

    void cancelFriendRequest(User currentUser, FriendRequest request);

    boolean deleteFromFriends(User userWho, User userDelete);

    boolean isUserBlockedSecond(User currentUser, User userSecond);

    boolean isUserFriendSecond(User currentUser, User userSecond);

    void saveOrUpdateUserInfo(UserInfo userInfo);

    List<FriendRequest> findInFriendRequests(User user);

    List<FriendRequest> findOutFriendRequests(User user);

    void unblockUser(User currentUser, User userToUnblock);

    Optional<FriendRequest> findFriendRequestById(Long id);

    List<User> getUserFriendList(User user);
}
