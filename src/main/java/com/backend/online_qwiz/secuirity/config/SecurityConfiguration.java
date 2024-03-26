package com.backend.online_qwiz.secuirity.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

//import static com.Awesome.Challenge.Online.Marketplace.API.model.Permission.ADMIN_DELETE;
//import com.backend.online_qwiz.model.Permission.*;
import com.backend.online_qwiz.model.Role.*;

import static com.backend.online_qwiz.model.Role.ADMIN;
import static com.backend.online_qwiz.model.Role.STUDENT;
import static org.springframework.http.HttpMethod.*;
//import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
    private static final String[] WHITE_LIST_URL = {"/api/v1/auth/**","/api/v2/auth/**",
            "/api/l1/search/{searchParam}",
            "/api/l1/listed",
            //LIST OF AUTHORIZED SWAGGER URLs
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"
            };
//    private static final String[] LIST_FOR_LOGGED_IN_URL = {
//            "/api/l2/order/press_order",
//            "/api/l2/reviews/submitReview",
//            "/api/l2/users/update/{userId}",
//    };
//    private static final String[] ADMIN_SELLER_LIST_URL = {
//
//    };
//    private static final String[] ADMIN_LIST_URL = {
//
//
//    };
//    private static final String[]
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            // Customize the error response
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Access Denied: " + accessDeniedException.getMessage());
        };
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AccessDeniedHandler accessDeniedHandler) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(req -> req
//                        .requestMatchers(WHITE_LIST_URL)
                .requestMatchers("/api/v1/auth/**","/api/v2/auth/**")
                        .permitAll()
//                        .requestMatchers(LIST_FOR_LOGGED_IN_URL).hasAnyRole(ADMIN.name(), STUDENT.name(), STUDENT.name())
//                        .requestMatchers(ADMIN_SELLER_LIST_URL).hasAnyRole(ADMIN.name(), STUDENT.name())
//                        .requestMatchers(ADMIN_LIST_URL).hasRole(ADMIN.name())
                        .anyRequest()
                        .authenticated())
                .exceptionHandling(exceptions -> exceptions
                .accessDeniedHandler(accessDeniedHandler)
        )

                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))

                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout.logoutUrl("/api/v1/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                );
        return http.build();
    }
}
