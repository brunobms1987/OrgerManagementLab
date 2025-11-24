package lab25.ordermanagement.articles.services;

import lab25.ordermanagement.articles.dto.ArticleDTO;
import lab25.ordermanagement.articles.enums.ArticleType;
import lab25.ordermanagement.articles.mapper.ArticleMapper;
import lab25.ordermanagement.articles.models.ArticleEntity;
import lab25.ordermanagement.articles.policies.ArticlePolicy;
import lab25.ordermanagement.articles.repositories.ArticleRepository;
import lab25.ordermanagement.config.BaseTest;
import lab25.ordermanagement.config.ServiceTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;

@ServiceTest
@WithMockUser(username = "user", roles = {"USER"})
@ExtendWith(MockitoExtension.class)
public class ArticleQueryServiceTest extends BaseTest {
    private ArticleQueryService articleQueryService;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleRepository articleRepository;

    private ArticleEntity article1 = new ArticleEntity()
            .setArticleType(ArticleType.PHYSICAL_PRODUCT)
            .setName("Article 1")
            .setArticleNumber("12345")
            .setDescription("Description 1");

    private ArticleEntity article2 = new ArticleEntity()
            .setArticleType(ArticleType.DIGITAL_PRODUCT)
            .setName("Article 2")
            .setArticleNumber("54321")
            .setDescription("Description 2");

    @BeforeEach
    void setUp() {
        article1.setCreatedAt(Instant.now());
        article2.setCreatedAt(Instant.now().minus(1, ChronoUnit.HOURS));
        this.articleRepository.save(article1);
        this.articleRepository.save(article2);
        this.articleQueryService = new ArticleQueryService(articleRepository, articleMapper, new ArticlePolicy());
    }

    @AfterEach
    void tearDown() {
        this.articleRepository.deleteAll();
    }

    @Test
    void getArticles_returnsAllArticles() {
        Pageable page = Pageable.ofSize(10).withPage(0);
        Page<ArticleDTO> result = articleQueryService.getArticles(page);
        assertEquals(1, result.getTotalPages());
        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getNumberOfElements());
        assertEquals(result.getContent().get(0).name(), article2.getName());
        assertEquals(result.getContent().get(1).name(), article1.getName());
    }

    @Test
    void getArticles_returnsPaginatedArticles() {
        Pageable page = Pageable.ofSize(1).withPage(0);
        Page<ArticleDTO> result = articleQueryService.getArticles(page);
        assertEquals(result.getTotalPages(), 2);
        assertEquals(result.getTotalElements(), 2);
        assertEquals(result.getNumberOfElements(), 1);
        assertEquals(result.getContent().get(0).name(), article2.getName());


        page = Pageable.ofSize(1).withPage(1);
        result = articleQueryService.getArticles(page);
        assertEquals(result.getTotalPages(), 2);
        assertEquals(result.getTotalElements(), 2);
        assertEquals(result.getNumberOfElements(), 1);
        assertEquals(result.getContent().get(0).name(), article1.getName());
    }

    @Test
    void getArticle_returnsArticle() {
        ArticleDTO result = articleQueryService.getArticle(article1.getId());
        assertEquals("Article 1", result.name());
        assertEquals(ArticleType.PHYSICAL_PRODUCT, result.type());
        assertEquals("12345", result.articleNumber());
        assertEquals("Description 1", result.description());
    }
}
