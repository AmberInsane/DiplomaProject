package com.tms.stankevich.dao;

import com.tms.stankevich.domain.movie.Hall;
import com.tms.stankevich.domain.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findById(Long id);
}