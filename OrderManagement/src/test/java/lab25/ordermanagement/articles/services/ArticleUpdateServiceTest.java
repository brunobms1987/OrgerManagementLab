package lab25.ordermanagement.articles.services;

import lab25.ordermanagement.articles.dto.ArticleDTO;
import lab25.ordermanagement.articles.dto.ArticleUpdateDTO;
import lab25.ordermanagement.articles.enums.ArticleType;
import lab25.ordermanagement.articles.mapper.ArticleMapper;
import lab25.ordermanagement.articles.models.ArticleEntity;
import lab25.ordermanagement.articles.policies.ArticlePolicy;
import lab25.ordermanagement.articles.repositories.ArticleRepository;
import lab25.ordermanagement.config.BaseTest;
import lab25.ordermanagement.config.ServiceTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ServiceTest
@ExtendWith(MockitoExtension.class)
public class ArticleUpdateServiceTest extends BaseTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleMapper articleMapper;

    private ArticlePolicy articlePolicy = new ArticlePolicy();

    private ArticleUpdateService articleUpdateService;

    private ArticleEntity article1 = new ArticleEntity()
            .setArticleType(ArticleType.PHYSICAL_PRODUCT)
            .setName("Article 1")
            .setArticleNumber("12345")
            .setDescription("Description 1");

    @BeforeEach
    void setUp() {
        articleRepository.save(article1);
        articleUpdateService = new ArticleUpdateService(articleRepository, articleMapper, articlePolicy);
    }

    @AfterEach
    void tearDown() {
        articleRepository.deleteAll();
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Test
    void updateArticle_throwsExceptionWhenUserIsNotLoggedIn() {
        ArticleUpdateDTO updateDTO = new ArticleUpdateDTO(
                ArticleType.DIGITAL_PRODUCT,
                "New name",
                "99999",
                "new description"
        );

        assertThrows(AccessDeniedException.class, () -> {
            articleUpdateService.updateArticle(article1.getId(), updateDTO);
        });

        articleRepository.flush();
        articleRepository.findById(article1.getId()).ifPresent(article -> {
            assertEquals("Article 1", article.getName());
        });
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void updateArticle_throwsExceptionWhenUserIsNotAllowed() {
        ArticleUpdateDTO updateDTO = new ArticleUpdateDTO(
                ArticleType.DIGITAL_PRODUCT,
                "New name",
                "99999",
                "new description"
        );

        assertThrows(AccessDeniedException.class, () -> {
            articleUpdateService.updateArticle(article1.getId(), updateDTO);
        });

        articleRepository.flush();
        articleRepository.findById(article1.getId()).ifPresent(article -> {
            assertEquals("Article 1", article.getName());
        });
    }

    @Nested
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    class whenUserHasAdminPermission {
        @Test
        void updateArticle_updatesArticle() {
            ArticleUpdateDTO updateDTO = new ArticleUpdateDTO(
                    ArticleType.DIGITAL_PRODUCT,
                    "New name",
                    "99999",
                    "new description"
            );

            ArticleDTO result = articleUpdateService.updateArticle(article1.getId(), updateDTO);

            assertEquals(ArticleType.DIGITAL_PRODUCT, result.type());
            assertEquals("New name", result.name());
            assertEquals("99999", result.articleNumber());
            assertEquals("new description", result.description());


            ArticleEntity updatedArticle = articleRepository.findById(article1.getId()).get();
            assertEquals(ArticleType.DIGITAL_PRODUCT, updatedArticle.getArticleType());
            assertEquals("New name", updatedArticle.getName());
            assertEquals("99999", updatedArticle.getArticleNumber());
            assertEquals("new description", updatedArticle.getDescription());
        }
    }
}
