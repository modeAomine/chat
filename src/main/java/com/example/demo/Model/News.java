package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(length = 1000)
    private String content;

    @OneToMany(mappedBy = "newsId", cascade = CascadeType.PERSIST)
    private List<Comments> commentsNews;

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", commentsNewsSize=" + (commentsNews != null ? commentsNews.size() : 0) + // Используйте размер коллекции вместо вывода ее содержимого
                '}';
    }
}