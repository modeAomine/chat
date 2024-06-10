package com.example.demo.Controller;

import com.example.demo.Exception.CommentSavingException;
import com.example.demo.Exception.InvalidCommentException;
import com.example.demo.Impl.CommentsService;
import com.example.demo.Impl.NewsService;
import com.example.demo.Impl.UserService;
import com.example.demo.Model.Comments;
import com.example.demo.Model.News;
import com.example.demo.Model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping
public class ProfileController {

    private final UserService userService;
    private final CommentsService commentsService;
    private final NewsService newsService;


    @GetMapping("/profile")
    public String profilePage(Model model) {
        User user = userService.authUser();
        boolean isUserOnline = userService.isUserOnline();
        List<News> userNews = newsService.getUserNews(user.getId());
        for (News news : userNews) {
            List<Comments> comments = commentsService.getCommentsForNews(news.getId());
            news.setCommentsNews(comments);

        }

        model.addAttribute("user", user);
        model.addAttribute("userNews", userNews);
        model.addAttribute("isUserOnline", isUserOnline);

        return "html/profile";
    }


    @GetMapping("/profile/{username}")
    public String viewUserProfile(@PathVariable String username, Model model) {
        User user = userService.findByUsername(username);
        if (user != null) {
            model.addAttribute("viewuser", user);
            model.addAttribute("user", userService.authUser());
            model.addAttribute("comment", new Comments());

            return "html/view-profile";
        } else {
            return "redirect:/profile";
        }
    }


    @PostMapping("/profile/publish/news")
    public ResponseEntity<String> publishNews(@RequestParam String newsContent) {
        User currentUser = userService.authUser();
        if (currentUser != null) {
            News news = new News();
            news.setContent(newsContent);
            userService.addPublishNewsProfile(currentUser, news);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(ServletUriComponentsBuilder.fromCurrentContextPath().path("/profile").build().toUri())
                    .body("News added successfully");
        }
        return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/profile/{newsId}/delete")
    public ResponseEntity<String> deleteNews(@PathVariable Long newsId) {
        User currentUser = userService.authUser();
        if (currentUser != null) {
            try {
                userService.deleteNews(currentUser.getId(), newsId);
                return ResponseEntity.status(HttpStatus.FOUND)
                        .location(ServletUriComponentsBuilder.fromCurrentContextPath().path("/profile").build().toUri())
                        .body("News deleted successfully");
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            } catch (IllegalStateException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
    }
}
