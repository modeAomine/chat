package com.example.demo.Impl;

import com.example.demo.Model.News;
import com.example.demo.Repo.NewsRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NewsService {

    private final NewsRepo newsRepo;

    public List<News> getUserNews(Long userId) {
        return newsRepo.findByUserIdId(userId);
    }

    public Optional<News> findById(Long id) { return newsRepo.findAllById(id); }
}
