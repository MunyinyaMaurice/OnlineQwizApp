package com.backend.online_qwiz.secuirity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class OAuth2Configuration {
    
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(googleClientRegistration());
    }

    private ClientRegistration googleClientRegistration() {
        return ClientRegistration.withRegistrationId("google")
            .clientId(clientId)
            .clientSecret(clientSecret)
            .scope("openid", "email", "profile")
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .redirectUri("http://localhost:23901/login/oauth2/code/{registrationId}")
            // .redirectUri("http://localhost:23901/api/v2/auth/quiz/all_questions")
            .authorizationUri("https://accounts.google.com/o/oauth2/auth")
            .tokenUri("https://oauth2.googleapis.com/token")
            .userInfoUri("https://openidconnect.googleapis.com/v1/userinfo")
            .userNameAttributeName("email")
            .clientName("Google")
            .build();
    }
}

