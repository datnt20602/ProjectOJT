package com.project.ojt.projectojt.service;

import com.project.ojt.projectojt.entity.Feedback;
import com.project.ojt.projectojt.entity.Movies;
import com.project.ojt.projectojt.entity.Url;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FeedbackService {

    List<Feedback> getRecentReview();


//    List<Feedback> getFeedbackByMovieId(Movies movie);


    Feedback addFeedback(Feedback feedback);


    Integer getTotalFeedbackByMovieId(Movies movie);

    Page<Feedback> getFeedbackPageByMovieId(Movies movie, Pageable pageable);

    Feedback getFeedbackById(Integer feedbackId);

    Feedback updateFeedback(Feedback feedback);
}
