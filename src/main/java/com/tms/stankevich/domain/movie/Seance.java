package com.tms.stankevich.domain.movie;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Seance implements Serializable {
    private static final long serialVersionUID = 42L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seance_id")
    private long id;

    @ManyToOne
    @JoinColumn(name="movie_id", nullable=false)
    private Movie movie;

    @Column(name = "date_time", nullable=false)
    private LocalDateTime dateTime;

    @ManyToOne
    @Column(name = "hall_id", nullable=false)
    private Hall hall;

    @Column(name = "price")
    private BigDecimal price;
}
