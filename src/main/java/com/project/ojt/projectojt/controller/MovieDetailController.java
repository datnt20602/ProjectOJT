package com.project.ojt.projectojt.controller;

import com.project.ojt.projectojt.entity.Feedback;
import com.project.ojt.projectojt.entity.Movies;
import com.project.ojt.projectojt.entity.Url;
import com.project.ojt.projectojt.entity.User;
import com.project.ojt.projectojt.service.FeedbackService;
import com.project.ojt.projectojt.service.MovieService;

import com.project.ojt.projectojt.service.UrlService;
import com.project.ojt.projectojt.service.UserService;
import jakarta.persistence.Convert;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MovieDetailController {
    private final MovieService movieService;
    private final FeedbackService feedbackService;
    private final UserService userService;
    private final UrlService urlService;

    @GetMapping("/movie-detail/{movieId}")
    public ModelAndView movieDetail(@PathVariable Integer movieId, HttpServletRequest request) {
        // Lấy thông tin phim dựa trên movieId và truyền nó đến trang movie-detail.html
        Movies movie = movieService.getMovieById(movieId); // implement phương thức getMovieById
        List<Url> urls = urlService.getUrlByMovieId(movieId); // Thay vì `getUrlByMovieId`
        List<Feedback> feedback = feedbackService.getFeedbackByMovieId(movie);

        ModelAndView mav = new ModelAndView("movie-detail");
        mav.addObject("movie", movie);
        mav.addObject("urls", urls);
        mav.addObject("feedback", feedback);

        User user = (User) request.getSession().getAttribute("user");
        mav.addObject("user", user);
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
    @PostMapping("/add-feedback/{movieId}")
    public ModelAndView addFeedback(@PathVariable Integer movieId,
                                    @RequestParam Double grade,
                                    @RequestParam String comment,
                                    HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        Movies movie = movieService.getMovieById(movieId);

        if (movie != null) {
            // Lấy userId từ session
            User user = ((User) request.getSession().getAttribute("user"));

            if (user != null) {
                // Tạo đối tượng Feedback và thiết lập thông tin
                Feedback feedback = Feedback.builder()
                        .grade(grade)
                        .comment(comment)
                        .movieId(movie)
                        .user(user)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();

                // Thêm Feedback vào cơ sở dữ liệu
                feedbackService.addFeedback(feedback);

                // Chuyển hướng đến trang chi tiết phim sau khi đăng feedback
                mav.setViewName("redirect:/movie-detail/" + movieId);
            } else {
                // Xử lý trường hợp người dùng chưa đăng nhập
                mav.setViewName("redirect:/login");
            }
        } else {
            // Xử lý trường hợp không tìm thấy bộ phim
            mav.addObject("errorMessage", "Bộ phim không tồn tại");
            mav.setViewName("error");
        }

        return mav;
    }

    @PostMapping("/add-url/{movieId}")
    public ModelAndView addUrl(@PathVariable Integer movieId,
                                    @RequestParam String link,
                                    HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        Movies movie = movieService.getMovieById(movieId);

        if (movie != null) {
            // Lấy userId từ session
            User user = ((User) request.getSession().getAttribute("user"));

            if (user != null) {
                // Tạo đối tượng Feedback và thiết lập thông tin
                Url url = Url.builder()
                        .movieId(movie.getMovieId())
                        .urlValue(link)
                        .user(user)
                        .build();

                // Thêm Feedback vào cơ sở dữ liệu
                urlService.addUrl(url);

                // Chuyển hướng đến trang chi tiết phim sau khi đăng feedback
                mav.setViewName("redirect:/movie-detail/" + movieId);
            } else {
                // Xử lý trường hợp người dùng chưa đăng nhập
                mav.setViewName("redirect:/login");
            }
        } else {
            // Xử lý trường hợp không tìm thấy bộ phim
            mav.addObject("errorMessage", "Bộ phim không tồn tại");
            mav.setViewName("error");
        }

        return mav;
    }




}

