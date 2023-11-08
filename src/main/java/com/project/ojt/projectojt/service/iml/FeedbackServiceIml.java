package com.project.ojt.projectojt.service.iml;

import com.project.ojt.projectojt.entity.Feedback;
import com.project.ojt.projectojt.entity.Movies;
import com.project.ojt.projectojt.repository.FeedbackRepository;
import com.project.ojt.projectojt.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FeedbackServiceIml implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Override
    public List<Feedback> getRecentReview() {
        return feedbackRepository.findFirst10ByOrderByIdDesc();
    }

    @Override
    public List<Feedback> getFeedbackByMovieId(Movies movie) {
        return feedbackRepository.findFeedbackByMovieId(movie);
    }


}
