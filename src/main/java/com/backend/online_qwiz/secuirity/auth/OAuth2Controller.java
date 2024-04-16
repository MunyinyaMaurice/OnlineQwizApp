package com.backend.online_qwiz.secuirity.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuth2Controller {

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @GetMapping("/oauth2/authorization/google")
    public String googleAuth() {
        // Retrieve Google ClientRegistration
        ClientRegistration googleRegistration = clientRegistrationRepository.findByRegistrationId("google");

        if (googleRegistration == null) {
            // Handle error if Google ClientRegistration is not found
            return "Error: Google OAuth2 configuration not found";
        }

        // Redirect URL to Google OAuth2 authorization endpoint
        return "redirect:" + googleRegistration.getProviderDetails().getAuthorizationUri()
                + "?client_id=" + googleRegistration.getClientId()
                + "&response_type=code"
                + "&scope=" + googleRegistration.getScopes().toString().replaceAll("[\\[\\],]", "")
                + "&redirect_uri=http://localhost:23901/login/oauth2/code/google";
    }
}
