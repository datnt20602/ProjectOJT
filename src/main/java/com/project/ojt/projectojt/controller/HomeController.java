package com.project.ojt.projectojt.controller;

import com.project.ojt.projectojt.entity.Movies;
import com.project.ojt.projectojt.entity.User;
import com.project.ojt.projectojt.service.FeedbackService;
import com.project.ojt.projectojt.service.MovieService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MovieService movieService;
    private final FeedbackService feedbackService;

    @GetMapping(path = {"/home","/"})
    public ModelAndView home(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        ModelAndView mav = new ModelAndView("home");
        if (user != null) {
            mav.addObject("name", user.getName());
        }

        mav.addObject("movies", movieService.getAll());
        mav.addObject("recentReviews", feedbackService.getRecentReview());
        return mav;
    }

    @GetMapping("/search")
    public ModelAndView searchMovies(@RequestParam("searchTerm") String searchTerm, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("home");
        User user = (User) request.getSession().getAttribute("user");

        if (user != null) {
            mav.addObject("name", user.getName());
        }

        mav.addObject("movies", movieService.searchMovies(searchTerm));
        mav.addObject("recentReviews", feedbackService.getRecentReview());
        mav.addObject("searchTerm", searchTerm);

        return mav;
    }




}
