package com.example.demo.Repo;

import com.example.demo.Model.Articles;
import com.example.demo.Model.Category;
import com.example.demo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticlesRepo extends JpaRepository<Articles, Long> {
    Articles findByTitle (String title);

    List<Articles> findByCategory(Category category);

    List<Articles> findByTitleContainingIgnoreCaseOrUser_UsernameOrCategory_Name(String title, String user, String category);
}
