package com.tms.stankevich.сontroller;

import com.tms.stankevich.domain.movie.*;
import com.tms.stankevich.domain.user.User;
import com.tms.stankevich.exception.MovieRateException;
import com.tms.stankevich.service.MovieService;
import com.tms.stankevich.service.SessionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.sql.Date;

@Controller
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;
    @Autowired
    private SessionService sessionService;

    @Value("${time.zone}")
    private String timeZone;

    private final Logger logger = LogManager.getLogger(MovieController.class);

    @GetMapping("")
    public String showAllMovies(Model model) {
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "movie/all_movies";
    }

    @GetMapping("/{movie_id}")
    public String showMovie(@PathVariable("movie_id") Long id, Model model, @AuthenticationPrincipal User user) {
        Optional<Movie> movie = movieService.findById(id);
        if (movie.isPresent()) {
            model.addAttribute("movie", movie.get());
            List<Session> sessions = sessionService.findByMovie(movie.get());
            model.addAttribute("sessions", sessions);
            model.addAttribute("rates", movieService.getRatesList());
            Optional<MovieRate> movieRateUser = movieService.getUserMovieRate(user, movie.get());
            if (movieRateUser.isPresent()) {
                model.addAttribute("user_rate_value", movieRateUser.get().getValue());
            }

            return "movie/show/movie";
        } else {
            return "movie/all_movies";
        }
    }

    @GetMapping("/today")
    public String showMovieToday(Model model) {
        Map<Date, Map<Movie, List<Session>>> moviesMap = sessionService.getDaysSessionsMap();
        model.addAttribute("movies", moviesMap);
        return "movie/today_sessions";

    }

    @PostMapping("/rate/{movie_id}")
    public String rateMovie(@PathVariable("movie_id") Long id, @RequestParam("rate") Integer rate, Model model, @AuthenticationPrincipal User user) {
        Optional<Movie> movie = movieService.findById(id);
        if (movie.isPresent()) {
            try {
                movieService.rateMovie(movie.get(), user, rate);
            } catch (MovieRateException e) {
                model.addAttribute("css", "danger");
                model.addAttribute("msg_code", e.getMessage());
            }
            return "redirect:/movie/" + movie.get().getId();
        } else {
            return "redirect:/";
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
        return "redirect:/";
    }

    @GetMapping("/genre/{genre_id}")
    public String showMoviesByGenre(@PathVariable("genre_id") Long id, Model model, HttpServletRequest request) {
        Optional<Genre> genre = movieService.findGenreById(id);
        if (genre.isPresent()) {
            List<Movie> movies = movieService.getMoviesByGenre(genre.get());
            model.addAttribute("genre", genre.get());
            model.addAttribute("movies", movies);
            return "movie/all_movies";
        }
        return "redirect:/";
    }

    @GetMapping("/year/{year}")
    public String showMoviesByYear(@PathVariable("year") String yearStr, Model model, HttpServletRequest request) {
        try {
            Short year = Short.parseShort(yearStr);
            List<Movie> movies = movieService.getMoviesByYear(year);
            model.addAttribute("year", year);
            model.addAttribute("movies", movies);
            return "movie/all_movies";

        } catch (NumberFormatException e) {
            Enumeration<String> headerNames = request.getHeaderNames();
            String name;
            String value;

            while (headerNames.hasMoreElements()) {
                name = headerNames.nextElement();
                value = request.getHeader(name);
                logger.info(name  + " " + value);
                logger.error(name  + " " + value);
                logger.debug(name  + " " + value);
            }
            return "redirect:/";
        }
    }
}
