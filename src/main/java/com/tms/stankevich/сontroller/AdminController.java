package com.tms.stankevich.сontroller;

import com.tms.stankevich.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.apache.log4j.Logger;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    final static Logger logger = Logger.getLogger(AdminController.class);

    @GetMapping("/")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "admin/admin";
    }

    @PostMapping("/")
    public String  deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
                              @RequestParam(required = true, defaultValue = "" ) String action,
                              Model model) {
        if (action.equals("delete")){
            userService.deleteUser(userId);
        }
        return "redirect:/admin";
    }

    @GetMapping("/gt/{userId}")
    public String gtUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("allUsers", userService.usergtList(userId));
        return "admin/admin";
    }

    @GetMapping("/manage")
    public String manageAdmins(Model model) {
        model.addAttribute("adminUsers", userService.getUsersByRole("ROLE_ADMIN"));
        model.addAttribute("users", userService.getUsersByRole("ROLE_USER"));
        model.addAttribute("allUsers", userService.allUsers());
        return "admin/admin_manage";
    }

    @PostMapping("/manage")
    public String manageAdmins(@RequestParam(required = true, defaultValue = "" ) Long userId,
                               @RequestParam(required = true, defaultValue = "" ) String action) {
        logger.info("manageAdmins " + action + " " + userId);
//        if (action.equals("delete")){
//
//            // userService.deleteUser(userId);
//        }
        return "redirect:/admin/manage";
    }
}