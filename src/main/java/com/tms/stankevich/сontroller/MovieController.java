package com.tms.stankevich.сontroller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.tms.stankevich.domain.movie.*;
import com.tms.stankevich.domain.user.User;
import com.tms.stankevich.exception.GenreDeleteException;
import com.tms.stankevich.exception.HallDeleteException;
import com.tms.stankevich.exception.MovieDeleteException;
import com.tms.stankevich.service.MovieServiceImpl;
import com.tms.stankevich.service.SessionServiceImpl;
import com.tms.stankevich.validator.HallFormValidator;
import com.tms.stankevich.validator.MovieFormValidator;
import com.tms.stankevich.validator.SessionFormValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/movie")
public class MovieController {

    final static Logger logger = Logger.getLogger(MovieController.class);

    @Autowired
    private MovieServiceImpl movieService;
    @Autowired
    private SessionServiceImpl sessionService;

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
