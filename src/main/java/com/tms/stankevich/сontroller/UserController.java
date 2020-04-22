package com.tms.stankevich.сontroller;

import com.tms.stankevich.domain.user.User;
import com.tms.stankevich.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/my_profile")
    public String showMyPage(Model model, @AuthenticationPrincipal User currentUser) {
        return showUserPage(currentUser.getId(), model);
    }

    @GetMapping("/{user_id}")
    public String showUserPage(@PathVariable("user_id") Long userId, Model model) {
        model.addAttribute("friend_list", new ArrayList<User> ());
        return "user/user_info";
    }

    @GetMapping("/find_friends")
    public String showFindFriends(Model model) {
        return "user/find_friend";
    }

    @PostMapping("/find_friends")
    public String getFindFriends(@RequestParam("friend_name") String userName, Model model) {
        if (!userName.equals("")) {
            List<User> foundUsers = userService.findUsersByName(userName);
            if (foundUsers.size() > 0) {
                model.addAttribute("css", "success");
                model.addAttribute("msg", "я нашел целых смотри сколько " + foundUsers.size() + " :)");
                model.addAttribute("friend_list", foundUsers);
            } else {
                model.addAttribute("css", "danger");
                model.addAttribute("msg", "я никого не нашел с именем " + userName + " :(");
            }
        } else{
            model.addAttribute("css", "danger");
            model.addAttribute("msg", "Ведите имя для поиска");
        }
        return "user/find_friend";
    }
}
