package com.lacorp.backend.mapper;

import com.lacorp.backend.model.HueRepositoryModel;
import com.lacorp.backend.model.UserRepositoryModel;
import com.lacorp.backend.model.json.hue.OAuthTokenResponse;
import com.lacorp.backend.model.json.hue.UsernameResponse;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-07T15:05:17+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (Amazon.com Inc.)"
)
public class HueMapperImpl implements HueMapper {

    @Override
    public HueRepositoryModel toHueRepositoryModel(OAuthTokenResponse tokenResponse, UsernameResponse usernameResponse, long lastRefresh, UserRepositoryModel user) {
        if ( tokenResponse == null && usernameResponse == null && user == null ) {
            return null;
        }

        HueRepositoryModel hueRepositoryModel = new HueRepositoryModel();

        if ( tokenResponse != null ) {
            hueRepositoryModel.setAccessToken( tokenResponse.getAccessToken() );
            hueRepositoryModel.setRefreshToken( tokenResponse.getRefreshToken() );
            hueRepositoryModel.setTokenType( tokenResponse.getTokenType() );
            hueRepositoryModel.setAccess_token_expires_in( String.valueOf( tokenResponse.getAccessTokenExpiresIn() ) );
            hueRepositoryModel.setRefresh_token_expires_in( String.valueOf( tokenResponse.getRefreshTokenExpiresIn() ) );
        }
        if ( usernameResponse != null ) {
            hueRepositoryModel.setUsername( usernameResponse.getUsername() );
        }
        hueRepositoryModel.setLastRefresh( lastRefresh );
        hueRepositoryModel.setUser( user );

        return hueRepositoryModel;
    }
}
