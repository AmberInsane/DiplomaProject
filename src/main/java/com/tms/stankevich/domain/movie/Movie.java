package com.tms.stankevich.domain.movie;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Movie implements Serializable {
    private static final long serialVersionUID = 42L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long id;

    @Column
    @NotNull
    private String title;

    @Column
    @NotNull
    private String description;

    @Column(name = "issue_year")
    @NotNull
    private @Valid Short year;

    @Column
    @NotNull
    private String genre;

    @Column(name = "rating_sum")
    private BigDecimal ratingSum;

    @Column(name = "rating_num")
    private BigDecimal ratingNum;

    @Column(name = "time_length")
    @NotNull
    private @Valid Short timeLength;

    public boolean isNew() {
        return (this.id == null);
    }
}
