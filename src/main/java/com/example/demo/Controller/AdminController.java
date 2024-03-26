package com.example.demo.Controller;

import com.example.demo.Impl.ArticlesImpl;
import com.example.demo.Impl.CategoryService;
import com.example.demo.Impl.CommentsService;
import com.example.demo.Impl.UserService;
import com.example.demo.Model.*;
import com.example.demo.Repo.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.naming.directory.SearchResult;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final ArticlesImpl articlesImpl;
    private final CategoryService categoryService;
    private final CommentsService commentsService;
    private final UserService userService;
    private final UserRepo repo;

    @GetMapping("/users")
    public String usersPage(Model model, @RequestParam(value = "param", defaultValue = "", required = false) String param) {
        List<User> users = userService.searchUser(param, param);
        List<Articles> articles = articlesImpl.all();
        List<Category> categories = categoryService.all();
        User user = userService.authUser();
        model.addAttribute("user", user);
        model.addAttribute("users", users);
        model.addAttribute("param", param);
        model.addAttribute("articles", articles);
        model.addAttribute("category", categories);
        return "html/users";
    }

/*    @GetMapping("/search")
    public String searchUserParam(@RequestParam("username") String username) {
        User foundUser = userService.findByUsername(username);
        if (foundUser != null) {
            return "redirect:/profile/" + foundUser.getUsername();
        } else {
            return "redirect:/profile";
        }
    }*/

/*    @GetMapping("/search")
    public RedirectView search(@RequestParam("query") String query, RedirectAttributes attributes) {
        List<Object> searchResults = userService.search(query);

        // Предполагаем, что первый результат - это то, что нам нужно
        if (!searchResults.isEmpty()) {
            Object result = searchResults.get(0);
            if (result instanceof User) {
                // Перенаправляем на страницу профиля пользователя
                attributes.addAttribute("username", ((User) result).getUsername());
                return new RedirectView("/profile/" + ((User) result).getUsername());
            } else if (result instanceof Articles) {
                // Перенаправляем на страницу новости
                attributes.addAttribute("articleId", ((Articles) result).getId());
                return new RedirectView("/article/" + ((Articles) result).getId());
            }
        }

        // Если ничего не найдено, можно вернуть страницу с ошибкой или на главную
        return new RedirectView("/");
    }*/

/*    @GetMapping("/search")
    public RedirectView search(@RequestParam("query") String query, HttpServletRequest request) {
        List<Object> searchResults = userService.search(query);

        if (!searchResults.isEmpty()) {
            Object result = searchResults.get(0);
            if (result instanceof User) {
                return new RedirectView("/profile/" + ((User) result).getUsername());
            } else if (result instanceof Articles) {
                return new RedirectView("/article/" + ((Articles) result).getId());
            }
        }

        String referer = request.getHeader("Referer");
        return new RedirectView(referer != null ? referer : "/");
    }*/

    @GetMapping("/search")
    @ResponseBody
    public List<com.example.demo.Model.SearchResult> search(@RequestParam("query") String query) {
        List<Object> searchResults = userService.search(query);
        List<com.example.demo.Model.SearchResult> results = new ArrayList<>();

        for (Object result : searchResults) {
            if (result instanceof User) {
                results.add(new com.example.demo.Model.SearchResult(((User) result).getUsername(), "/profile/" + ((User) result).getUsername()));
            } else if (result instanceof Articles) {
                results.add(new com.example.demo.Model.SearchResult(((Articles) result).getTitle(), "/article/" + ((Articles) result).getId()));
            }
        }

        return results;
    }

    @GetMapping("/all/comment")
    public String all(Model model) {
        List<Comments> comments = commentsService.getAll();
        model.addAttribute("comment", comments);
        return "html/com";
    }


    @GetMapping("/users/add")
    public String pageAdd(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", Role.values());
        return "html/add-users";
    }

    @PostMapping("/add")
    public String add(User user, Role role) {
        userService.newUserAdd(user, role);
        return "redirect:/users";
    }

    @PostMapping("/users/save")
    public String save(@ModelAttribute("user") User user, Role role) {
        userService.save(user, role);
        return "redirect:/users";
    }

    @GetMapping("/users/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        User user = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("roles", Role.values());
        model.addAttribute("user", user);
        return "html/edit-user";
    }

    @GetMapping("/users/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Пользователь успешно удален");
        return "redirect:/users";
    }
}
