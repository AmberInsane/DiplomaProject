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

@Entity
@Getter
@Setter
public class Session implements Serializable {
    private static final long serialVersionUID = 42L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="movie_id", nullable=false)
    private Movie movie;

    @Column(name = "start_time", nullable=false)
    private LocalDateTime startTime;

    @ManyToOne
    @JoinColumn(name = "hall_id", nullable=false)
    private Hall hall;

    @Column(name = "price")
    private BigDecimal price;

    @Formula(value = "DATE_FORMAT(start_time, '%Y-%m-%dT%H:%i')")
    private String dateFormatJSP;

    @Formula(value = "DATE_FORMAT(start_time, '%d.%m.%Y %H:%i')")
    private String dateFormatText;

    public boolean isNew() {
        return (this.id == null);
    }
}
