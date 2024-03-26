package com.example.demo.Impl;

import com.example.demo.Exception.FileStorageException;
import com.example.demo.Model.Articles;
import com.example.demo.Model.Category;
import com.example.demo.Model.User;
import com.example.demo.Repo.ArticlesRepo;
import com.example.demo.Repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ArticlesImpl {

    private final ArticlesRepo articlesRepo;
    private final FileStorageImpl fileStorage;
    private final UserRepo userRepo;
    private final CategoryService categoryService;

    public Articles save(Articles articles, User user, Category category, MultipartFile file) {
        User existingUser = userRepo.findById(user.getId()).orElse(null);
        Category existingCategory = categoryService.getCategoryById(category.getId());

        if (existingUser != null && existingCategory != null) {
            articles.setUser(existingUser);
            articles.setCategory(existingCategory);

            try {
                // Сохранение файла
                String filename = fileStorage.storeFile(file);
                articles.setArticlesFileName(filename);
            } catch (FileStorageException ex) {
                // Обработка ошибки сохранения файла
                ex.printStackTrace();
                return null;
            }

            return articlesRepo.save(articles);
        } else {
            return null;
        }
    }

    public List<Articles> getArticlesByCategory(Category category) {
        return articlesRepo.findByCategory(category);
    }

    public List<Articles> all() { return articlesRepo.findAll(); }

    public Optional<Articles> findById (Long id) { return articlesRepo.findById(id); }
}
