package com.lacorp.backend.mapper;

import com.lacorp.backend.model.Room;
import com.lacorp.backend.model.RoomOutputDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-23T16:40:17+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (Amazon.com Inc.)"
)
public class RoomMapperImpl implements RoomMapper {

    @Override
    public RoomOutputDTO roomToRoomOutputDTO(Room room) {
        if ( room == null ) {
            return null;
        }

        RoomOutputDTO roomOutputDTO = new RoomOutputDTO();

        roomOutputDTO.setId( room.getId() );
        roomOutputDTO.setName( room.getName() );
        roomOutputDTO.setAllOn( room.isAllOn() );
        roomOutputDTO.setAnyOn( room.isAnyOn() );

        return roomOutputDTO;
    }

    @Override
    public List<RoomOutputDTO> roomToRoomsOutputDTOs(List<Room> rooms) {
        if ( rooms == null ) {
            return null;
        }

        List<RoomOutputDTO> list = new ArrayList<RoomOutputDTO>( rooms.size() );
        for ( Room room : rooms ) {
            list.add( roomToRoomOutputDTO( room ) );
        }

        return list;
    }
}
