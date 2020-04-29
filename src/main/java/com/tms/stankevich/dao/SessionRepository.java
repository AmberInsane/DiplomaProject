package com.tms.stankevich.dao;

import com.tms.stankevich.domain.movie.*;
import com.tms.stankevich.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findById(Long id);

    List<Session> findSessionsByStartTimeGreaterThanOrderByStartTime(LocalDateTime nowTime);

    List<Session> findSessionsByHall(Hall hall);

    List<Session> findSessionsByMovieAndStartTimeGreaterThanOrderByStartTime(Movie movie, LocalDateTime nowTime);

    Optional<Session> findNextSession(LocalDateTime time, Hall hall);

    Optional<Session> findPrevSession(LocalDateTime time, Hall hall);
}