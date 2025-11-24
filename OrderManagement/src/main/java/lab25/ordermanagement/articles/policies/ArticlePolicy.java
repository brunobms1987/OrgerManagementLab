package lab25.ordermanagement.articles.policies;

import lab25.ordermanagement.articles.models.ArticleEntity;
import lab25.ordermanagement.common.config.UserToken;
import lab25.ordermanagement.common.policies.BasePolicy;
import org.springframework.stereotype.Component;

@Component
public class ArticlePolicy extends BasePolicy<ArticleEntity> {

    public boolean canShow(UserToken token, ArticleEntity entity) {
        return token != null;
    }

    public boolean canUpdate(UserToken token, ArticleEntity entity) {
        if (token == null) { return false; }
        return token.isAdmin();
    }
}
