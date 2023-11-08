package com.project.ojt.projectojt.repository;

import com.project.ojt.projectojt.entity.Feedback;
import com.project.ojt.projectojt.entity.Movies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    List<Feedback> findFirst10ByOrderByIdDesc();

    List<Feedback> findFeedbackByMovieId(Movies movies);

}
