package com.tms.stankevich.dao;

import com.tms.stankevich.domain.user.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findByUserRequestAndUserResponse(User userRequest, User userResponse);
    List<FriendRequest> findByUserRequest(User userRequest);
    List<FriendRequest> findByUserResponseAndStatus(User userResponse, FriendRequestStatus status);

    List<FriendRequest> findByUserRequestAndStatus(User user, FriendRequestStatus sd);
}
