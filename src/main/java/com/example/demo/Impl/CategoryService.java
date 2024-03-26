package com.example.demo.Impl;

import com.example.demo.Model.Category;
import com.example.demo.Repo.CategoryRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepo categoryRepo;

    public Category getCategoryById(Long categoryId) {
        Optional<Category> categoryOptional = categoryRepo.findById(categoryId);
        return categoryOptional.orElse(null);
    }

    public Category save(Category category) {
       return categoryRepo.save(category);
    }

    public List<Category> all() { return categoryRepo.findAll(); }
}
