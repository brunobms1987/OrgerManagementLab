package lab25.ordermanagement.articles.services;

import lab25.ordermanagement.articles.dto.ArticleDTO;
import lab25.ordermanagement.articles.dto.ArticleUpdateDTO;
import lab25.ordermanagement.articles.mapper.ArticleMapper;
import lab25.ordermanagement.articles.models.ArticleEntity;
import lab25.ordermanagement.articles.policies.ArticlePolicy;
import lab25.ordermanagement.articles.repositories.ArticleRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ArticleCreateService {
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final ArticlePolicy articlePolicy;

    public ArticleCreateService(ArticleRepository articleRepository, ArticleMapper articleMapper, ArticlePolicy articlePolicy) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
        this.articlePolicy = articlePolicy;
    }

    public ArticleDTO createArticle(ArticleUpdateDTO createDto) {
        ArticleEntity createArticleEntity = articleMapper.createFromDTO(createDto);
        articlePolicy.canCreate(createArticleEntity, SecurityContextHolder.getContext().getAuthentication());
        ArticleEntity saved = articleRepository.save(createArticleEntity);
        return articleMapper.toDTO(saved);
    }

}
