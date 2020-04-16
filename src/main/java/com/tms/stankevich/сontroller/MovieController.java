package com.tms.stankevich.сontroller;

import com.tms.stankevich.domain.movie.Genre;
import com.tms.stankevich.domain.movie.Hall;
import com.tms.stankevich.domain.movie.Movie;
import com.tms.stankevich.domain.movie.Session;
import com.tms.stankevich.service.MovieServiceImpl;
import com.tms.stankevich.service.SessionServiceImpl;
import com.tms.stankevich.validator.MovieFormValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/movie")
public class MovieController {

    final static Logger logger = Logger.getLogger(MovieController.class);

    @Autowired
    MovieFormValidator movieFormValidator;

    @InitBinder("movieForm")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(movieFormValidator);
    }

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

    @GetMapping("/add_movie")
    public String addMovie(Model model) {
        Movie movie = new Movie();
        model.addAttribute("movieForm",movie);
        populateDefaultMovieModel(model);
        return "movie/movie";
    }

    @PostMapping(value = "/add_movie")
    public String saveOrUpdateMovie(@ModelAttribute("movieForm") @Validated Movie movie,
                                    BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            populateDefaultMovieModel(model);
            return "movie/movie";
        } else {
            redirectAttributes.addFlashAttribute("css", "success");
            if (movie.isNew()) {
                redirectAttributes.addFlashAttribute("msg", "User added successfully!");
            } else {
                redirectAttributes.addFlashAttribute("msg", "User updated successfully!");
            }
            movieService.saveOrUpdate(movie);
            return "redirect:/movie";
        }
    }

    @GetMapping("/update_movie/{id}")
    public String updateMovie(@PathVariable("id") Long id, Model model) {
        Optional<Movie> movie = movieService.findById(id);

        if (movie.isPresent()) {
            model.addAttribute("movieForm", movie.get());
            populateDefaultMovieModel(model);
            return "movie/movie";
        } else {
            return "users/userform";
        }
    }

    @PostMapping("/delete_movie/{id}")
    public String deleteMovie(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Optional<Movie> movie = movieService.findById(id);
        if (movie.isPresent()) {
            String movieName = movie.get().getTitle();
            movieService.deleteMovie(movie.get());
            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("msg", movieName + " удалено");
        }
        return "redirect:/movie";
    }

    private void populateDefaultMovieModel(Model model) {
        List<Genre> genresList = movieService.getAllGenres();
        model.addAttribute("genreList", genresList);
    }

    @GetMapping("/genre")
    public String showAllGenres(Model model) {
        model.addAttribute("genreForm", new Genre());
        model.addAttribute("genres", movieService.getAllGenres());
        populateDefaultMovieModel(model);
        return "movie/genre";
    }

    @PostMapping(value = "/genre")
    public String saveOrUpdateGenre(@ModelAttribute("genreForm") Genre genre,
                                    BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
        if (genre.getName().length() < 3) {
            redirectAttributes.addFlashAttribute("css", "has-error");
            redirectAttributes.addFlashAttribute("msg", "не добавлено");
        } else {
            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("msg", genre.getName() + " добавлено");
            movieService.saveOrUpdateGenre(genre);
        }

        return "redirect:/movie/genre";
    }

    @PostMapping("/delete_genre/{id}")
    public String deleteGenre(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Optional<Genre> genre = movieService.findGenreById(id);
        if (genre.isPresent()) {
            String genreName = genre.get().getName();
          movieService.deleteGenre(genre.get());
            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("msg", genreName + " удалено");
        }
       return  "redirect:/movie/genre";
    }

    @GetMapping("/session")
    public String showAllSessions(Model model) {
        List<Session> sessions = sessionService.getAllSessions();
        model.addAttribute("sessions", sessions);
        return "movie/all_sessions";
    }

    @GetMapping("/session/add_session")
    public String addSession(Model model) {
        Session session = new Session();
        populateDefaultSessionModel(model);
        model.addAttribute("sessionForm",session);
        return "movie/session";
    }

    @PostMapping(value = "/session/add_movie")
    public String saveOrUpdateSession(@ModelAttribute("sessionForm") @Validated Session session,
                                    BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            populateDefaultMovieModel(model);
            return "movie/session";
        } else {
            sessionService.saveOrUpdate(session);
            return "redirect:/movie/session";
        }
    }

    private void populateDefaultSessionModel(Model model) {
        List<Hall> hallList = sessionService.getAllHalls();
        model.addAttribute("hallList", hallList);

        List<Movie> movieList = movieService.getAllMovies();
        model.addAttribute("movieList", movieList);
    }

}
