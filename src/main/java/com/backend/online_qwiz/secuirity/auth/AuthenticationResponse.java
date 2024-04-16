package com.backend.online_qwiz.secuirity.auth;

import com.backend.online_qwiz.model.Role;
import com.backend.online_qwiz.model.User.UserBuilder;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("user_role")
    private String role;
    
}
