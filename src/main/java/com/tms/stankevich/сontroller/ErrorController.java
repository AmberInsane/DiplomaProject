package com.tms.stankevich.сontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/err")
public class ErrorController {
    private static final String DEFAULT_ERROR_VIEW = "error";

    @GetMapping("")
    public ModelAndView showErrPage(HttpServletRequest request, HttpServletResponse response) {

        int httpErrorCode = response.getStatus();
        ModelAndView mav = new ModelAndView();

        mav.addObject("errorCode", "error." + httpErrorCode);
        mav.addObject("url", request.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }
}
