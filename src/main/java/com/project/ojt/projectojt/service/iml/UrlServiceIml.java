package com.project.ojt.projectojt.service.iml;

import com.project.ojt.projectojt.entity.Url;
import com.project.ojt.projectojt.repository.UrlRepository;
import com.project.ojt.projectojt.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UrlServiceIml implements UrlService {

    private final UrlRepository urlRepository;

    @Override
    public List<Url> getUrlByMovieId(Integer movieId) {
        return urlRepository.getUrlByMovieId(movieId);
    }

    @Override
    public Url addUrl(Url url) {
        return urlRepository.save(url);
    }
}
