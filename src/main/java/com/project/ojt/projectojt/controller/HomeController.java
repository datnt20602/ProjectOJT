package com.project.ojt.projectojt.controller;

import com.project.ojt.projectojt.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    @GetMapping(path = {"/home","/"})
    public ModelAndView home(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        ModelAndView mav = new ModelAndView("home");
        if (user != null) {
            mav.addObject("name", user.getName());
        }
        return mav;
    }
}
