package com.tms.stankevich.dao;

import com.tms.stankevich.domain.movie.Session;
import com.tms.stankevich.domain.movie.Ticket;
import com.tms.stankevich.domain.user.Role;
import com.tms.stankevich.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findTicketByUserFor(User user);
    List<Ticket> findTicketsBySession(Session session);

    @Query("SELECT t FROM Ticket t WHERE t.userBy = ?1 and t.userFor <> ?1")
    List<Ticket> findTicketByUserForFriends(User user);

}
