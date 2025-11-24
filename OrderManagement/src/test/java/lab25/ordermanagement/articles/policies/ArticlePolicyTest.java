package lab25.ordermanagement.articles.policies;

import lab25.ordermanagement.articles.models.ArticleEntity;
import lab25.ordermanagement.common.config.UserToken;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArticlePolicyTest {

    private final ArticlePolicy policy = new ArticlePolicy();

    private UserToken normalUser = new UserToken("user1", false);
    private UserToken adminUser = new UserToken("user2", true);


    private ArticleEntity article = new ArticleEntity()
            .setName("Article 1");

    @Test
    void canShow() {
        assertFalse(policy.canShow(null, article));
        assertTrue(policy.canShow(normalUser, article));
        assertTrue(policy.canShow(adminUser, article));
    }

    @Test
    void canCreate() {
        assertFalse(policy.canCreate(null, article));
        assertFalse(policy.canCreate(normalUser, article));
        assertFalse(policy.canCreate(adminUser, article));
    }

    @Test
    void canUpdate() {
        assertFalse(policy.canUpdate(null, article));
        assertFalse(policy.canUpdate(normalUser, article));
        assertTrue(policy.canUpdate(adminUser, article));
    }

    @Test
    void canDelete() {
        assertFalse(policy.canDelete(null, article));
        assertFalse(policy.canDelete(normalUser, article));
        assertFalse(policy.canDelete(adminUser, article));
    }
}
