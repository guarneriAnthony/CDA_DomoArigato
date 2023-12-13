package com.lacorp.backend.mapper;

import com.lacorp.backend.model.Room;
import com.lacorp.backend.model.RoomInputDTO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-13T17:11:33+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (Amazon.com Inc.)"
)
public class RoomMapperImpl implements RoomMapper {

    @Override
    public Room roomInputDTOToRoom(RoomInputDTO roomInputDTO) {
        if ( roomInputDTO == null ) {
            return null;
        }

        Room room = new Room();

        room.setId( roomInputDTO.id() );
        room.setName( roomInputDTO.name() );

        return room;
    }
}
