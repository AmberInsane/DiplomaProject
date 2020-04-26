package com.tms.stankevich.сontroller;

import com.tms.stankevich.domain.user.FriendRequest;
import com.tms.stankevich.domain.user.User;
import com.tms.stankevich.domain.user.UserInfo;
import com.tms.stankevich.exception.FriendRequestException;
import com.tms.stankevich.service.UserService;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/my_profile")
    public String showMyPage(Model model, @AuthenticationPrincipal User currentUser) {
        return showUserPage(currentUser.getId(), model, currentUser);
    }

    @GetMapping("/{user_id}")
    public String showUserPage(@PathVariable("user_id") Long userId, Model model, @AuthenticationPrincipal User currentUser) {
        User user = userService.findUserById(userId);
        model.addAttribute("user", user);
        if (userId.equals(currentUser.getId())) {
            model.addAttribute("requestsNum", userService.findInFriendRequests(user).size());
        } else {
            //response - current user have in responses user
            List<FriendRequest> inFriendRequests = userService.findInFriendRequests(currentUser);
            Optional<FriendRequest> response = inFriendRequests.stream().filter(friendRequest -> friendRequest.getUserRequest().equals(user)).findAny();
            if (response.isPresent())
                model.addAttribute("nowResponse", response.get());

            //request - current user have in requests user
            List<FriendRequest> outFriendRequests = userService.findOutFriendRequests(currentUser);
            Optional<FriendRequest> request = outFriendRequests.stream().filter(friendRequest -> friendRequest.getUserResponse().equals(user)).findAny();
            if (request.isPresent())
                model.addAttribute("nowRequest", request.get());
        }
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
        } else {
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

    @PostMapping("/friend/{user_id}/black")
    public String sendToBlackList(Model model, @AuthenticationPrincipal User currentUser, @PathVariable("user_id") Long userId, final RedirectAttributes redirectAttributes) {
        User userToBlock = userService.findUserById(userId);
        userService.sendToBlackList(currentUser, userToBlock);

        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "Пользователь добавлен в черный список");
        return "redirect:/user/" + userId;
    }

    @GetMapping("/my_edit")
    public String editUser(Model model, @AuthenticationPrincipal User currentUser) {

        UserInfo userInfo = userService.findUserById(currentUser.getId()).getInfo();
        if (userInfo == null) {
            userInfo = new UserInfo();
            currentUser.setInfo(userInfo);
            userInfo = userService.saveOrUpdate(currentUser).getInfo();
        }

        model.addAttribute("userInfoForm", userInfo);
        return "user/user_edit";
    }

    @PostMapping("/my_edit")
    public String saveEditUser(@ModelAttribute("userInfoForm") UserInfo userInfo) {
        userService.saveOrUpdateUserInfo(userInfo);
        return "redirect:/user/my_profile";
    }

    @GetMapping("/my_friends")
    public String showMyFriends(Model model, @AuthenticationPrincipal User currentUser) {
        User user = userService.findUserById(currentUser.getId());

        model.addAttribute("inRequests", userService.findInFriendRequests(user));
        model.addAttribute("friends", user.getFriends());
        model.addAttribute("outRequests", userService.findOutFriendRequests(user));
        return "user/friends";
    }


}
