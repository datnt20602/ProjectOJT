package com.project.ojt.projectojt.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        session.invalidate(); // Invalidate the current session to log the user out
        ModelAndView modelAndView = new ModelAndView("redirect:/home"); // Redirect to the login page
        return modelAndView;
    }
}