package com.lacorp.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HueAccountInputDTO {
    String username;
    String accessToken;
    String refreshToken;
    String tokenType;
    String access_token_expires_in;
    String refresh_token_expires_in;
}
