package com.tms.stankevich.service;

import com.tms.stankevich.dao.FriendRequestRepository;
import com.tms.stankevich.dao.RoleRepository;
import com.tms.stankevich.dao.UserInfoRepository;
import com.tms.stankevich.dao.UserRepository;
import com.tms.stankevich.domain.user.*;

import com.tms.stankevich.exception.AdminAddException;
import com.tms.stankevich.exception.BalanceMinusException;
import com.tms.stankevich.exception.FriendRequestException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.*;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    private final Logger logger = LogManager.getLogger(UserServiceImpl.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private TicketService ticketService;

    public static final String USER_ROLE = "ROLE_USER";
    public static final String ADMIN_ROLE = "ROLE_ADMIN";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("loadUserByUsername" + username);
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }

    @Override
    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> findUserByName(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User addUser(User user) {
        Optional<User> userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB.isPresent()) {
            return userFromDB.get();
        }
        user.setRoles(Collections.singleton(roleRepository.findByName(USER_ROLE)));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        logger.debug("registration " + user.getUsername());
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsersByRole(String roleName) {
        return userRepository.findUsersByRoles(roleRepository.findByName(roleName));
    }

    @Override
    public void updateAdminRole(Long userId, String action) throws AdminAddException {
        Role userRole = roleRepository.findByName(USER_ROLE);
        Role adminRole = roleRepository.findByName(ADMIN_ROLE);
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            switch (action) {
                case "delete":
                    if (isUserAdmin(user)) {
                        user.getRoles().remove(adminRole);
                        user.getRoles().add(userRole);
                        logger.debug(action + " admin " + user.getUsername());
                        userRepository.save(user);
                    }
                    break;
                case "add":
                    if (!isUserAdmin(user)) {
                        if (isUserActive(user)) {
                            throw new AdminAddException("admin.manage.error.active");
                        }
                        user.getRoles().remove(userRole);
                        user.getRoles().add(adminRole);
                        logger.debug(action + " admin " + user.getUsername());
                        userRepository.save(user);
                    }
                    break;
            }
        }
    }

    private boolean isUserActive(User user) {
        return user.getFriends().size() > 0
                || user.getBlackList().size() > 0
                || user.getBalance().compareTo(BigDecimal.ZERO) > 0
                || findInFriendRequests(user).size() > 0
                || findOutFriendRequests(user).size() > 0
                || ticketService.findTicketByAndForUser(user).size() > 0
                ||  ticketService.findTicketByUserForFriends(user).size() > 0;
    }

    @Override
    public User saveOrUpdate(User user) {
        if (user.isNew()) {
            return addUser(user);
        }
        else {
            logger.debug("update user " + user.getUsername());
            return userRepository.save(user);
        }
    }

    @Override
    public List<User> findUsersByName(String userName) {
        Role userRole = roleRepository.findByName(USER_ROLE);
        return userRepository.findByUsernameStartsWithAndRoles(userName, userRole);
    }

    @Override
    public void sendFriendRequest(User userRequest, User userResponse) throws FriendRequestException {
        logger.debug("sendFriendRequest from " + userRequest.getUsername() + " to " + userResponse.getUsername());
        if (userRequest.equals(userResponse))
            throw new FriendRequestException("message.request.deny.me");
        FriendRequest friendRequest = checkAndGetFriendRequest(userRequest, userResponse);
        friendRequest.setStatus(FriendRequestStatus.SD);
        friendRequestRepository.save(friendRequest);
    }

    @Override
    public FriendRequest checkAndGetFriendRequest(User userRequest, User userResponse) throws FriendRequestException {
        FriendRequest request;
        Optional<FriendRequest> friendRequest = friendRequestRepository.findByUserRequestAndUserResponse(userRequest, userResponse);
        if (friendRequest.isPresent()) {
            request = friendRequest.get();
            @NotNull FriendRequestStatus status = request.getStatus();
            if (status == FriendRequestStatus.SD)
                throw new FriendRequestException("message.request.deny.repeat");
            return request;
        }
        if (isUserFriendSecond(userRequest, userResponse)) {
            throw new FriendRequestException("message.request.deny.already");
        }
        if (isUserBlockedSecond(userResponse, userRequest)) {
            throw new FriendRequestException("message.request.deny.block");
        }
        request = new FriendRequest();
        request.setUserRequest(userRequest);
        request.setUserResponse(userResponse);
        return request;
    }

    @Override
    public void blockUser(User currentUser, User userToBlock) {
        if (!isUserBlockedSecond(currentUser, userToBlock)) {
            //удалить из друзей
            if (!deleteFromFriends(currentUser, userToBlock)) {
                //ИЛИ обновить все запросы
                Optional<FriendRequest> request = findOutFriendRequests(currentUser).stream()
                        .filter(friendRequest -> friendRequest.getUserResponse().equals(userToBlock)).findFirst();
                if (request.isPresent()) {
                    cancelFriendRequest(currentUser, request.get());
                }

                Optional<FriendRequest> response = findInFriendRequests(currentUser).stream()
                        .filter(friendRequest -> friendRequest.getUserRequest().equals(userToBlock)).findFirst();

                if (response.isPresent()) {
                    refuseFriendRequest(currentUser, response.get());
                }
            }
            currentUser.getBlackList().add(userToBlock);
            logger.debug("blockUser " + currentUser.getUsername() + " of " + userToBlock.getUsername());
            userRepository.save(currentUser);
        }
    }

    @Override
    public void acceptFriendRequest(User currentUser, FriendRequest friendRequest) {
        if (friendRequest.getUserResponse().equals(currentUser)) {
            User userRequest = friendRequest.getUserRequest();
            userRequest.getFriends().add(currentUser);
            userRepository.save(userRequest);

            currentUser.getFriends().add(userRequest);
            userRepository.save(currentUser);

            logger.debug("acceptFriendRequest from " + userRequest.getUsername() + " to " + currentUser.getUsername());
            friendRequest.setStatus(FriendRequestStatus.OK);
            friendRequestRepository.save(friendRequest);
        }
    }

    @Override
    public void refuseFriendRequest(User currentUser, FriendRequest friendRequest) {
        if (friendRequest.getUserResponse().equals(currentUser)) {
            logger.debug("refuseFriendRequest from " + friendRequest.getUserRequest().getUsername() + " to " + currentUser.getUsername());
            friendRequest.setStatus(FriendRequestStatus.NO);
            friendRequestRepository.save(friendRequest);
        }
    }

    @Override
    public void cancelFriendRequest(User currentUser, FriendRequest request) {
        if (request.getUserRequest().equals(currentUser)) {
            request.setStatus(FriendRequestStatus.AN);
            friendRequestRepository.save(request);
        }
    }

    @Override
    public boolean deleteFromFriends(User userWho, User userDelete) {
        if (isUserFriendSecond(userWho, userDelete)) {
            userWho.getFriends().removeIf(user -> user.equals(userDelete));
            userRepository.save(userWho);

            userDelete.getFriends().removeIf(user -> user.equals(userWho));
            userRepository.save(userDelete);
            logger.debug("deleteFromFriends " + userWho.getUsername() + "  of " + userDelete.getUsername());
            return true;
        }
        return false;
    }

    @Override
    public boolean isUserBlockedSecond(User currentUser, User userSecond) {
        return currentUser.getBlackList().stream().anyMatch(user -> user.equals(userSecond));
    }

    @Override
    public boolean isUserFriendSecond(User currentUser, User userSecond) {
        return currentUser.getFriends().stream().anyMatch(user -> user.equals(userSecond));
    }

    @Override
    public void saveOrUpdateUserInfo(UserInfo userInfo) {
        userInfoRepository.save(userInfo);
    }

    @Override
    public List<FriendRequest> findInFriendRequests(User user) {
        return friendRequestRepository.findByUserResponseAndStatus(user, FriendRequestStatus.SD);
    }

    @Override
    public List<FriendRequest> findOutFriendRequests(User user) {
        return friendRequestRepository.findByUserRequestAndStatus(user, FriendRequestStatus.SD);
    }

    @Override
    public void unblockUser(User currentUser, User userToUnblock) {
        if (isUserBlockedSecond(currentUser, userToUnblock)) {
            currentUser.getBlackList().removeIf(user -> user.equals(userToUnblock));
            logger.debug("unblockUser " + currentUser.getUsername() + "  of " + userToUnblock.getUsername());
            userRepository.save(currentUser);
        }
    }

    @Override
    public Optional<FriendRequest> findFriendRequestById(Long id) {
        return friendRequestRepository.findById(id);
    }

    @Override
    public List<User> getUserFriendList(User user) {
        List<User> userList = new ArrayList<>(user.getFriends());
        userList.addAll(user.getFriendOf());
        return userList;
    }

    @Override
    public void plusToBalance(User user, BigDecimal sumNumber) {
        User curUser = userRepository.findById(user.getId()).get();
        logger.debug("plusToBalance " + curUser.getUsername() + " " + sumNumber);
        curUser.setBalance(curUser.getBalance().add(sumNumber));
        userRepository.save(curUser);
    }

    @Override
    public void minusFromBalance(User user, BigDecimal sumNumber) throws BalanceMinusException {
        logger.debug("minusFromBalance " + user.getUsername() + " " + sumNumber);
        BigDecimal userBalance = user.getBalance();
        if (userBalance.compareTo(sumNumber) < 0)
            throw new BalanceMinusException();
        user.setBalance(user.getBalance().subtract(sumNumber));
        userRepository.save(user);
    }

    @Override
    public boolean isUserAdmin(User user) {
        Role adminRole = roleRepository.findByName(ADMIN_ROLE);
        return user.getRoles().stream().anyMatch(role -> role.equals(adminRole));
    }
}
