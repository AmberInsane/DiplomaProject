package com.tms.stankevich.domain.movie;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

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

    @NotNull
    @Column
    @Type(type = "text")
    private String description;

    @Column(name = "issue_year")
    @NotNull
    private @Valid Short year;

    @ManyToMany
    @JoinTable(name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genre;

    @Column(name = "time_length")
    private @Valid Short timeLength;

    @Formula("(select round(avg(mr.value),2) from movie_rate mr where mr.movie_id = movie_id)")
    private BigDecimal rate;

    @Formula("(select count(mr.value) from movie_rate mr where mr.movie_id = movie_id)")
    private Integer rateCount;

    public boolean isNew() {
        return (this.id == null);
    }
}
