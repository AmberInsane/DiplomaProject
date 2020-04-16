package com.tms.stankevich.service;

import com.tms.stankevich.dao.HallRepository;
import com.tms.stankevich.dao.SessionRepository;
import com.tms.stankevich.domain.movie.Hall;
import com.tms.stankevich.domain.movie.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
