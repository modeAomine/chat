package com.example.demo.Repo;

import com.example.demo.Model.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepo extends JpaRepository<News, Long> {

}
