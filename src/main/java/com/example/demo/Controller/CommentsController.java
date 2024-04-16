package com.example.demo.Controller;

import com.example.demo.Exception.CommentSavingException;
import com.example.demo.Exception.InvalidCommentException;
import com.example.demo.Impl.CommentsService;
import com.example.demo.Impl.UserService;
import com.example.demo.Model.Comments;
import com.example.demo.Model.News;
import com.example.demo.Model.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
@RequestMapping
public class CommentsController {

    private final CommentsService commentsService;
    private final UserService userService;


    @PostMapping("/comments/{newsId}")
    public String addCommentToProfile(@PathVariable Long newsId, @RequestParam String content, HttpServletRequest request) {
        Comments comment = new Comments();
        comment.setContent(content);
        try {
            commentsService.addCommentToProfile(newsId, comment);
            String referer = request.getHeader("Referer");
            return "redirect:" + referer;
        } catch (IllegalArgumentException e) {
            return "redirect:/profile";
        }
    }

    @PostMapping("/news/{newsId}/comments/{commentId}")
    public String deleteCommentUnderPost(@PathVariable Long newsId, @PathVariable Long commentId, RedirectAttributes redirectAttributes) {
        try {
            User user = userService.authUser();
            if (commentsService.canDeleteComment(commentId, user.getId())) {
                commentsService.deleteComment(commentId);
                redirectAttributes.addFlashAttribute("message", "Комментарий успешно удален");
            } else {
                redirectAttributes.addFlashAttribute("error", "Вы не можете удалить этот комментарий");
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Произошла ошибка при удалении комментария");
        }
        return "redirect:/profile";
    }
}