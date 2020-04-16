package com.tms.stankevich.domain.movie;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Session implements Serializable {
    private static final long serialVersionUID = 42L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seance_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="movie_id", nullable=false)
    private Movie movie;

    @Column(name = "date_time", nullable=false)
    @DateTimeFormat(pattern = "dd-mm-yyyy HH:mm")
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "hall_id", nullable=false)
    private Hall hall;

    @Column(name = "price")
    private BigDecimal price;

    public boolean isNew() {
        return (this.id == null);
    }
}
