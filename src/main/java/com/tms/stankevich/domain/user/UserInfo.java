package com.tms.stankevich.domain.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "user_info")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "info_id")
    private Long id;

    @Column(name = "about", length = 400)
    private String aboutUser;

    @Column(name = "genres", length = 400)
    private String favoriteGenres;

    @Column(name = "movies", length = 400)
    private String favoriteMovies;

    public boolean isNew() {
        return (this.id == null);
    }
}
