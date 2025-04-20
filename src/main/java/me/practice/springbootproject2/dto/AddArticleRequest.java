package me.practice.springbootproject2.dto;

import lombok.Getter;
import me.practice.springbootproject2.domain.Article;
import me.practice.springbootproject2.domain.User;


@Getter
public class AddArticleRequest {
    String title;
    String content;

    public Article toEntity(AddArticleRequest request, User user) {
        return Article
                .builder()
                .title(request.getTitle())
                .content(request.getContent())
                .author(user)
                .build();
    }
}
