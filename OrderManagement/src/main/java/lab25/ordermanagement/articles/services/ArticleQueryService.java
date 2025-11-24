package lab25.ordermanagement.articles.services;

import lab25.ordermanagement.articles.dto.ArticleDTO;
import lab25.ordermanagement.articles.mapper.ArticleMapper;
import lab25.ordermanagement.articles.models.ArticleEntity;
import lab25.ordermanagement.articles.policies.ArticlePolicy;
import lab25.ordermanagement.articles.repositories.ArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ArticleQueryService {
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final ArticlePolicy articlePolicy;

    public ArticleQueryService(ArticleRepository articleRepository, ArticleMapper articleMapper, ArticlePolicy articlePolicy) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
        this.articlePolicy = articlePolicy;
    }

    public ArticleDTO getArticle(Long id) {
        ArticleEntity article = articleRepository.findById(id).orElseThrow();
        this.articlePolicy.assertCanShow(article);
        return articleMapper.toDTO(article);
    }

    public Page<ArticleDTO> getArticles(Pageable page) {
        this.articlePolicy.assertCanShow(new ArticleEntity());

        Pageable sortedPage = page.getSort().isSorted()
                ? page
                : PageRequest.of(page.getPageNumber(), page.getPageSize(), Sort.by(Sort.Direction.ASC, "createdAt"));
        Page<ArticleEntity> currentPage = articleRepository.findAll(sortedPage);
        return currentPage.map(articleMapper::toDTO);
    }
}
