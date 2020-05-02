package com.tms.stankevich.domain.movie;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
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

   /* @Column(name = "IMAGE")
    private Blob image;*/

    public boolean isNew() {
        return (this.id == null);
    }
}
