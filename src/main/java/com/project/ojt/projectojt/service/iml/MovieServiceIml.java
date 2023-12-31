package com.project.ojt.projectojt.service.iml;

import com.project.ojt.projectojt.entity.Feedback;
import com.project.ojt.projectojt.entity.Movies;
import com.project.ojt.projectojt.entity.Url;
import com.project.ojt.projectojt.repository.MoviesRepository;
import com.project.ojt.projectojt.repository.UrlRepository;
import com.project.ojt.projectojt.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Movies addMovie(Movies movies) {
        return moviesRepository.save(movies);
    }

    @Override
    public Movies updateMovie(Movies movie) {
        return moviesRepository.save(movie);
    }

    @Override
    public Page<Movies> getAllPageable(Pageable pageable) {
        return moviesRepository.findAll(pageable);
    }


}
