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

    @Query("select s from session s  where s.hall_id = ?1 and s.start_time = (select max(start_time) " +
            "from session s where s.startTime <= ?1 and s.hall = ?2)")
    Optional<Session> findNextSession(LocalDateTime time, Long hallId);
//
//
//    //добавить добавление времени фильма
//    @Query("SELECT s FROM session s WHERE s.id = 1")
//    Optional<Session> findPrevSession(LocalDateTime time, Long hallId);
}