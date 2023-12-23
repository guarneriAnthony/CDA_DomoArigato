package com.lacorp.backend.mapper;

import com.lacorp.backend.model.Light;
import com.lacorp.backend.model.LightOutputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LightMapper {
    LightMapper INSTANCE = Mappers.getMapper(LightMapper.class);

    LightOutputDTO lightToLightOutputDTO(Light light);

    List<LightOutputDTO> lightToLightsOutputDTOs(List<Light> lights);
}
