package me.practice.springbootproject2.controller;


import lombok.RequiredArgsConstructor;
import me.practice.springbootproject2.domain.User;
import me.practice.springbootproject2.dto.AddArticleRequest;
import me.practice.springbootproject2.dto.ArticleListResponse;
import me.practice.springbootproject2.dto.UpdateArticleRequest;
import me.practice.springbootproject2.dto.ArticleResponse;
import me.practice.springbootproject2.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    // 빈: 스프링 컨테이너가 관리하는 객체(BoardService)
    // boardService: 스프링 컨테이너가 생성한 BoardService 빈을 의존성 주입(Dependency Injection)받은 필드

    @GetMapping
    public ResponseEntity<List<ArticleListResponse>> getAllArticles(
            /*@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable*/ ) {
        List<ArticleListResponse> response = boardService.findAll();
        return ResponseEntity.ok(response);
    }

    /* 검색 */
    @GetMapping("/search")
    public ResponseEntity<List<ArticleListResponse>> getSearchArticles(@RequestParam String range,
                                                                       @RequestParam String query) {
        List<ArticleListResponse> response = boardService.search(range, query);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> getArticle(@PathVariable Long id) { // @RequestParam -> /articles?id=1
        ArticleResponse response = boardService.findById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ArticleResponse> addArticle(@RequestBody AddArticleRequest request,
                                                      @AuthenticationPrincipal User user) {
        // @RequestBody: Json 형식의 데이터를 받아옴
       ArticleResponse response = boardService.save(request, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleResponse> updateArticle(@PathVariable Long id,
                              @RequestBody UpdateArticleRequest request,
                              @AuthenticationPrincipal User user) {
        ArticleResponse response = boardService.update(id, request, user);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id,
                                              @AuthenticationPrincipal User user) {
        boardService.delete(id, user);
        return ResponseEntity.ok().build();
    }
}
