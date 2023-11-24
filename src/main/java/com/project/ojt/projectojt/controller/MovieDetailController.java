package com.project.ojt.projectojt.controller;

import com.project.ojt.projectojt.entity.*;
import com.project.ojt.projectojt.service.*;

import jakarta.persistence.Convert;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final UrlService urlService;
    private final ReplyService replyService;

    @GetMapping("/movie-detail/{movieId}")
    public ModelAndView movieDetail(@PathVariable Integer movieId,
                                    @RequestParam(defaultValue = "0") int page,
                                    HttpServletRequest request) {
        // Lấy thông tin phim dựa trên movieId và truyền nó đến trang movie-detail.html
        Movies movie = movieService.getMovieById(movieId); // implement phương thức getMovieById
        List<Url> urls = urlService.getUrlByMovieId(movie); // Thay vì `getUrlByMovieId`

        int pageSize=4;
        if (page < 0) {
            page = 0; // Nếu page âm, sử dụng trang đầu tiên
        }
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").descending());

//        List<Feedback> feedback = feedbackService.getFeedbackByMovieId(movie);
        Page<Feedback> feedbackPage = feedbackService.getFeedbackPageByMovieId(movie, pageable);

        Integer totalFeedback = feedbackService.getTotalFeedbackByMovieId(movie);

        ModelAndView mav = new ModelAndView("movie-detail");
        mav.addObject("movie", movie);
        mav.addObject("urls", urls);
        mav.addObject("feedback", feedbackPage.getContent()); // Lấy danh sách đánh giá từ trang hiện tại
        mav.addObject("totalFeedback", totalFeedback);
        mav.addObject("currentPage", page);
        mav.addObject("totalPages", feedbackPage.getTotalPages());

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
                movie.calculateAverageGrade();
                movieService.updateMovie(movie); // Đảm bảo cập nhật đối tượng Movies

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
                        .movieId(movie)
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
    @PostMapping("/reply-feedback/{movieId}/{feedbackId}")
    public ModelAndView replyFeedback(@PathVariable Integer movieId,
                                      @PathVariable Integer feedbackId,
                                      @RequestParam String reply,
                                      HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        Feedback feedback = feedbackService.getFeedbackById(feedbackId);

        if (feedback != null) {
            User user = (User) request.getSession().getAttribute("user");

            if (user != null) {
                // Tạo đối tượng Reply và thiết lập thông tin
                Reply replyObject = Reply.builder()
                        .feedback(feedback)
                        .repliedBy(user)
                        .content(reply)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();

                // Thêm Reply vào cơ sở dữ liệu
                replyService.addReply(replyObject);

                // Cập nhật lại danh sách replies của feedback
                feedback.getReplies().add(replyObject);
                feedbackService.updateFeedback(feedback);

                // Chuyển hướng đến trang chi tiết phim sau khi trả lời feedback
                mav.setViewName("redirect:/movie-detail/" + movieId);
            } else {
                // Xử lý trường hợp người dùng chưa đăng nhập
                mav.setViewName("redirect:/login");
            }
        } else {
            // Xử lý trường hợp không tìm thấy feedback
            mav.addObject("errorMessage", "Feedback không tồn tại");
            mav.setViewName("error");
        }

        return mav;
    }
}

