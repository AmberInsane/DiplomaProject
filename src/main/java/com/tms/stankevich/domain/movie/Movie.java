package com.tms.stankevich.domain.movie;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class Movie implements Serializable {
    private static final long serialVersionUID = 42L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @Column(name = "issue_year")
    private Short year;

    @Column
    private String genre;

    @Column(name = "rating_sum")
    private BigDecimal ratingSum;

    @Column(name = "rating_num")
    private BigDecimal ratingNum;

    @Column(name = "time_length")
    private Integer timeLength;
}
