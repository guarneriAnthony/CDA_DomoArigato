package com.lacorp.backend.mapper;

import com.lacorp.backend.model.AccountHue;
import com.lacorp.backend.model.json.hue.OAuthTokenResponse;
import com.lacorp.backend.model.json.hue.UsernameResponse;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-23T16:40:17+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (Amazon.com Inc.)"
)
public class HueMapperImpl implements HueMapper {

    @Override
    public AccountHue toAccountHueRepositoryModel(OAuthTokenResponse tokenResponse, UsernameResponse usernameResponse, long lastRefresh) {
        if ( tokenResponse == null && usernameResponse == null ) {
            return null;
        }

        AccountHue accountHue = new AccountHue();

        if ( tokenResponse != null ) {
            accountHue.setAccessToken( tokenResponse.getAccessToken() );
            accountHue.setRefreshToken( tokenResponse.getRefreshToken() );
            accountHue.setTokenType( tokenResponse.getTokenType() );
            accountHue.setAccess_token_expires_in( String.valueOf( tokenResponse.getAccessTokenExpiresIn() ) );
            accountHue.setRefresh_token_expires_in( String.valueOf( tokenResponse.getRefreshTokenExpiresIn() ) );
        }
        if ( usernameResponse != null ) {
            accountHue.setUsername( usernameResponse.getUsername() );
        }
        accountHue.setLastRefresh( lastRefresh );

        return accountHue;
    }
}
