package com.tms.stankevich.service;

import com.tms.stankevich.dao.HallRepository;
import com.tms.stankevich.dao.SessionRepository;
import com.tms.stankevich.dao.TicketRepository;
import com.tms.stankevich.domain.movie.Hall;
import com.tms.stankevich.domain.movie.Movie;
import com.tms.stankevich.domain.movie.Session;
import com.tms.stankevich.domain.movie.Ticket;
import com.tms.stankevich.exception.HallDeleteException;
import com.tms.stankevich.exception.SessionDeleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SessionServiceImpl implements SessionService {
    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    HallRepository hallRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Value("${time.zone}")
    private String timeZone;

    @Value("${session.togay.count}")
    private Integer sessionDaysCount;

    @Override
    public List<Session> getAllSessions() {
        return sessionRepository.findSessionsByStartTimeGreaterThanOrderByStartTime(LocalDateTime.now(ZoneId.of(timeZone)));
    }

    @Override
    public List<Hall> getAllHalls() {
        return hallRepository.findAll();
    }

    @Override
    public List<Hall> getAllHallsOrdered() {
        return hallRepository.findAllByOrderByName();
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
    public Optional<Hall> findHallByName(String name) {
        return hallRepository.findByName(name);
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
      return sessionRepository.findSessionsByMovieAndStartTimeGreaterThanOrderByStartTime(movie, LocalDateTime.now(ZoneId.of(timeZone)));
    }

    @Override
    public Optional<Session> getNextSession(LocalDateTime time, Hall hall) {
        Optional<Session> nextSession = sessionRepository.findNextSession(time,hall);
        return nextSession;
    }

    @Override
    public Optional<Session> getPrevSession(LocalDateTime time, Hall hall) {
        Optional<Session> prevSession = sessionRepository.findPrevSession(time,hall);
        return prevSession;
    }

    @Override
    public void deleteSession(Session session) throws SessionDeleteException {
        List<Ticket> tickets = ticketRepository.findTicketsBySession(session);
        if (tickets.size() > 0)
            throw new SessionDeleteException(tickets.size());
        sessionRepository.delete(session);
    }

    @Override
    public Map<Movie, List<Session>> getDaySessionsMap(Date date) {
        List<Session> sessions = sessionRepository.findDateValidSession(date);
        Map<Movie, List<Session>> movieListMap = sessions.stream()
                .collect(Collectors.groupingBy(Session::getMovie));
        return movieListMap;
    }
    @Override
    public boolean isSessionValid(Session session) {
        return session.getStartTime().compareTo(LocalDateTime.now(ZoneId.of(timeZone))) > 0;
    }

    @Override
    public Map<Date, Map<Movie, List<Session>>> getDaysSessionsMap() {
        Map<Date, Map<Movie, List<Session>>> sessionsMap = new TreeMap<>();
        LocalDate date = LocalDate.now(ZoneId.of(timeZone));
        for (int i = 0; i < sessionDaysCount; i++) {
            sessionsMap.put(Date.valueOf(date), getDaySessionsMap(Date.valueOf(date)));
            date = date.plusDays(1);
        }
        return sessionsMap;
    }
}
