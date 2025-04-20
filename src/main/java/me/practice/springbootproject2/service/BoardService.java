package me.practice.springbootproject2.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.practice.springbootproject2.domain.Article;
import me.practice.springbootproject2.domain.User;
import me.practice.springbootproject2.dto.AddArticleRequest;
import me.practice.springbootproject2.dto.ArticleListResponse;
import me.practice.springbootproject2.dto.UpdateArticleRequest;
import me.practice.springbootproject2.dto.ArticleResponse;
import me.practice.springbootproject2.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public List<ArticleListResponse> findAll() {
        return boardRepository.findAll()
                .stream()
                .map(ArticleListResponse::fromEntity)
                .toList();
        /* return boardRepository.findAll(pageable)
                .map(ArticleListResponse::fromEntity); */
    }

    public List<ArticleListResponse> search(String range, String query) {
        List<Article> articles;
        switch (range) {
            case "title":
                articles = boardRepository.findByTitleContaining(query);
                break;
            case "content":
                articles =boardRepository.findByContentContaining(query);
                break;
            case "author":
                articles = boardRepository.findByAuthor_UsernameContaining(query);
                break;
            case "all":
            default:
                articles = boardRepository.findByTitleOrContentOrUserName(query);
                break;
        }
        return articles
                .stream()
                .map(ArticleListResponse::fromEntity)
                .toList();
    }

    public ArticleResponse findById(Long id) {
        Article article = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID(" + id + ")의 글을 찾을 수 없습니다."));
        return ArticleResponse.fromEntity(article);
    }

    public ArticleResponse save(AddArticleRequest request, User user) {
        return ArticleResponse.fromEntity(boardRepository.save(request
                .toEntity(request, user)));
    }

    @Transactional
    public ArticleResponse update(Long id, UpdateArticleRequest request, User user) {
        Article article = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID(" + id + ")의 글을 찾을 수 없습니다."));
        if (!article.getAuthor().getId().equals(user.getId())) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }

        article.update(request.getTitle(), request.getContent());
        return ArticleResponse.fromEntity(article);
    }

    public void delete(Long id,User user) {
        Article article = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID(" + id + ")의 글을 찾을 수 없습니다."));
        if (!article.getAuthor().getId().equals(user.getId())) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }
        boardRepository.deleteById(id);
    }
}
