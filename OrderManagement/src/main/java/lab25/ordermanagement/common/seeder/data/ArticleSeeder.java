package lab25.ordermanagement.common.seeder.data;

import lab25.ordermanagement.articles.enums.ArticleType;
import lab25.ordermanagement.articles.models.ArticleEntity;
import lab25.ordermanagement.articles.repositories.ArticleRepository;
import lab25.ordermanagement.common.seeder.BaseSeeder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ArticleSeeder extends BaseSeeder {
    private static final String TABLE_NAME = "articles";

    private final ArticleRepository articleRepository;

    public ArticleSeeder(JdbcTemplate jdbcTemplate, ArticleRepository articleRepository) {
        super(jdbcTemplate);
        this.articleRepository = articleRepository;
    }

    @Override
    protected void executeSeed() {
        seedArticles();
    }

    private void seedArticles() {

        for (int i = 0; i < 11; i++) {
            createArticle(i);
        }
    }

    private void createArticle(int i) {
        ArticleEntity article = new ArticleEntity()
                .setArticleType(ArticleType.PHYSICAL_PRODUCT)
                .setArticleNumber("1111" + i)
                .setName("Article " + i);
        articleRepository.save(article);
    }

    @Override
    protected boolean shouldSkipSeeding() {
        return dataExists(TABLE_NAME);
    }

    @Override public int getOrder() { return 1; }
    @Override public String getName() { return "Articles"; }
}
