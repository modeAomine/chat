package com.example.demo.Impl;

import com.example.demo.Exception.CommentSavingException;
import com.example.demo.Exception.InvalidCommentException;
import com.example.demo.Model.Comments;
import com.example.demo.Model.News;
import com.example.demo.Model.User;
import com.example.demo.Repo.CommentsRepo;
import com.example.demo.Repo.NewsRepo;
import com.example.demo.Repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentsService {

    private final CommentsRepo commentsRepo;
    private final UserService userService;
    private final NewsRepo newsRepo;

    public void addCommentToProfile(Long newsId, Comments content) {
        Optional<News> optionalNews = newsRepo.findById(newsId);
        User user = userService.authUser();

        if (user != null && optionalNews.isPresent()) {
            News news = optionalNews.get();
            content.setUserId(user);
            content.setNewsId(news);
            commentsRepo.save(content);
            news.getCommentsNews().add(content);
            newsRepo.save(news);
        } else {
            throw new IllegalArgumentException("User or news not found");
        }
    }

    public boolean canDeleteComment(Long commentId, Long userId) {
        Optional<Comments> optionalComment = commentsRepo.findById(commentId);
        if (optionalComment.isPresent()) {
            Comments comment = optionalComment.get();
            return comment.getUserId().getId().equals(userId) || comment.getNewsId().getUserId().getId().equals(userId);
        }
        return false;
    }

    public void deleteComment(Long commentId) {
        Optional<Comments> optionalComment = commentsRepo.findById(commentId);
        if (optionalComment.isPresent()) {
            Comments comment = optionalComment.get();
            if (comment.getContent().isBlank()) {
                throw new IllegalArgumentException("Comment is empty or consists only of whitespace");
            }
            commentsRepo.delete(comment);
        } else {
            throw new IllegalArgumentException("Comment not found with id: " + commentId);
        }
    }

    public List<Comments> getCommentsForNews(Long newsId) {
        return commentsRepo.findByNewsIdId(newsId);
    }

    public List<Comments> getCommentForNewsId(Long newsId) {
        return commentsRepo.findByNewsId_Id(newsId);
    }

    public List<Comments> getAll() { return commentsRepo.findAll(); }
}