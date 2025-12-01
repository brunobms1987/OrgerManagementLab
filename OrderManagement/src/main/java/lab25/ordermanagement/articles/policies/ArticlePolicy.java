package lab25.ordermanagement.articles.policies;

import lab25.ordermanagement.articles.models.ArticleEntity;
import lab25.ordermanagement.common.config.UserToken;
import lab25.ordermanagement.common.policies.BasePolicy;
import org.springframework.security.core.Authentication;
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

    public boolean canCreate(UserToken token, ArticleEntity entity) {
        if (token == null) { return false; }
        return token.isAdmin();
    }

    public boolean canCreate(ArticleEntity entity, Authentication authentication) {
        if (authentication == null) {
            return canCreate((UserToken) null, entity);
        }
        Object principal = authentication.getPrincipal();
        UserToken token = null;
        if (principal instanceof UserToken) {
            token = (UserToken) principal;
        }
        return canCreate(token, entity);
    }
}
