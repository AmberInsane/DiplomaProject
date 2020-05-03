package com.tms.stankevich.dao;

import com.tms.stankevich.domain.movie.Ticket;
import com.tms.stankevich.domain.user.Role;
import com.tms.stankevich.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findUsersByRoles(Role role);
    List<User> findByUsernameStartsWithAndRoles(String name, Role role);
}
