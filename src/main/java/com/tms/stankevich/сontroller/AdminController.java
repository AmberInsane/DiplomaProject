package com.tms.stankevich.сontroller;

import com.tms.stankevich.domain.movie.Genre;
import com.tms.stankevich.domain.movie.Hall;
import com.tms.stankevich.domain.movie.Movie;
import com.tms.stankevich.domain.movie.Session;
import com.tms.stankevich.domain.user.User;
import com.tms.stankevich.exception.GenreDeleteException;
import com.tms.stankevich.exception.HallDeleteException;
import com.tms.stankevich.exception.MovieDeleteException;
import com.tms.stankevich.exception.SessionDeleteException;
import com.tms.stankevich.service.*;
import com.tms.stankevich.validator.GenreFormValidator;
import com.tms.stankevich.validator.HallFormValidator;
import com.tms.stankevich.validator.MovieFormValidator;
import com.tms.stankevich.validator.SessionFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@SessionAttributes(value = "user")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private MovieFormValidator movieFormValidator;

    @InitBinder("movieForm")
    protected void initBinderMovie(WebDataBinder binder) {
        binder.setValidator(movieFormValidator);
    }

    @Autowired
    private GenreFormValidator genreFormValidator;

    @InitBinder("genreForm")
    protected void initBinderGenre(WebDataBinder binder) {
        binder.setValidator(genreFormValidator);
    }

    @Autowired
    SessionFormValidator sessionFormValidator;

    @InitBinder("sessionForm")
    protected void initBinderSession(WebDataBinder binder) {
        binder.setValidator(sessionFormValidator);
    }

    @Autowired
    HallFormValidator hallFormValidator;

    @InitBinder("hallForm")
    protected void initBinderHall(WebDataBinder binder) {
        binder.setValidator(hallFormValidator);
    }

    @GetMapping("/")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.getAllUsers());
        return "admin/admin";
    }

    @GetMapping("/manage")
    public String manageAdmins(Model model, @AuthenticationPrincipal User currentUser) {
        List<User> adminUsers = userService.getUsersByRole(UserServiceImpl.ADMIN_ROLE);
        System.out.println(currentUser);
        model.addAttribute("adminUsers", adminUsers);
        model.addAttribute("users", userService.getUsersByRole(UserServiceImpl.USER_ROLE));
        model.addAttribute("allUsers", userService.getAllUsers());
        return "admin/admin_manage";
    }

    @GetMapping("/manage/add_admin/{userId}")
    public String manageAdmins(@PathVariable Long userId) {
        userService.updateAdminRole(userId, "add");
        return "redirect:/admin/manage";
    }

    @GetMapping("/manage/delete_admin/{userId}")
    public String manageAdmins(@PathVariable Long userId,
                               @AuthenticationPrincipal User currentUser) {
        if (currentUser.getId().equals(userId)) {
            return "admin/admin_manage_error";
        } else {
            userService.updateAdminRole(userId, "delete");
        }
        return "redirect:/admin/manage";
    }

    @GetMapping("/movies")
    public String manageMovies() {
        return "redirect:/movie";
    }

    private void populateDefaultMovieModel(Model model) {
        List<Genre> genresList = movieService.getAllGenres();
        model.addAttribute("genreList", genresList);
    }

    @GetMapping("/movie/add")
    public String addMovie(Model model) {
        Movie movie = new Movie();
        model.addAttribute("movieForm", movie);
        populateDefaultMovieModel(model);
        return "movie/add/movie";
    }

    @PostMapping(value = "/movie/add")
    public String saveOrUpdateMovie(@ModelAttribute("movieForm") @Validated Movie movie,
                                    BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            populateDefaultMovieModel(model);
            return "movie/add/movie";
        } else {
            redirectAttributes.addFlashAttribute("css", "success");
            if (movie.isNew()) {
                redirectAttributes.addFlashAttribute("msg_code", "messages.add.success");
            } else {
                redirectAttributes.addFlashAttribute("msg_code", "messages.update.success");
            }
            movieService.saveOrUpdate(movie);
            return "redirect:/movie";
        }
    }

    @GetMapping("/movie/update/{id}")
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

    @PostMapping("/movie/delete/{id}")
    public String deleteMovie(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Optional<Movie> movie = movieService.findById(id);
        if (movie.isPresent()) {
            String movieName = movie.get().getTitle();
            try {
                movieService.deleteMovie(movie.get());
                redirectAttributes.addFlashAttribute("css", "success");
                redirectAttributes.addFlashAttribute("msg_code", "messages.delete.success");
            } catch (MovieDeleteException e) {
                redirectAttributes.addFlashAttribute("css", "alert");
                redirectAttributes.addFlashAttribute("msg_code", "messages.delete.error");
                redirectAttributes.addFlashAttribute("count", e.getCountOfSessions());
                redirectAttributes.addFlashAttribute("count_type_code", "session.form5");
            }
        }
        return "redirect:/movie";
    }

    @GetMapping("/genre")
    public String showAllGenres(Model model) {
        model.addAttribute("genres", movieService.getAllGenres());
        return "movie/all_genres";
    }

    @GetMapping("/genre/add")
    public String addGenre(Model model) {
        model.addAttribute("genreForm", new Genre());
        return "movie/add/genre";
    }

    @PostMapping(value = "/genre/add")
    public String saveOrUpdateGenre(@ModelAttribute("genreForm") @Validated Genre genre,
                                    BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            populateDefaultSessionModel(model);
            return "movie/add/session";
        } else {
            redirectAttributes.addFlashAttribute("css", "success");
            if (genre.isNew()) {
                redirectAttributes.addFlashAttribute("msg_code", "messages.add.success");
            } else {
                redirectAttributes.addFlashAttribute("msg_code", "messages.update.success");
            }
            movieService.saveOrUpdateGenre(genre);
            return "redirect:/admin/genre";
        }
    }

    @GetMapping("/genre/update/{id}")
    public String updateGenre(@PathVariable("id") Long id, Model model) {
        Optional<Genre> genre = movieService.findGenreById(id);
        if (genre.isPresent()) {
            model.addAttribute("genreForm", genre.get());
            return "movie/add/genre";
        } else {
            return "redirect:/movie";
        }
    }

    @PostMapping("/genre/delete/{id}")
    public String deleteGenre(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Optional<Genre> genre = movieService.findGenreById(id);
        if (genre.isPresent()) {
            try {
                movieService.deleteGenre(genre.get());
                redirectAttributes.addFlashAttribute("css", "success");
                redirectAttributes.addFlashAttribute("msg_code", "messages.delete.success");
            } catch (GenreDeleteException e) {
                redirectAttributes.addFlashAttribute("css", "alert");
                redirectAttributes.addFlashAttribute("msg_code", "messages.delete.error");
                redirectAttributes.addFlashAttribute("count", e.getCountOfMovies());
                redirectAttributes.addFlashAttribute("count_type_code", "genre.form5");
            }
        }
        return "redirect:/admin/genre";
    }

    @GetMapping("/session")
    public String manageSession() {
        return "redirect:/movie/session";
    }

    @GetMapping("/session/add")
    public String addSession(Model model) {
        Session session = new Session();
        populateDefaultSessionModel(model);
        model.addAttribute("sessionForm", session);
        return "movie/add/session";
    }

    @PostMapping(value = "/session/add")
    public String saveOrUpdateSession(@ModelAttribute("sessionForm") @Validated Session session,
                                      BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            populateDefaultSessionModel(model);
            return "movie/add/session";
        } else {
            sessionService.saveOrUpdate(session);
            return "redirect:/admin/session";
        }
    }

    @GetMapping("/session/update/{id}")
    public String updateSession(@PathVariable("id") Long id, Model model) {
        Optional<Session> session = sessionService.findById(id);

        if (session.isPresent()) {
            populateDefaultSessionModel(model);
            model.addAttribute("sessionForm", session.get());
            return "movie/add/session";
        } else {
            return "redirect:/movie/session";
        }
    }

    @PostMapping("/session/delete/{id}")
    public String deleteSession(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Optional<Session> session = sessionService.findById(id);

        if (session.isPresent()) {
            String sessionName = session.get().getMovie() + " " + session.get().getStartTime();
            try {
                sessionService.deleteSession(session.get());
                redirectAttributes.addFlashAttribute("css", "success");
                redirectAttributes.addFlashAttribute("msg_code", "messages.delete.success");
            } catch (SessionDeleteException e) {
                redirectAttributes.addFlashAttribute("css", "alert");
                redirectAttributes.addFlashAttribute("msg_code", "messages.delete.error");
                redirectAttributes.addFlashAttribute("count", e.getCountOfTickets());
                redirectAttributes.addFlashAttribute("count_type_code", "ticket.form5");
            }
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

    @GetMapping("/hall/add")
    public String addHall(Model model) {
        model.addAttribute("hallForm", new Hall());
        return "movie/add/hall";
    }

    @PostMapping(value = "/hall/add")
    public String saveOrUpdateHall(@ModelAttribute("hallForm") @Validated Hall hall,
                                   BindingResult result, final RedirectAttributes redirectAttributes) {
        String action;
        if (result.hasErrors()) {
            return "movie/add/hall";
        } else {
            sessionService.saveOrUpdateHall(hall);
            redirectAttributes.addFlashAttribute("css", "success");
            if (hall.isNew()) {
                redirectAttributes.addFlashAttribute("msg_code", "messages.add.success");
            } else {
                redirectAttributes.addFlashAttribute("msg_code", "messages.update.success");
            }
            return "redirect:/admin/hall";
        }
    }

    @GetMapping("/hall/update/{id}")
    public String updateHall(@PathVariable("id") Long id, Model model) {
        Optional<Hall> hall = sessionService.findHallById(id);

        if (hall.isPresent()) {
            model.addAttribute("hallForm", hall.get());
            return "movie/add/hall";
        } else {
            return "admin/hall";
        }
    }

    @PostMapping("/hall/delete/{id}")
    public String deleteHall(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Optional<Hall> hall = sessionService.findHallById(id);

        if (hall.isPresent()) {
            String hallName = hall.get().getName();
            try {
                sessionService.deleteHall(hall.get());
                redirectAttributes.addFlashAttribute("css", "success");
                redirectAttributes.addFlashAttribute("msg_code", "messages.delete.success");
            } catch (HallDeleteException e) {
                redirectAttributes.addFlashAttribute("css", "alert");
                redirectAttributes.addFlashAttribute("msg_code", "messages.delete.error");
                redirectAttributes.addFlashAttribute("count", e.getCountOfSessions());
                redirectAttributes.addFlashAttribute("count_type_code", "session.form5");
            }
        }
        return "redirect:/admin/hall";
    }
}