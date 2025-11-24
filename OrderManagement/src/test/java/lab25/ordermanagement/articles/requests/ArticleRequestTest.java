package lab25.ordermanagement.articles.requests;

import lab25.ordermanagement.articles.enums.ArticleType;
import lab25.ordermanagement.articles.models.ArticleEntity;
import lab25.ordermanagement.articles.repositories.ArticleRepository;
import lab25.ordermanagement.config.BaseTest;
import lab25.ordermanagement.config.IntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.junit.Assert.assertEquals;

@AutoConfigureMockMvc
@IntegrationTest
public class ArticleRequestTest extends BaseTest {
    @Autowired
    private MockMvc mockMvc;

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
    }

    @AfterEach
    void tearDown() {
        this.articleRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void listArticles_returnsAllArticles() throws Exception {
        mockMvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id").value(article2.getId()))
                .andExpect(jsonPath("$.content[0].name").value("Article 2"))
                .andExpect(jsonPath("$.content[0].type").value("DIGITAL_PRODUCT"))
                .andExpect(jsonPath("$.content[0].articleNumber").value("54321"))
                .andExpect(jsonPath("$.content[0].description").value("Description 2"))
                .andExpect(jsonPath("$.content[1].id").value(article1.getId()))
                .andExpect(jsonPath("$.content[1].name").value("Article 1"))
                .andExpect(jsonPath("$.content[1].type").value("PHYSICAL_PRODUCT"))
                .andExpect(jsonPath("$.content[1].articleNumber").value("12345"))
                .andExpect(jsonPath("$.content[1].description").value("Description 1"))
        ;
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void listArticles_paginatesResult() throws Exception {
        mockMvc.perform(get("/articles?page=0&size=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id").value(article2.getId()))
        ;

        mockMvc.perform(get("/articles?page=1&size=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id").value(article1.getId()))
        ;
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void getArticleById_returnsArticle() throws Exception {
        mockMvc.perform(get("/articles/" + article1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(article1.getId()))
                .andExpect(jsonPath("$.name").value("Article 1"))
                .andExpect(jsonPath("$.type").value("PHYSICAL_PRODUCT"))
                .andExpect(jsonPath("$.articleNumber").value("12345"))
                .andExpect(jsonPath("$.description").value("Description 1"))
        ;
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void updateArticle_updatesArticle() throws Exception {
        String requestBody = """
                {
                    "name": "Article 1 updated",
                    "type": "DIGITAL_PRODUCT",
                    "articleNumber": "99999",
                    "description": "new description"
                }
                """;

        mockMvc.perform(post("/articles/" + article1.getId())
                    .contentType("application/json")
                    .content(requestBody)
                )
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(article1.getId()))
                .andExpect(jsonPath("$.name").value("Article 1 updated"))
                .andExpect(jsonPath("$.type").value("DIGITAL_PRODUCT"))
                .andExpect(jsonPath("$.articleNumber").value("99999"))
                .andExpect(jsonPath("$.description").value("new description"))
        ;

        this.articleRepository.findById(article1.getId()).ifPresent(article -> {
            assertEquals("Article 1 updated", article.getName());
            assertEquals(ArticleType.DIGITAL_PRODUCT, article.getArticleType());
            assertEquals("99999", article.getArticleNumber());
            assertEquals("new description", article.getDescription());
        });
    }
}
