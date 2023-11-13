package com.project.ojt.projectojt.service;

import com.project.ojt.projectojt.entity.Url;

import java.util.List;

public interface UrlService {
    List<Url> getUrlByMovieId(Integer movieId);

    Url addUrl(Url url);
}
