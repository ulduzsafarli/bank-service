package java.az.bankservice.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.az.bankservice.enumeration.auth.Role;

import static java.az.bankservice.enumeration.auth.Permission.*;
import static java.az.bankservice.enumeration.auth.UrlMapping.*;
import static org.springframework.http.HttpMethod.*;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtFilter jwtAuthFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers(PERMIT_ALL.getUrls()).permitAll()
                                .requestMatchers(ADMIN.getUrls()).hasRole(Role.ADMIN.name())
                                .requestMatchers(MANAGER.getUrls()).hasAnyRole(Role.ADMIN.name(), Role.MANAGER.name())
                                .requestMatchers(GET, LIST_URL.getUrls()).hasAnyAuthority(ADMIN_READ.getValue(), MANAGER_READ.getValue())
                                .requestMatchers(PUT, LIST_URL.getUrls()).hasAnyAuthority(ADMIN_UPDATE.getValue(), MANAGER_UPDATE.getValue())
                                .requestMatchers(DELETE, LIST_URL.getUrls()).hasAuthority(ADMIN_DELETE.getValue())
                                .requestMatchers(ANY_AUTHENTICATED.getUrls()).authenticated()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((request, response, authException) ->
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED)
                        )
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                response.setStatus(HttpServletResponse.SC_FORBIDDEN)
                        )
                );
        return http.build();
    }
}