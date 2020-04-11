package com.tms.stankevich.dao;

import com.tms.stankevich.domain.user.Role;
import com.tms.stankevich.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findUsersByRoles(Role role);
}
