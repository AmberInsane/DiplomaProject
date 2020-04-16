package com.tms.stankevich.dao;

import com.tms.stankevich.domain.movie.Genre;
import com.tms.stankevich.domain.movie.Hall;
import com.tms.stankevich.domain.movie.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findById(Long id);
    List<Session> findAll();
    List<Session> findSessionsByHall(Hall hall);
}