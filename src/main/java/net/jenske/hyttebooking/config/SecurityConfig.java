package net.jenske.hyttebooking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Configures our application with Spring Security to restrict access to our API endpoints.
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // TODO - configure RBAC for our API endpoints
        return http
                .authorizeHttpRequests((authorize) -> authorize
                        // All /api/ endpoints require authentication
                        .requestMatchers("/api/**").authenticated()
                        .requestMatchers("/api/bookings/**").hasAuthority("SCOPE_write:bookings") // Specific scope for bookings
                        .requestMatchers("/api/cabins/**", "/api/users/**").hasAuthority("SCOPE_crud:admin") // Admin scope for cabins and users
                )
                .cors(withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(withDefaults())
                )
                .build();
    }
}
