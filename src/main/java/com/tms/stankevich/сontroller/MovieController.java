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
    MovieFormValidator movieFormValidator;

    @Autowired
    SessionFormValidator sessionFormValidator;

    @Autowired
    HallFormValidator hallFormValidator;

    @Autowired
    private MovieServiceImpl movieService;
    @Autowired
    private SessionServiceImpl sessionService;

    @InitBinder("movieForm")
    protected void initBinderMovie(WebDataBinder binder) {
        binder.setValidator(movieFormValidator);
    }

    @InitBinder("sessionForm")
    protected void initBinderSession(WebDataBinder binder) {
        binder.setValidator(sessionFormValidator);
    }

    @InitBinder("hallForm")
    protected void initBinderHall(WebDataBinder binder) {
        binder.setValidator(hallFormValidator);
    }


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

    @GetMapping("/add")
    public String addMovie(Model model) {
        Movie movie = new Movie();
        model.addAttribute("movieForm", movie);
        populateDefaultMovieModel(model);
        return "movie/add/movie";
    }

    @PostMapping(value = "/add")
    public String saveOrUpdateMovie(@ModelAttribute("movieForm") @Validated Movie movie,
                                    BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            populateDefaultMovieModel(model);
            return "movie/add/movie";
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
            return "movie/add/movie";
        } else {
            return "redirect:/movie";
        }
    }

    @PostMapping("/delete_movie/{id}")
    public String deleteMovie(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Optional<Movie> movie = movieService.findById(id);
        if (movie.isPresent()) {
            String movieName = movie.get().getTitle();
            try {
                movieService.deleteMovie(movie.get());
                redirectAttributes.addFlashAttribute("css", "success");
                redirectAttributes.addFlashAttribute("msg", movieName + " удалено");
            } catch (MovieDeleteException e) {
                redirectAttributes.addFlashAttribute("css", "alert");
                redirectAttributes.addFlashAttribute("msg", movieName + " не удалено: " + e.getCountOfSessions());
            }
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
        return "movie/all_genres";
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
            try {
                movieService.deleteGenre(genre.get());
                redirectAttributes.addFlashAttribute("css", "success");
                redirectAttributes.addFlashAttribute("msg", genreName + " удалено");
            } catch (GenreDeleteException e) {
                redirectAttributes.addFlashAttribute("css", "alert");
                redirectAttributes.addFlashAttribute("msg", genreName + " не удалено:" + e.getCountOfMovies());
            }
        }
        return "redirect:/movie/genre";
    }

    @GetMapping("/session")
    public String showAllSessions(Model model) {
        List<Session> sessions = sessionService.getAllSessions();
        model.addAttribute("sessions", sessions);
        return "movie/all_sessions";
    }

    @GetMapping("/add/session")
    public String addSession(Model model) {
        Session session = new Session();
        populateDefaultSessionModel(model);
        model.addAttribute("sessionForm", session);
        return "movie/add/session";
    }

    @PostMapping(value = "/add/session")
    public String saveOrUpdateSession(@ModelAttribute("sessionForm") @Validated Session session,
                                      BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            populateDefaultSessionModel(model);
            return "movie/add/session";
        } else {
            sessionService.saveOrUpdate(session);
            return "redirect:/movie/session";
        }
    }

    @GetMapping("/session/update_session/{id}")
    public String updateSession(@PathVariable("id") Long id, Model model) {
        Optional<Session> session = sessionService.findById(id);

        if (session.isPresent()) {
            populateDefaultSessionModel(model);
            model.addAttribute("sessionForm", session.get());
            return "movie/add/session";
        } else {
            return "movie/session";
        }
    }

    @PostMapping("/session/delete_session/{id}")
    public String deleteSession(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Optional<Session> session = sessionService.findById(id);

        if (session.isPresent()) {
            String sessionName = session.get().getMovie() + " " + session.get().getStartTime();
            sessionService.deleteSession(session.get());
            redirectAttributes.addFlashAttribute("css", "danger");
            redirectAttributes.addFlashAttribute("msg", sessionName + " удалено");
        }
        return "redirect:/movie/session";
    }

    private void populateDefaultSessionModel(Model model) {
        List<Hall> hallList = sessionService.getAllHalls();
        model.addAttribute("hallList", hallList);

        List<Movie> movieList = movieService.getAllMovies();
        model.addAttribute("movieList", movieList);
    }

    @GetMapping("/hall")
    public String showAllHalls(Model model) {
        model.addAttribute("halls", sessionService.getAllHalls());
        model.addAttribute("action", "show");
        return "movie/all_halls";
    }

    @GetMapping("/add/hall")
    public String addHall(Model model) {
        model.addAttribute("hallForm", new Hall());
        return "movie/add/hall";
    }

    @PostMapping(value = "/add/hall")
    public String saveOrUpdateHall(@ModelAttribute("hallForm") @Validated Hall hall,
                                   BindingResult result, final RedirectAttributes redirectAttributes) {
        String action;
        if (hall.isNew())
            action = "добавлен";
        else
            action = "изменен";
        if (result.hasErrors()) {
            return "movie/add/hall";
        } else {
            Hall hal1 = sessionService.saveOrUpdateHall(hall);
            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("msg", hal1.getName() + " " + action);
            return "redirect:/movie/hall";
        }
    }

    @GetMapping("/hall/update_hall/{id}")
    public String updateHall(@PathVariable("id") Long id, Model model) {
        Optional<Hall> hall = sessionService.findHallById(id);

        if (hall.isPresent()) {
            model.addAttribute("hallForm", hall.get());
            return "movie/add/hall";
        } else {
            return "movie/hall";
        }
    }

    @PostMapping("/hall/delete_hall/{id}")
    public String deleteHall(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Optional<Hall> hall = sessionService.findHallById(id);

        if (hall.isPresent()) {
            String hallName = hall.get().getName();
            try {
                sessionService.deleteHall(hall.get());
                redirectAttributes.addFlashAttribute("css", "danger");
                redirectAttributes.addFlashAttribute("msg", hallName + " удалено");
            } catch (HallDeleteException e) {
                redirectAttributes.addFlashAttribute("css", "danger");
                redirectAttributes.addFlashAttribute("msg", hallName + " не удалено:" + e.getCountOfSessions());
            }
        }
        return "redirect:/movie/hall";
    }



}
