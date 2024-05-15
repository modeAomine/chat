package com.example.demo.Repo;

import com.example.demo.Model.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NewsRepo extends JpaRepository<News, Long> {

    List<News> findByUserIdId(Long userId);

    Optional<News> findAllById(Long id);
}
