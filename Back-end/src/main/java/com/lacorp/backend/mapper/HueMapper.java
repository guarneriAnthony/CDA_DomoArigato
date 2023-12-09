package com.lacorp.backend.mapper;

import com.lacorp.backend.model.HueRepositoryModel;
import com.lacorp.backend.model.UserRepositoryModel;
import com.lacorp.backend.model.json.hue.OAuthTokenResponse;
import com.lacorp.backend.model.json.hue.UsernameResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HueMapper {
    HueMapper INSTANCE = Mappers.getMapper(HueMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", source = "usernameResponse.username")
    @Mapping(target = "accessToken", source = "tokenResponse.accessToken")
    @Mapping(target = "refreshToken", source = "tokenResponse.refreshToken")
    @Mapping(target = "tokenType", source = "tokenResponse.tokenType")
    @Mapping(target = "access_token_expires_in", source = "tokenResponse.accessTokenExpiresIn")
    @Mapping(target = "refresh_token_expires_in", source = "tokenResponse.refreshTokenExpiresIn")
    @Mapping(target = "lastRefresh", source = "lastRefresh")
    HueRepositoryModel toHueRepositoryModel(OAuthTokenResponse tokenResponse, UsernameResponse usernameResponse, long lastRefresh);
}
