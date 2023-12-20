package com.lacorp.backend.mapper;

import com.lacorp.backend.model.House;
import com.lacorp.backend.model.HouseOutputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface HouseMapper {
    HouseMapper INSTANCE = Mappers.getMapper(HouseMapper.class);

    HouseOutputDTO houseToHouseOutputDTO(House house);

    List<HouseOutputDTO> housesToHouseOutputDTOs(List<House> houses);
}
