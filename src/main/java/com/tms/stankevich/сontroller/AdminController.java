package com.tms.stankevich.сontroller;

import com.tms.stankevich.domain.movie.Movie;
import com.tms.stankevich.domain.user.User;
import com.tms.stankevich.service.MovieServiceImpl;
import com.tms.stankevich.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.apache.log4j.Logger;

import java.util.List;

@Controller
@RequestMapping("/admin")
@SessionAttributes(value = "user")
public class AdminController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private MovieServiceImpl movieService;

    final static Logger logger = Logger.getLogger(AdminController.class);

    @GetMapping("/")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.getAllUsers());
        return "admin/admin";
    }

//    @PostMapping("/")
//    public String deleteUser(@RequestParam(required = true, defaultValue = "") Long userId,
//                             @RequestParam(required = true, defaultValue = "") String action,
//                             Model model) {
//        if (action.equals("delete")) {
//            userService.deleteUser(userId);
//        }
//        return "redirect:/admin";
//    }

//    @GetMapping("/gt/{userId}")
//    public String gtUser(@PathVariable("userId") Long userId, Model model) {
//        model.addAttribute("allUsers", userService.usergtList(userId));
//        return "admin/admin";
//    }

    @GetMapping("/manage")
    public String manageAdmins(Model model, @AuthenticationPrincipal User currentUser) {
        List<User> adminUsers = userService.getUsersByRole(UserServiceImpl.ADMIN_ROLE);
        System.out.println(currentUser);
        logger.debug(adminUsers.contains(currentUser));

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
        if (currentUser.getId() == userId) {
            return "admin/admin_manage_error";
        } else {
            userService.updateAdminRole(userId, "dalete");
        }
        return "redirect:/admin/manage";
    }

    @GetMapping("/movies")
    public String manageMovies(Model model) {
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "admin/admin_movies";
    }

    @GetMapping("/add_movie")
    public String addMovie(Model model) {
        model.addAttribute("movieForm", new Movie());
        model.addAttribute("action", "add");
        return "admin/movie";
    }

    @GetMapping("/update_movie/{id}")
    public String addMovie(@PathVariable Long id, Model model) {
        model.addAttribute("movieForm", movieService.getAllMovies());
        model.addAttribute("action", "add");
        logger.debug("movie update id");
        return "admin/movie";
    }
}