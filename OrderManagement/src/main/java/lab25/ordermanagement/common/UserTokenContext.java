package lab25.ordermanagement.common;

import lab25.ordermanagement.common.config.UserToken;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserTokenContext {

    public static UserToken getCurrentUserToken() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }

        if (authentication == null || authentication.getPrincipal() == null) { return null; }
        User user = (User) authentication.getPrincipal();

        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        if (roles.isEmpty()) { return null; }

        if (roles.contains("ROLE_ADMIN")) {
            return new UserToken(user.getUsername(), true);
        }

        return new UserToken(user.getUsername(), false);
    }
}
