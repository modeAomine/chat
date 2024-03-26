package com.example.demo.Controller;

import com.example.demo.Impl.CategoryService;
import com.example.demo.Model.Category;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@RequestMapping
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/articles-in-category")
    public String category(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("categories", categoryService.all());
        return "html/add-category-in-articles";
    }
    @PostMapping("/categories/add")
    public String addCategory(@RequestParam("name") String name) {
        Category category = new Category();
        category.setName(name);
        categoryService.save(category);
        return "redirect:/users";
    }
}
