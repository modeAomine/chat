package com.example.demo.Controller;

import com.example.demo.Impl.ArticlesImpl;
import com.example.demo.Impl.CategoryService;
import com.example.demo.Impl.UserService;
import com.example.demo.Model.Articles;
import com.example.demo.Model.Category;
import com.example.demo.Model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping
public class ArticlesController {

    private final UserService userService;
    private final CategoryService categoryService;
    private final ArticlesImpl articlesImpl;

    @GetMapping("/articles")
    public String articles(Model model) {
        User user = userService.authUser();
        model.addAttribute("user", user);
        List<Articles> articles = articlesImpl.all(); // Получаем список всех новостей
        model.addAttribute("articles", articles);
        List<Category> categories = categoryService.all();
        model.addAttribute("categories", categories);
        return "html/articles";
    }



    @GetMapping("/articles-in-category/{categoryId}")
    public String getArticlesInCategory(@PathVariable Long categoryId, Model model) {
        User user = userService.authUser();
        model.addAttribute("user", user);
        Category category = categoryService.getCategoryById(categoryId);
        List<Articles> articles = articlesImpl.getArticlesByCategory(category);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("articles", articles);
        List<Category> categories = categoryService.all();
        model.addAttribute("categories", categories);
        return "html/articles"; // Вернуть ту же страницу
    }

    @GetMapping("/article/{id}")
    public String viewArticle(@PathVariable("id") Long id, Model model) {
        User user = userService.authUser();
        model.addAttribute("user", user);
        Optional<Articles> articleOptional = articlesImpl.findById(id);
        if (articleOptional.isPresent()) {
            Articles article = articleOptional.get();
            model.addAttribute("article", article);
            return "html/view-article";
        } else {
            return "redirect:/articles";
        }
    }

    @PostMapping("/save-article")
    public String saveArticle(@ModelAttribute("article") Articles article,
                              @RequestParam("file") MultipartFile file,
                              @RequestParam("categoryId") Long categoryId,
                              Principal principal) {
        String username = principal.getName();
        User user = userService.findByUsername(username);
        Category category = categoryService.getCategoryById(categoryId);
        articlesImpl.save(article, user, category, file); // Добавляем категорию
        return "redirect:/articles";
    }
}

