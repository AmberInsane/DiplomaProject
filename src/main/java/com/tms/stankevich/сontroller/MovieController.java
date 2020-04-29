package com.tms.stankevich.сontroller;

import com.tms.stankevich.domain.movie.*;
import com.tms.stankevich.service.MovieService;
import com.tms.stankevich.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;
    @Autowired
    private SessionService sessionService;

    @GetMapping("")
    public String showAllMovies(Model model) {
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "movie/all_movies";
    }

    @GetMapping("/{movie_id}")
    public String showMovie(@PathVariable("movie_id") Long id, Model model) {
        Optional<Movie> movie = movieService.findById(id);
        if (movie.isPresent()) {
            model.addAttribute("movie", movie.get());
            List<Session> sessions = sessionService.findByMovie(movie.get());
            model.addAttribute("sessions", sessions);
            return "movie/show/movie";
        } else {
            return "movie/all_movies";
        }
    }

    @GetMapping("/session")
    public String showAllSessions(Model model) {
        List<Session> sessions = sessionService.getAllSessions();
        model.addAttribute("sessions", sessions);
        return "movie/all_sessions";
    }

    @GetMapping("/hall/{hall_id}")
    public String showHall(@PathVariable("hall_id") Long id, Model model, HttpServletRequest request) {
        Optional<Hall> hall = sessionService.findHallById(id);
        if (hall.isPresent()) {
            model.addAttribute("hall", hall.get());
            return "movie/show/hall";
        }
        return "redirect:" + request.getHeader("Referer");
    }
}
