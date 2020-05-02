package com.tms.stankevich.domain.user;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

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

    @Column(name = "about")
    @Type(type="text")
    private String aboutUser;

    @Column(name = "genres")
    @Type(type="text")
    private String favoriteGenres;

    @Column(name = "movies")
    @Type(type="text")
    private String favoriteMovies;

    public boolean isNew() {
        return (this.id == null);
    }
}
