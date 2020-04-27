package com.tms.stankevich.domain.movie;

import com.tms.stankevich.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Ticket implements Serializable {
    private static final long serialVersionUID = 42L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private long id;

    @ManyToOne
    @JoinColumn(name="session_id", nullable=false)
    private Session session;

    @ManyToOne
    @JoinColumn(name="user_by_id", nullable=false)
    private User userBy;

    @ManyToOne
    @JoinColumn(name="user_for_id", nullable=false)
    private User userFor;

    @Transient
    private List<User> usersFor;

    public boolean isEnable(){
        return session.getStartTime().compareTo(LocalDateTime.now()) > 0;
    }
}
