package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "comments")
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "newsId")
    private News newsId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User userId;

    private String content;

    @Override
    public String toString() {
        return "Comments{" +
                "id=" + id +
                ", newsId=" + (newsId != null ? newsId.getId() : null) + // Используйте идентификатор новости
                ", userId=" + (userId != null ? userId.getId() : null) + // Используйте идентификатор пользователя
                ", content='" + content + '\'' +
                '}';
    }
}
