package com.project.ojt.projectojt.repository;

import com.project.ojt.projectojt.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrlRepository extends JpaRepository<Url, Integer> {
    List<Url> findByMovieId(Integer movieId);
}

