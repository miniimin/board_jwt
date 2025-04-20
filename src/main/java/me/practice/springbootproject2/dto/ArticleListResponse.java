package me.practice.springbootproject2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.practice.springbootproject2.domain.Article;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter // Json 변환을 위해 필요
public class ArticleListResponse {
    private Long id;
    private String title;
    private String authorName;
    private LocalDateTime createdAt;

    public static ArticleListResponse fromEntity(Article article) {
        return new ArticleListResponse(
                article.getId(),
                article.getTitle(),
                article.getAuthor().getUsername(),
                article.getCreatedAt()
        );
    }
}
