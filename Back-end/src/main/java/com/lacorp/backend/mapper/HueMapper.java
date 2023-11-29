package com.lacorp.backend.mapper;

import com.lacorp.backend.model.HueAccountInputDTO;
import com.lacorp.backend.model.HueRepositoryModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HueMapper {
    HueMapper INSTANCE = Mappers.getMapper(HueMapper.class);
    @Mapping(target = "id", ignore = true)
    HueRepositoryModel hueAccountInputDtoToHueRepositoryModel(HueAccountInputDTO hueAccountInputDTO);
}
