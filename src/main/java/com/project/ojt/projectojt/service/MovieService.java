package com.project.ojt.projectojt.service;

import com.project.ojt.projectojt.entity.Movies;
import com.project.ojt.projectojt.entity.Url;

import java.util.List;

public interface MovieService {

    List<Movies> getAll();

    List<Movies> searchMovies(String searchTerm);

    Movies getMovieById(Integer movieId);

    List<Url> getUrlByMovieId(Integer movieId);
}
