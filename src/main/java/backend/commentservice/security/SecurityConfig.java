package backend.commentservice.security;

import backend.commentservice.dto.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtRequestFilter jwtRequestFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/news/*/comments/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/news/*/comments/**").hasAnyRole(Role.ADMIN.name(), Role.SUBSCRIBER.name())
                        .requestMatchers(HttpMethod.PUT, "/news/*/comments/**").hasAnyRole(Role.ADMIN.name(), Role.SUBSCRIBER.name())
                        .requestMatchers(HttpMethod.DELETE, "/news/*/comments/**").hasAnyRole(Role.ADMIN.name(), Role.SUBSCRIBER.name())
                        .anyRequest().authenticated()
                )
                .exceptionHandling(eh -> eh.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
