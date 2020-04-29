package com.tms.stankevich.service;

import com.tms.stankevich.domain.movie.Hall;
import com.tms.stankevich.domain.movie.Movie;
import com.tms.stankevich.domain.movie.Session;
import com.tms.stankevich.exception.HallDeleteException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SessionService {
    List<Session> getAllSessions();

    List<Hall> getAllHalls();

    Session saveOrUpdate(Session session);

    Hall saveOrUpdateHall(Hall hall);

    Optional<Hall> findHallById(Long id);

    Optional<Hall> findHallByName(String name);

    void deleteHall(Hall hall) throws HallDeleteException;

    Optional<Session> findById(Long id);

    List<Session> findByMovie(Movie movie);

    Optional<Session> getNextSession(LocalDateTime time, Hall hall);

    Optional<Session> getPrevSession(LocalDateTime time, Hall hall);

    void deleteSession(Session session);
}
