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

    @GetMapping("/movie-detail/{movieId}")
    public ModelAndView movieDetail(@PathVariable Integer movieId, HttpServletRequest request) {
        // Lấy thông tin phim dựa trên movieId và truyền nó đến trang movie-detail.html
        Movies movie = movieService.getMovieById(movieId); // Chưa implement phương thức getMovieById
        ModelAndView mav = new ModelAndView("movie-detail");
        mav.addObject("movie", movie);

        User user = (User) request.getSession().getAttribute("user");
        if (movie != null) {
            mav.addObject("movie", movie);
            if (user != null) {
                mav.addObject("name", user.getName());
            }
        } else {
            // Xử lý trường hợp không tìm thấy bộ phim
            mav.addObject("errorMessage", "Bộ phim không tồn tại");
        }

        // Thực hiện bất kỳ công việc nào khác cần thiết
        return mav;
    }


}
