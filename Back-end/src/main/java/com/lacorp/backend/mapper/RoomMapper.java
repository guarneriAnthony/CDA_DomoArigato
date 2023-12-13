package com.lacorp.backend.mapper;

import com.lacorp.backend.model.Room;
import com.lacorp.backend.model.RoomInputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoomMapper {
    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    @Mapping(target = "turnedOn", ignore = true)
    @Mapping(target = "lights", ignore = true)
    @Mapping(target = "house", ignore = true)
    Room roomInputDTOToRoom(RoomInputDTO roomInputDTO);
}
