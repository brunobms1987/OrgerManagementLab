package lab25.ordermanagement.common.policies;

import lab25.ordermanagement.common.UserTokenContext;
import lab25.ordermanagement.common.config.UserToken;
import org.springframework.security.access.AccessDeniedException;

import java.util.function.BiPredicate;

public abstract class BasePolicy<T> {

    public void assertCanShow(T entity) {
        assertCan(entity, this::canShow);
    }

    public void assertCanCreate(T entity) {
        assertCan(entity, this::canCreate);
    }

    public void assertCanUpdate(T entity) {
        assertCan(entity, this::canUpdate);
    }

    public void assertCanDelete(T entity) {
        assertCan(entity, this::canDelete);
    }

    public void assertCan(T entity, BiPredicate<UserToken, T> action) {
        UserToken currentUser = UserTokenContext.getCurrentUserToken();
        if (currentUser == null) {
            throw new AccessDeniedException("Authentication required to update entity");
        }

        if (!action.test(currentUser, entity)) {
            throw new AccessDeniedException("User does not have permission to " + action.toString().toLowerCase() + " this entity");
        }
    }

    public boolean canShow(UserToken token, T entity) {
        return false;
    }

    public boolean canCreate(UserToken token, T entity) {
        return false;
    }

    public boolean canUpdate(UserToken token, T entity) {
        return false;
    }

    public boolean canDelete(UserToken token, T entity) {
        return false;
    }

    public boolean can(T entity, BiPredicate<UserToken, T> action) {
        UserToken currentUser = UserTokenContext.getCurrentUserToken();

        return action.test(currentUser, entity);
    }
}

