package com.example.demo.Repo;

import com.example.demo.Model.Comments;
import com.example.demo.Model.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepo extends JpaRepository<Comments, Long> {

    Comments deleteByNewsId (News newsId);

    List<Comments> findByNewsIdId(Long newsId);

    List<Comments> findByNewsId_Id(Long newsId);
}