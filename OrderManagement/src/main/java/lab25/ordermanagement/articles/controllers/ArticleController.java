package lab25.ordermanagement.articles.controllers;

import lab25.ordermanagement.articles.dto.ArticleDTO;
import lab25.ordermanagement.articles.dto.ArticleUpdateDTO;
import lab25.ordermanagement.articles.services.ArticleQueryService;
import lab25.ordermanagement.articles.services.ArticleUpdateService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleQueryService articleQueryService;
    private final ArticleUpdateService articleUpdateService;

    public ArticleController(ArticleQueryService articleQueryService, ArticleUpdateService articleUpdateService) {
        this.articleQueryService = articleQueryService;
        this.articleUpdateService = articleUpdateService;
    }

    @GetMapping
    public Page<ArticleDTO> listArticles(@ParameterObject Pageable page) {
        return articleQueryService.getArticles(page);
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Long articleId) {
        return ResponseEntity.ok(articleQueryService.getArticle(articleId));
    }

    @PostMapping("/{articleId}")
    public ResponseEntity<ArticleDTO> updateArticle(@PathVariable Long articleId, @RequestBody ArticleUpdateDTO dto) {
        return ResponseEntity.ok(articleUpdateService.updateArticle(articleId, dto));
    }
}
