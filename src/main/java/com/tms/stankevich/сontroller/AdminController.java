package com.tms.stankevich.сontroller;

import com.tms.stankevich.domain.movie.Movie;
import com.tms.stankevich.domain.user.User;
import com.tms.stankevich.service.MovieServiceImpl;
import com.tms.stankevich.service.UserServiceImpl;
import com.tms.stankevich.validator.MovieFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
@SessionAttributes(value = "user")
public class AdminController {
    @Autowired
    private UserServiceImpl userService;

    final static Logger logger = Logger.getLogger(AdminController.class);

    @GetMapping("/")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.getAllUsers());
        return "admin/admin";
    }

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

    @GetMapping("/session")
    public String manageSession() {
        return "redirect:/movie/session";
    }
}