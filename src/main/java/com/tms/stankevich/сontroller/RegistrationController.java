package com.tms.stankevich.сontroller;

import com.tms.stankevich.domain.user.User;
import com.tms.stankevich.service.UserServiceImpl;
import com.tms.stankevich.validator.RegistrationFormValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private final Logger logger = LogManager.getLogger(RegistrationController.class.getName());

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RegistrationFormValidator registrationFormValidator;

    @InitBinder("userForm")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(registrationFormValidator);
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        logger.debug("ololo");
        logger.info("ololo");
        logger.error("ololo");
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String saveOrUpdateUser(@ModelAttribute("userForm") @Validated User user,
                                   BindingResult result, final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "registration";
        } else {
            userService.saveOrUpdate(user);
            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("msg_code", "messages.add.success");
            return "redirect:/";
        }

    }
}