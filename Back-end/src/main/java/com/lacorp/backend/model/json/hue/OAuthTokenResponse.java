package com.lacorp.backend.model.json.hue;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OAuthTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private long expiresIn;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("access_token_expires_in")
    private long accessTokenExpiresIn;

    @JsonProperty("refresh_token_expires_in")
    private long refreshTokenExpiresIn;
}
