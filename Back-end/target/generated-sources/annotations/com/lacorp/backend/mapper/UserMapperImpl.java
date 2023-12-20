package com.lacorp.backend.mapper;

import com.lacorp.backend.model.House;
import com.lacorp.backend.model.User;
import com.lacorp.backend.model.UserInfoOutputDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-19T15:11:03+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (Amazon.com Inc.)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public UserInfoOutputDTO userToUserInfoOutputDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserInfoOutputDTO userInfoOutputDTO = new UserInfoOutputDTO();

        userInfoOutputDTO.setUsername( user.getUsername() );
        userInfoOutputDTO.setEmail( user.getEmail() );
        List<House> list = user.getHouses();
        if ( list != null ) {
            userInfoOutputDTO.setHouses( new ArrayList<House>( list ) );
        }

        userInfoOutputDTO.setHasHueAccount( user.hasHueAccount() );

        return userInfoOutputDTO;
    }
}
