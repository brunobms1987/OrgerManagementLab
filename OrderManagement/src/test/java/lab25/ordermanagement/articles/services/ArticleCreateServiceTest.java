package lab25.ordermanagement.articles.services;

import lab25.ordermanagement.articles.dto.ArticleDTO;
import lab25.ordermanagement.articles.dto.ArticleUpdateDTO;
import lab25.ordermanagement.articles.enums.ArticleType;
import lab25.ordermanagement.articles.models.ArticleEntity;
import lab25.ordermanagement.articles.policies.ArticlePolicy;
import lab25.ordermanagement.articles.mapper.ArticleMapper;
import lab25.ordermanagement.articles.repositories.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ArticleCreateServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private ArticleMapper articleMapper;

    @Mock
    private ArticlePolicy articlePolicy;

    @InjectMocks
    private ArticleCreateService service;

    @Test
    void createArticle_savesAndReturnsDto_whenPolicyAllows() {
        ArticleUpdateDTO dto = new ArticleUpdateDTO(
                ArticleType.PHYSICAL_PRODUCT,
                "Test Article",
                "ART-123",
                "A test article"
        );

        ArticleEntity toSave = new ArticleEntity()
                .setArticleType(ArticleType.PHYSICAL_PRODUCT)
                .setName("Test Article")
                .setArticleNumber("ART-123")
                .setDescription("A test article");

        ArticleEntity saved = new ArticleEntity()
                .setId(42L)
                .setArticleType(ArticleType.PHYSICAL_PRODUCT)
                .setName("Test Article")
                .setArticleNumber("ART-123")
                .setDescription("A test article");

        ArticleDTO expected = new ArticleDTO("42", ArticleType.PHYSICAL_PRODUCT, "Test Article", "ART-123", "A test article");

        when(articleMapper.createFromDTO(dto)).thenReturn(toSave);
        when(articlePolicy.canCreate(any(ArticleEntity.class), any(Authentication.class))).thenReturn(true);
        when(articleRepository.save(toSave)).thenReturn(saved);
        when(articleMapper.toDTO(saved)).thenReturn(expected);

        ArticleDTO result = service.createArticle(dto);

        assertEquals(expected.id(), result.id());
        assertEquals(expected.name(), result.name());
        verify(articleRepository).save(toSave);
    }
}