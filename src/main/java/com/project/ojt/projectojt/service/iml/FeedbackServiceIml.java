package com.project.ojt.projectojt.service.iml;

import com.project.ojt.projectojt.entity.Feedback;
import com.project.ojt.projectojt.entity.Movies;
import com.project.ojt.projectojt.repository.FeedbackRepository;
import com.project.ojt.projectojt.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class FeedbackServiceIml implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Override
    public List<Feedback> getRecentReview() {
        return feedbackRepository.findFirst10ByOrderByIdDesc();
    }

//    @Override
//    public List<Feedback> getFeedbackByMovieId(Movies movie) {
//        return feedbackRepository.findFeedbackByMovieIdOrderByIdDesc(movie);
//    }

    @Override
    public Feedback addFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    @Override
    public Integer getTotalFeedbackByMovieId(Movies movie) {
        return feedbackRepository.countFeedbacksByMovieId(movie);
    }

    @Override
    public Page<Feedback> getFeedbackPageByMovieId(Movies movie, Pageable pageable) {
        return feedbackRepository.findByMovieIdOrderByIdDesc(movie,pageable);
    }


}
