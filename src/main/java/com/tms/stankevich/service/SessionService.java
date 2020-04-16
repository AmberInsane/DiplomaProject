package com.tms.stankevich.service;

import com.tms.stankevich.domain.movie.Hall;
import com.tms.stankevich.domain.movie.Session;

import java.util.List;

public interface SessionService {
    List<Session> getAllSessions();

    List<Hall> getAllHalls();

    Session saveOrUpdate(Session session);
}
