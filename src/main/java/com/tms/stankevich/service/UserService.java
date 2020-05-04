package com.tms.stankevich.service;


import com.tms.stankevich.domain.user.FriendRequest;
import com.tms.stankevich.domain.user.User;
import com.tms.stankevich.domain.user.UserInfo;
import com.tms.stankevich.exception.AdminAddException;
import com.tms.stankevich.exception.BalanceMinusException;
import com.tms.stankevich.exception.FriendRequestException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findUserById(Long userId);

    Optional<User> findUserByName(String name);

    Optional<User> findUserByEmail(String email);

    List<User> getAllUsers();

    User addUser(User user);

    List<User> getUsersByRole(String roleName);

    void updateAdminRole(Long userId, String action) throws AdminAddException;

    User saveOrUpdate(User user);

    List<User> findUsersByName(String userName);

    void sendFriendRequest(User currentUser, User friend) throws FriendRequestException;

    FriendRequest checkAndGetFriendRequest(User userRequest, User userResponse) throws FriendRequestException;

    void blockUser(User currentUser, User userToBlock);

    void acceptFriendRequest(User currentUser, FriendRequest friendRequest);

    void refuseFriendRequest(User currentUser, FriendRequest friendRequest);

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

    void plusToBalance(User currentUser, BigDecimal sumNumber);

    void minusFromBalance(User user, BigDecimal sumNumber) throws BalanceMinusException;

    boolean isUserAdmin(User user);
}
