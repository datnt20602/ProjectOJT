package com.project.ojt.projectojt.controller;

import com.project.ojt.projectojt.entity.Feedback;
import com.project.ojt.projectojt.entity.Movies;
import com.project.ojt.projectojt.entity.Url;
import com.project.ojt.projectojt.entity.User;
import com.project.ojt.projectojt.service.FeedbackService;
import com.project.ojt.projectojt.service.MovieService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MovieDetailController {
    private final MovieService movieService;
    private final FeedbackService feedbackService;

    @GetMapping("/movie-detail/{movieId}")
    public ModelAndView movieDetail(@PathVariable Integer movieId, HttpServletRequest request) {
        // Lấy thông tin phim dựa trên movieId và truyền nó đến trang movie-detail.html
        Movies movie = movieService.getMovieById(movieId); // Chưa implement phương thức getMovieById
        List<Url> urls = movieService.getUrlByMovieId(movieId); // Thay vì `getUrlByMovieId`
        List<Feedback> feedback = feedbackService.getFeedbackByMovieId(movie);

        ModelAndView mav = new ModelAndView("movie-detail");
        mav.addObject("movie", movie);
        mav.addObject("urls", urls);
        mav.addObject("feedback", feedback);


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
