package com.lacorp.backend.mapper;

import com.lacorp.backend.model.User;
import com.lacorp.backend.model.UserInfoOutputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "hasHueAccount", expression = "java(user.hasHueAccount())")
    @Mapping(target = "hasGoveeAccount", expression = "java(user.hasGoveeAccount())")
    UserInfoOutputDTO userToUserInfoOutputDTO(User user);

}
