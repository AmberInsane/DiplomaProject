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

import javax.validation.Valid;


@Controller
public class RegistrationController {

    @Autowired
    private UserServiceImpl userService;


    @Autowired
    RegistrationFormValidator userFormValidator;

    Logger logger = Logger.getLogger(RegistrationController.class);


    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(userFormValidator);
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, Model model) {

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
    }

    @PostMapping("/registration1")
    public String saveOrUpdateUser(@ModelAttribute("userForm") @Validated User user,
                                   BindingResult result, Model model, final RedirectAttributes redirectAttributes) {

        logger.debug("saveOrUpdateUser()");

        if (result.hasErrors()) {
            //populateDefaultModel(model);
            return "users/userform";
        } else {

          /*  redirectAttributes.addFlashAttribute("css", "success");
            if(user.isNew()){
                redirectAttributes.addFlashAttribute("msg", "User added successfully!");
            }else{
                redirectAttributes.addFlashAttribute("msg", "User updated successfully!");
            }

            userService.saveOrUpdate(user);

            // POST/REDIRECT/GET
            return "redirect:/users/" + user.getId();

            // POST/FORWARD/GET
            // return "user/list";*/


        }
        return "redirect:/";
    }
}