package com.tms.stankevich.сontroller;

import com.tms.stankevich.domain.user.User;
import com.tms.stankevich.service.UserServiceImpl;
import com.tms.stankevich.validator.RegistrationFormValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class RegistrationController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    RegistrationFormValidator registrationFormValidator;

    @InitBinder("userForm")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(registrationFormValidator);
    }

    Logger logger = Logger.getLogger(RegistrationController.class);

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    /*@PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Validated User userForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "registration";
        }
        if (!userService.saveUser(userForm)) {
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "registration";
        }

        return "redirect:/";
    }*/

    // save or update user
    // 1. @ModelAttribute bind form value
    // 2. @Validated form validator
    // 3. RedirectAttributes for flash value
    @PostMapping("/registration")
    public String saveOrUpdateUser(@ModelAttribute("userForm") @Validated User user,
                                   BindingResult result, Model model,
                                   final RedirectAttributes redirectAttributes) {

        logger.debug("saveOrUpdateUser() : {}" + user);

        if (result.hasErrors()) {
            return "registration";
        } else {
            userService.saveOrUpdate(user);
            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("msg", "User added successfully!");
            // POST/REDIRECT/GET
            return "redirect:/";

            // POST/FORWARD/GET
            // return "user/list";

        }

    }
}