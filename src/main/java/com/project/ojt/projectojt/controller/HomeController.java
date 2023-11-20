package com.project.ojt.projectojt.controller;

import com.project.ojt.projectojt.entity.Feedback;
import com.project.ojt.projectojt.entity.Movies;
import com.project.ojt.projectojt.entity.User;
import com.project.ojt.projectojt.service.FeedbackService;
import com.project.ojt.projectojt.service.MovieService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Date;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MovieService movieService;
    private final FeedbackService feedbackService;

//    @GetMapping(path = {"/home","/"})
//    public ModelAndView home(HttpServletRequest request) {
//        User user = (User) request.getSession().getAttribute("user");
//        ModelAndView mav = new ModelAndView("home");
//        if (user != null) {
//            mav.addObject("name", user.getName());
//        }
//
//        mav.addObject("movies", movieService.getAll());
//        mav.addObject("recentReviews", feedbackService.getRecentReview());
//        return mav;
//    }
@GetMapping(path = {"/home","/"})
public ModelAndView home(HttpServletRequest request,
                         @RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "5") int size) {
    User user = (User) request.getSession().getAttribute("user");
    ModelAndView mav = new ModelAndView("home");

    if (user != null) {
        mav.addObject("name", user.getName());
    }

    // Sử dụng Pageable để lấy trang phim
    Page<Movies> moviesPage = movieService.getAllPageable(PageRequest.of(page, size));

    mav.addObject("movies", moviesPage.getContent());
    mav.addObject("recentReviews", feedbackService.getRecentReview());
    mav.addObject("currentPage", page);
    mav.addObject("totalPages", moviesPage.getTotalPages());

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

    @PostMapping("/add-movie")
    public ModelAndView addMovie(   @RequestParam String describe,
                                    @RequestParam String movieName,
                                    @RequestParam String imgLink,
                                    @RequestParam String director,
                                    @RequestParam String genre,
                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date releaseDate,
                                    HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

            // Lấy userId từ session
            User user = ((User) request.getSession().getAttribute("user"));

            if (user != null) {
                // Tạo đối tượng Feedback và thiết lập thông tin
                Movies movies = Movies.builder()
                        .movieName(movieName)
                        .image(imgLink)
                        .director(director)
                        .description(describe)
                        .genre(genre)
                        .releaseDate(releaseDate)
                        .user(user)
                        .build();

                // Thêm Feedback vào cơ sở dữ liệu
                movieService.addMovie(movies);

                // Chuyển hướng đến trang chi tiết phim sau khi đăng feedback
                mav.setViewName("redirect:/");
            } else {
                // Xử lý trường hợp người dùng chưa đăng nhập
                mav.setViewName("redirect:/login");
            }
        return mav;
    }




}
