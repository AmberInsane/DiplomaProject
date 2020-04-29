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

    @Query("SELECT min(start_time) FROM session s WHERE  s.start_time > ?1 and s.hall_id = ?2")
    Optional<LocalDateTime> getNextSessionBeginTime(LocalDateTime time, Hall hall);


    //добавить добавление времени фильма
    @Query("SELECT max(start_time) FROM session s WHERE  s.start_time <= ?1 and s.hall_id = ?2")
    Optional<LocalDateTime> getPrevSessionEndTime(LocalDateTime time, Hall hall);
}