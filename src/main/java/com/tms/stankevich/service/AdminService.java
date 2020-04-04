package com.tms.stankevich.service;

import com.tms.stankevich.domain.movie.Hall;
import com.tms.stankevich.domain.movie.Movie;
import com.tms.stankevich.domain.movie.Seance;
import com.tms.stankevich.domain.user.User;

public interface AdminService {
    void setAdminRole(User user, boolean isAdmin);

    /*void addMovie(Movie movie);

    void updateMovie(Movie movie);

    void deleteMovie(Movie movie);

    void addHall(Hall hall);

    void updateHall(Hall hall);

    void deleteHall(Hall hall);

    void addSeance(Seance hall);

    void updateSeance(Seance hall);

    void deleteSeance(Seance hall);*/
}
