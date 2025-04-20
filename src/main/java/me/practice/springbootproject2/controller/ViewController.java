package me.practice.springbootproject2.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.practice.springbootproject2.dto.ArticleListResponse;
import me.practice.springbootproject2.service.BoardService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final BoardService boardService;

    @GetMapping("/")
    public String home() {
        return "homePage";
    }

    @GetMapping("/board")
    public String board(@RequestParam(required = false) String range,
                        @RequestParam(required = false) String query,
                        Model model,
                        HttpServletRequest request) {

        List<ArticleListResponse> articles =
                (range != null && query != null) ?
                        boardService.search(range, query) :
                        boardService.findAll();

        model.addAttribute("articles", articles);
        model.addAttribute("range", range);
        model.addAttribute("query", query);
        model.addAttribute("queryString", request.getQueryString());
        return "boardPage";
    }

    @GetMapping("/write-article")
    public String writeArticle() {
        return "writeArticle";
    }

    @GetMapping("/edit-article/{articleId}")
    public String editArticle(@PathVariable String articleId) {
        return "writeArticle";
    }

    @GetMapping("/read-article/{articleId}")
    public String readArticle(@PathVariable String articleId) {
        return "readArticle";
    }

    @GetMapping("/login")
    public String login() { return "login"; }

    @GetMapping("/join")
    public String join() { return "join"; }

}
