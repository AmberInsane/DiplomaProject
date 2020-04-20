package com.tms.stankevich.service;

import com.tms.stankevich.dao.HallRepository;
import com.tms.stankevich.dao.SessionRepository;
import com.tms.stankevich.domain.movie.Hall;
import com.tms.stankevich.domain.movie.Movie;
import com.tms.stankevich.domain.movie.Session;
import com.tms.stankevich.exception.HallDeleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SessionServiceImpl implements SessionService {
    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    HallRepository hallRepository;

    @Override
    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    @Override
    public List<Hall> getAllHalls() {
        return hallRepository.findAll();
    }

    @Override
    public Session saveOrUpdate(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    public Hall saveOrUpdateHall(Hall hall) {
        return hallRepository.save(hall);
    }

    @Override
    public Optional<Hall> findHallById(Long id) {
        return hallRepository.findById(id);
    }

    @Override
    public void deleteHall(Hall hall) throws HallDeleteException {
        List<Session> session = sessionRepository.findSessionsByHall(hall);
        if (session.size() > 0)
            throw new HallDeleteException(session.size());
        hallRepository.delete(hall);
    }

    @Override
    public Optional<Session> findById(Long id) {
        return sessionRepository.findById(id);
    }

    @Override
    public List<Session> findByMovie(Movie movie) {
      return sessionRepository.findSessionsByMovie(movie);
    }

    public void deleteSession(Session session) {
        sessionRepository.delete(session);
    }
}
