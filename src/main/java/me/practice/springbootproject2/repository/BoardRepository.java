package me.practice.springbootproject2.repository;

import me.practice.springbootproject2.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Article, Long> {
    List<Article> findByTitleContaining(String query);
    List<Article> findByContentContaining(String query);
    List<Article> findByAuthor_UsernameContaining(String query);

    @Query("SELECT a fROM Article a JOIN a.author u where a.title LIKE %:query% OR a.content LIKE %:query% OR u.username LIKE %:query%")
    List<Article> findByTitleOrContentOrUserName(@Param("query") String qeury);
}
