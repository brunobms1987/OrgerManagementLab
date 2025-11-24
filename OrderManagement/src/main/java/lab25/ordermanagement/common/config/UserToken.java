package lab25.ordermanagement.common.config;

public record UserToken(
        String userId,
        boolean isAdmin
        ) {
}
