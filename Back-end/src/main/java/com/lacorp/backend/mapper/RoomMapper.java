package com.lacorp.backend.mapper;

import com.lacorp.backend.model.Room;
import com.lacorp.backend.model.RoomOutputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RoomMapper {
    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    RoomOutputDTO roomToRoomOutputDTO(Room room);

    List<RoomOutputDTO> roomToRoomsOutputDTOs(List<Room> rooms);
}
