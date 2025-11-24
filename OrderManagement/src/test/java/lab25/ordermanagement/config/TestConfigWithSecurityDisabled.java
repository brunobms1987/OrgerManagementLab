package lab25.ordermanagement.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@TestConfiguration
public class TestConfigWithSecurityDisabled {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Disable CSRF
        return http
                .cors(AbstractHttpConfigurer::disable)  // Disable CORS
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF
                // Permit all requests without authentication
                .authorizeHttpRequests(authz -> authz.anyRequest().permitAll())
                .build();
    }
}
