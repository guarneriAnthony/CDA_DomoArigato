package com.lacorp.backend.mapper;

import com.lacorp.backend.model.UserInfoOutputDTO;
import com.lacorp.backend.model.UserRepositoryModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "hasHueAccount", expression = "java(user.hasHueAccount())")
    UserInfoOutputDTO userDetailsToUserInfoOutputDTO(UserRepositoryModel user);
}
