package com.tms.stankevich.service;


import com.tms.stankevich.domain.movie.Ticket;
import com.tms.stankevich.domain.movie.TicketType;
import com.tms.stankevich.domain.user.User;
import com.tms.stankevich.exception.BalanceMinusException;
import com.tms.stankevich.exception.TicketReturnTimeException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TicketService {
    Optional<Ticket> findById(Long ticketId);

    Ticket save(Ticket ticket);

    void saveTickets(Ticket rawTicket) throws BalanceMinusException;

    Map<TicketType, List<Ticket>> findUsersTickets(User user);

    List<Ticket> findUsersTicketsForFriends(User user);

    void returnTicket(Ticket ticket) throws TicketReturnTimeException;

    boolean isTicketValid(Ticket ticket);
}
