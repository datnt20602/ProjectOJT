package com.project.ojt.projectojt.service;


import com.project.ojt.projectojt.entity.Movies;
import com.project.ojt.projectojt.entity.Url;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieService {

    List<Movies> getAll();

    List<Movies> searchMovies(String searchTerm);

    Movies getMovieById(Integer movieId);


    Movies addMovie(Movies movies);

    Movies updateMovie(Movies movie);

    Page<Movies> getAllPageable(Pageable pageable);
}
