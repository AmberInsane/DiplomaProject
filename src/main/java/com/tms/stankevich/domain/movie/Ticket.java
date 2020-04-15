package com.tms.stankevich.domain.movie;

import com.tms.stankevich.domain.user.User;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Ticket implements Serializable {
    private static final long serialVersionUID = 42L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private long id;

    @ManyToOne
    @JoinColumn(name="seance_id", nullable=false)
    private Session session;

    @ManyToOne
    @JoinColumn(name="user_by_id", nullable=false)
    private User userBy;

    @ManyToOne
    @JoinColumn(name="user_for_id", nullable=false)
    private User userFor;
}
