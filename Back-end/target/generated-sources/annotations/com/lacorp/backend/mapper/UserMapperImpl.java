package com.lacorp.backend.mapper;

import com.lacorp.backend.model.UserInfoOutputDTO;
import com.lacorp.backend.model.UserRepositoryModel;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-07T15:06:43+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (Amazon.com Inc.)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public UserInfoOutputDTO userDetailsToUserInfoOutputDTO(UserRepositoryModel user) {
        if ( user == null ) {
            return null;
        }

        UserInfoOutputDTO userInfoOutputDTO = new UserInfoOutputDTO();

        userInfoOutputDTO.setUsername( user.getUsername() );
        userInfoOutputDTO.setEmail( user.getEmail() );

        userInfoOutputDTO.setHasHueAccount( user.hasHueAccount() );

        return userInfoOutputDTO;
    }
}
