package com.tms.stankevich.service;


import com.tms.stankevich.domain.movie.Movie;
import com.tms.stankevich.domain.movie.Seance;
import com.tms.stankevich.domain.user.User;

import java.util.Map;

public interface MyUserService {
    void sendFriendRequest(User user);

    void replyFriendRequest(User request, boolean isAccept);

    void buyTicket(User user, Seance seance, Map<User, Integer> ticketNumber);

    void addMovieRating(Movie movie, Integer rate);
}
