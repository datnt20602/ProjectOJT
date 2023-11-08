package com.project.ojt.projectojt.repository;

import com.project.ojt.projectojt.entity.Movies;
import com.project.ojt.projectojt.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoviesRepository extends JpaRepository<Movies, Integer> {
    List<Movies> findByMovieNameContaining(String searchTerm);

    Movies findMoviesByMovieId(Integer movieId);

}
