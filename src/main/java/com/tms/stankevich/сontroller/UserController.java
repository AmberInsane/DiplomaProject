package com.tms.stankevich.сontroller;

import com.tms.stankevich.domain.user.FriendRequest;
import com.tms.stankevich.domain.user.User;
import com.tms.stankevich.exception.FriendRequestException;
import com.tms.stankevich.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        User user = userService.findUserById(userId);
        model.addAttribute("user", user);
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

    @PostMapping("/friend/{user_id}/send")
    public String sendFriendRequest(Model model, @AuthenticationPrincipal User currentUser, @PathVariable("user_id") Long userId, final RedirectAttributes redirectAttributes) {
        User friend = userService.findUserById(userId);
        try {
            userService.sendFriendRequest(currentUser, friend);
        } catch (FriendRequestException e) {
            redirectAttributes.addFlashAttribute("css", "danger");
            redirectAttributes.addFlashAttribute("msg", "Отказано в отправке запроса: " + e.getMessage());
            return "redirect:/user/" + userId;
        }
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "Запрос отправлен");
        return "redirect:/user/" + userId;
    }

}
