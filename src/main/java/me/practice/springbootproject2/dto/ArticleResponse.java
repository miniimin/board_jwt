package me.practice.springbootproject2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.practice.springbootproject2.domain.Article;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter // Json 변환을 위해 필요
public class ArticleResponse {
    private Long id;
    private String title;
    private String content;
    private String authorName;
    private LocalDateTime createdAt;

    public static ArticleResponse fromEntity(Article article) {
        return new ArticleResponse(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getAuthor().getUsername(),
                article.getCreatedAt()
        );
    }
}
