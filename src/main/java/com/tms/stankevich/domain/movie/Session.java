package com.tms.stankevich.domain.movie;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.sql.Date;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Getter
@Setter
@NamedQueries({
        @NamedQuery(name = "Session.findPrevSession",
                query = "SELECT s FROM Session s where s.hall = ?2 and s.startTime = " +
                        "(select max(s1.startTime) from Session s1 where s1.startTime < ?1 and s1.hall = ?2)"),
        @NamedQuery(name = "Session.findNextSession",
                query = "SELECT s FROM Session s where s.hall = ?2 and s.startTime = " +
                        "(select min(s1.startTime) from Session s1 where s1.startTime > ?1 and s1.hall = ?2)"),
        @NamedQuery(name = "Session.findDateValidSession",
                query = "SELECT s FROM Session s where date(s.startTime) = ?1 and s.startTime > current_time order by s.startTime")
})
public class Session implements Serializable {
    private static final long serialVersionUID = 42L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @ManyToOne
    @JoinColumn(name = "hall_id", nullable = false)
    private Hall hall;

    @Column(name = "price")
    private BigDecimal price;

    @Formula(value = "DATE_FORMAT(start_time, '%Y-%m-%dT%H:%i')")
    private String dateFormatJSP;

    @Formula(value = "DATE_FORMAT(start_time, '%d.%m.%Y %H:%i')")
    private String dateFormatText;

    @Formula("(select count(1) from Ticket t where t.session_id = session_id)")
    private Integer ticketsSold;

    public boolean isNew() {
        return (this.id == null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(id, session.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
