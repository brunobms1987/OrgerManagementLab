package lab25.ordermanagement.articles.services;

import lab25.ordermanagement.articles.dto.ArticleDTO;
import lab25.ordermanagement.articles.dto.ArticleUpdateDTO;
import lab25.ordermanagement.articles.mapper.ArticleMapper;
import lab25.ordermanagement.articles.models.ArticleEntity;
import lab25.ordermanagement.articles.policies.ArticlePolicy;
import lab25.ordermanagement.articles.repositories.ArticleRepository;
import org.springframework.stereotype.Service;

@Service
public class ArticleUpdateService {
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final ArticlePolicy articlePolicy;

    public ArticleUpdateService(ArticleRepository articleRepository, ArticleMapper articleMapper, ArticlePolicy articlePolicy) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
        this.articlePolicy = articlePolicy;
    }

    public ArticleDTO updateArticle(Long articleId, ArticleUpdateDTO articleUpdateDTO) {
        ArticleEntity article = articleRepository.findById(articleId).orElseThrow();
        articlePolicy.assertCanUpdate(article);

        articleMapper.updateFromDTO(articleUpdateDTO, article);

        articleRepository.save(article);

        return articleMapper.toDTO(article);
    }
}
