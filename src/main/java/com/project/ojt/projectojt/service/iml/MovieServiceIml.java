package com.project.ojt.projectojt.service.iml;

import com.project.ojt.projectojt.entity.Movies;
import com.project.ojt.projectojt.repository.MoviesRepository;
import com.project.ojt.projectojt.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieServiceIml implements MovieService {

    private final MoviesRepository moviesRepository;
    @Override
    public List<Movies> getAll() {
        return moviesRepository.findAll();
    }

    @Override
    public List<Movies> searchMovies(String searchTerm) {
        return moviesRepository.findByMovieNameContaining(searchTerm);
    }

    @Override
    public Movies getMovieById(Integer movieId) {
        return moviesRepository.findMoviesByMovieId(movieId);
    }



}
