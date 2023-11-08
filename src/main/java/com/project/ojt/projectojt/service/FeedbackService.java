package com.project.ojt.projectojt.service;

import com.project.ojt.projectojt.entity.Feedback;
import com.project.ojt.projectojt.entity.Movies;

import java.util.List;

public interface FeedbackService {

    List<Feedback> getRecentReview();


    List<Feedback> getFeedbackByMovieId(Movies movie);
}
