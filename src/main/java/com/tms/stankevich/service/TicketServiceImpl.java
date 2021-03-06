package com.tms.stankevich.service;

import com.tms.stankevich.dao.*;
import com.tms.stankevich.domain.movie.Ticket;
import com.tms.stankevich.domain.movie.TicketType;
import com.tms.stankevich.domain.user.User;
import com.tms.stankevich.exception.BalanceMinusException;
import com.tms.stankevich.exception.TicketReturnTimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {
    private final Logger logger = LogManager.getLogger(TicketServiceImpl.class.getName());

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @Value("${ticket.return.max.before}")
    private int maxTimeReturn;

    @Value("${time.zone}")
    private String timeZone;

    @Override
    public Optional<Ticket> findById(Long ticketId) {
        return ticketRepository.findById(ticketId);
    }

    @Override
    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public void saveTickets(Ticket rawTicket) throws BalanceMinusException {
        userService.minusFromBalance(rawTicket.getUserBy(), rawTicket.getCommonSum());
        logger.debug("saveTickets " + rawTicket.getUserBy() + " "  + rawTicket.getSession().getId() + " " + rawTicket.getUsersFor().size());
        List<User> usersFor = rawTicket.getUsersFor();
        for (User user : usersFor) {
            Ticket newTicket = new Ticket();
            newTicket.setUserBy(rawTicket.getUserBy());
            newTicket.setSession(rawTicket.getSession());
            newTicket.setUserFor(user);
            ticketRepository.save(newTicket);
        }
    }

    private Map<Boolean, List<Ticket>> getPartitioned(List<Ticket> tickets) {
        return tickets.stream().collect(
                Collectors.partitioningBy(this::isTicketValid));
    }

    @Override
    public Map<TicketType, List<Ticket>> findUsersTickets(User user) {
        Map<TicketType, List<Ticket>> ticketsMap = new HashMap<>();

        List<Ticket> myTickets = findTicketByAndForUser(user);
        List<Ticket> friendTickets = findTicketByUserForFriends(user);
        List<Ticket> myTicketsFriends = findTicketByFriendsForUser(user);

        Map<Boolean, List<Ticket>> myPartitioned = getPartitioned(myTickets);
        ticketsMap.put(TicketType.MY, myPartitioned.get(true));
        ticketsMap.put(TicketType.MY_OLD, myPartitioned.get(false));

        Map<Boolean, List<Ticket>> friendPartitioned = getPartitioned(friendTickets);
        ticketsMap.put(TicketType.FOR_FRIEND, friendPartitioned.get(true));
        ticketsMap.put(TicketType.FOR_FRIEND_OLD, friendPartitioned.get(false));

        Map<Boolean, List<Ticket>> myFromFriendPartitioned = getPartitioned(myTicketsFriends);
        ticketsMap.put(TicketType.BY_FRIEND, myFromFriendPartitioned.get(true));
        ticketsMap.put(TicketType.BY_FRIEND_OLD, myFromFriendPartitioned.get(false));

        return ticketsMap;
    }

    @Override
    public List<Ticket> findUsersTicketsForFriends(User user) {
        return ticketRepository.findTicketByUserForFriends(user);
    }

    @Override
    public List<Ticket> findTicketByAndForUser(User user) {
        return ticketRepository.findTicketByAndForUser(user);
    }

    @Override
    public List<Ticket> findTicketByUserForFriends(User user) {
        return ticketRepository.findTicketByUserForFriends(user);
    }

    @Override
    public List<Ticket> findTicketByFriendsForUser(User user) {
        return ticketRepository.findTicketByFriendsForUser(user);
    }

    @Override
    public void returnTicket(Ticket ticket) throws TicketReturnTimeException {
        if (LocalDateTime.now(ZoneId.of(timeZone)).plusMinutes(maxTimeReturn).isAfter(ticket.getSession().getStartTime())) {
            throw new TicketReturnTimeException("message.ticket.late");
        }
        logger.debug("returnTicket " + ticket.getUserBy() + " "  + ticket.getSession().getId() + " " + ticket.getUserFor().getUsername());
        userService.plusToBalance(ticket.getUserBy(), ticket.getSession().getPrice());
        ticketRepository.delete(ticket);
    }

    @Override
    public boolean isTicketValid(Ticket ticket) {
        return sessionService.isSessionValid(ticket.getSession());
    }
}
