package com.tms.stankevich.dao;

import com.tms.stankevich.domain.movie.Genre;
import com.tms.stankevich.domain.movie.Hall;
import com.tms.stankevich.domain.movie.Movie;
import com.tms.stankevich.domain.movie.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findById(Long id);
    List<Session> findSessionsByStartTimeGreaterThanOrderByStartTime(LocalDateTime nowTime);
    List<Session> findSessionsByHall(Hall hall);
    List<Session> findSessionsByMovieAndStartTimeGreaterThanOrderByStartTime(Movie movie, LocalDateTime nowTime);
}