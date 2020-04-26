package com.tms.stankevich.dao;

import com.tms.stankevich.domain.user.FriendRequest;
import com.tms.stankevich.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findByUserRequestAndUserResponse(User userRequest, User userResponse);
    List<FriendRequest> findByUserRequest(User userRequest);
    List<FriendRequest> findByUserResponse(User userResponse);
}
