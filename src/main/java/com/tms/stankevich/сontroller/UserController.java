package com.tms.stankevich.сontroller;

import com.tms.stankevich.domain.movie.Ticket;
import com.tms.stankevich.domain.movie.TicketType;
import com.tms.stankevich.domain.user.FriendRequest;
import com.tms.stankevich.domain.user.User;
import com.tms.stankevich.domain.user.UserInfo;
import com.tms.stankevich.exception.FriendRequestException;
import com.tms.stankevich.service.TicketService;
import com.tms.stankevich.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    @GetMapping("/my_profile")
    public String showMyPage(Model model, @AuthenticationPrincipal User currentUser, HttpServletRequest servletRequest) {
        return showUserPage(currentUser.getId(), model, currentUser, servletRequest);
    }

    @GetMapping("/my_edit")
    public String editUser(Model model, @AuthenticationPrincipal User currentUser) {
        UserInfo userInfo = userService.findUserById(currentUser.getId()).get().getInfo();
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
        User user = userService.findUserById(currentUser.getId()).get();

        model.addAttribute("inRequests", userService.findInFriendRequests(user));
        model.addAttribute("friends", user.getFriends());
        model.addAttribute("outRequests", userService.findOutFriendRequests(user));
        return "user/friends";
    }

    @GetMapping("/my_tickets")
    public String showMyTickets(Model model, @AuthenticationPrincipal User currentUser) {
        Map<TicketType, List<Ticket>> ticketMap = ticketService.findUsersTickets(currentUser);
        model.addAttribute("tickets", ticketMap.get(TicketType.MY));
        model.addAttribute("ticketsOld", ticketMap.get(TicketType.MY_OLD));
        model.addAttribute("ticketsByFriends", ticketMap.get(TicketType.BY_FRIEND));
        model.addAttribute("ticketsByFriendsOld", ticketMap.get(TicketType.BY_FRIEND_OLD));
        model.addAttribute("ticketsForFriends", ticketMap.get(TicketType.FOR_FRIEND));
        model.addAttribute("ticketsForFriendsOld", ticketMap.get(TicketType.FOR_FRIEND_OLD));
        return "user/tickets";
    }

    @GetMapping("/my_purse")
    public String showMyPurse(Model model, @AuthenticationPrincipal User currentUser) {
        model.addAttribute("balance", userService.findUserById(currentUser.getId()).get().getBalance());
        return "user/purse";
    }

    @PostMapping("/my_purse")
    public String addMoneyMyPurse(@RequestParam("sum") String sum, Model model, @AuthenticationPrincipal User currentUser, final RedirectAttributes redirectAttributes) {
        BigDecimal sumNumber;
        try {
            sumNumber = new BigDecimal(sum);
        } catch (NumberFormatException e) {
            sumNumber = BigDecimal.ZERO;
        }
        if (sumNumber.compareTo(BigDecimal.ZERO) <= 0) {
            redirectAttributes.addFlashAttribute("css", "danger");
            redirectAttributes.addFlashAttribute("msg_code", "messages.invalid.sum");
        } else {
            sumNumber = sumNumber.setScale(2, BigDecimal.ROUND_HALF_DOWN);
            userService.plusToBalance(currentUser, sumNumber);
            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("msg_code", "messages.add.success");
        }
        return "redirect:/user/my_purse";
    }

    @GetMapping("/{user_id}")
    public String showUserPage(@PathVariable("user_id") Long userId, Model model, @AuthenticationPrincipal User currentUser, HttpServletRequest servletRequest) {
        Optional<User> optionalUser = userService.findUserById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (!userService.isUserAdmin(user) || userService.isUserAdmin(currentUser)) {
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

                    model.addAttribute("blocked", userService.isUserBlockedSecond(currentUser, user));
                    model.addAttribute("youBlocked", userService.isUserBlockedSecond(user, currentUser));

                    model.addAttribute("isFriend", currentUser.getFriends().contains(user));
                }
                return "user/user_info";
            }
        }
        return "redirect:/";
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
                model.addAttribute("msg_code", "messages.found");
                model.addAttribute("count", foundUsers.size());
                model.addAttribute("count_type_code", "user.form5");

                model.addAttribute("friend_list", foundUsers);
            } else {
                model.addAttribute("css", "danger");
                model.addAttribute("msg_code", "text.not.found");
            }
        } else {
            model.addAttribute("css", "danger");
            model.addAttribute("msg_code", "messages.enter.name");
        }
        return "user/find_friend";
    }

    @PostMapping("/friend/{user_id}/send")
    public String sendFriendRequest(@AuthenticationPrincipal User currentUser, @PathVariable("user_id") Long userId, final RedirectAttributes redirectAttributes) {
        User friend = userService.findUserById(userId).get();
        try {
            userService.sendFriendRequest(currentUser, friend);
        } catch (FriendRequestException e) {
            redirectAttributes.addFlashAttribute("css", "danger");
            redirectAttributes.addFlashAttribute("msg_code", "message.request.deny");
            redirectAttributes.addFlashAttribute("cause_code", e.getMessage());
            return "redirect:/user/" + userId;
        }
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg_code", "message.request.send");
        return "redirect:/user/" + userId;
    }

    @PostMapping("/friend/{user_id}/delete")
    public String deleteFriend(Model model, @AuthenticationPrincipal User currentUser, @PathVariable("user_id") Long userId, final RedirectAttributes redirectAttributes) {
        User userToBlock = userService.findUserById(userId).get();
        userService.deleteFromFriends(currentUser, userToBlock);

        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg_code", "message.request.remove");
        return "redirect:/user/" + userId;
    }

    @PostMapping("/friend/{user_id}/block")
    public String blockUser(Model model, @AuthenticationPrincipal User currentUser, @PathVariable("user_id") Long userId, final RedirectAttributes redirectAttributes) {
        User userToBlock = userService.findUserById(userId).get();
        userService.blockUser(currentUser, userToBlock);

        redirectAttributes.addFlashAttribute("css", "danger");
        redirectAttributes.addFlashAttribute("msg_code", "message.request.block");
        return "redirect:/user/" + userId;
    }

    @PostMapping("/friend/{user_id}/unblock")
    public String unblockUser(Model model, @AuthenticationPrincipal User currentUser, @PathVariable("user_id") Long userId, final RedirectAttributes redirectAttributes) {
        User userToUnblock = userService.findUserById(userId).get();
        userService.unblockUser(currentUser, userToUnblock);

        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg_code", "message.request.unblock");
        return "redirect:/user/" + userId;
    }

    @PostMapping("/request/accept/{request_id}")
    public String acceptFriendRequest(HttpServletRequest request, @AuthenticationPrincipal User currentUser, @PathVariable("request_id") Long requestId, final RedirectAttributes redirectAttributes) {
        Optional<FriendRequest> friendRequest = userService.findFriendRequestById(requestId);
        if (friendRequest.isPresent()) {
            userService.acceptFriendRequest(userService.findUserById(currentUser.getId()).get(), friendRequest.get());
        }
        return "redirect:" + request.getHeader("Referer");
    }

    @PostMapping("/request/refuse/{request_id}")
    public String refuseFriendRequest(HttpServletRequest request, @AuthenticationPrincipal User currentUser, @PathVariable("request_id") Long requestId, final RedirectAttributes redirectAttributes) {
        Optional<FriendRequest> friendRequest = userService.findFriendRequestById(requestId);
        if (friendRequest.isPresent()) {
            userService.refuseFriendRequest(userService.findUserById(currentUser.getId()).get(), friendRequest.get());
        }
        return "redirect:" + request.getHeader("Referer");
    }

    @PostMapping("/request/cancel/{request_id}")
    public String cancelFriendRequest(HttpServletRequest request, @AuthenticationPrincipal User currentUser, @PathVariable("request_id") Long requestId, final RedirectAttributes redirectAttributes) {
        Optional<FriendRequest> friendRequest = userService.findFriendRequestById(requestId);
        if (friendRequest.isPresent()) {
            userService.cancelFriendRequest(userService.findUserById(currentUser.getId()).get(), friendRequest.get());
        }
        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping("/find_sessions")
    public String showMovieSessions() {
        return "redirect:/movie/session";
    }

    @GetMapping("/find_movies")
    public String showMovies() {
        return "redirect:/movie";
    }
}
