package com.tms.stankevich.сontroller;

import com.tms.stankevich.domain.movie.Session;
import com.tms.stankevich.domain.movie.Ticket;
import com.tms.stankevich.domain.user.User;
import com.tms.stankevich.service.MovieServiceImpl;
import com.tms.stankevich.service.SessionServiceImpl;
import com.tms.stankevich.service.TicketService;
import com.tms.stankevich.service.UserService;
import com.tms.stankevich.validator.HallFormValidator;
import com.tms.stankevich.validator.SessionFormValidator;
import com.tms.stankevich.validator.TicketFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    private SessionServiceImpl sessionService;

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    TicketFormValidator ticketFormValidator;

    @InitBinder("ticketForm")
    protected void initBinderMovie(WebDataBinder binder) {
        binder.setValidator(ticketFormValidator);
    }

    @GetMapping("/{id}")
    public String showTicket(Model model, @PathVariable Long id, HttpServletRequest request, @AuthenticationPrincipal User user) {
        Optional<Ticket> ticket = ticketService.findById(id);
        if (ticket.isPresent()) {
            if (ticket.get().getUserFor().equals(user)) {
                model.addAttribute("ticket", ticket.get());
                return "movie/show/ticket";
            }
        }
        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping("{session_id}/buy")
    public String buyTicket(Model model, @PathVariable("session_id") Long sessionId, HttpServletRequest request, @AuthenticationPrincipal User user) {
        Optional<Session> session = sessionService.findById(sessionId);
        if (session.isPresent()) {
            Ticket ticket = new Ticket();
            ticket.setUserBy(user);
            ticket.setSession(session.get());
            model.addAttribute("ticketForm", ticket);
            List<User> usersFor = new ArrayList<>();
            usersFor.add(user);
            usersFor.addAll(userService.findUserById(user.getId()).getFriends());
            model.addAttribute("friends", usersFor);
            return "movie/add/ticket";
        }
        return "redirect:" + request.getHeader("Referer");
    }

    @PostMapping("/add")
    public String addTicket(@ModelAttribute("ticketForm") @Validated Ticket rawTicket,
                            BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "ticket/" + rawTicket.getSession().getId() + "/buy";
        } else {
            ticketService.saveTickets(rawTicket);

            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("msg", "Ticket added successfully!");
            return "redirect:/user/my_tickets";
        }
    }
    }
