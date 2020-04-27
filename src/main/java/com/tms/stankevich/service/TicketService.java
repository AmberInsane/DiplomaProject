package com.tms.stankevich.service;


import com.tms.stankevich.domain.movie.Ticket;
import com.tms.stankevich.domain.movie.TicketType;
import com.tms.stankevich.domain.user.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TicketService {
    Optional<Ticket> findById(Long ticketId);

    Ticket save(Ticket ticket);

    void saveTickets(Ticket rawTicket);

    Map<TicketType, List<Ticket>> findUsersTickets(User user);

    List<Ticket> findUsersTicketsForFriends(User user);
}
