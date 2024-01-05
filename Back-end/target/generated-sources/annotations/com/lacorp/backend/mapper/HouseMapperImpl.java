package com.lacorp.backend.mapper;

import com.lacorp.backend.model.House;
import com.lacorp.backend.model.HouseOutputDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-23T16:40:17+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (Amazon.com Inc.)"
)
public class HouseMapperImpl implements HouseMapper {

    @Override
    public HouseOutputDTO houseToHouseOutputDTO(House house) {
        if ( house == null ) {
            return null;
        }

        HouseOutputDTO houseOutputDTO = new HouseOutputDTO();

        houseOutputDTO.setId( house.getId() );
        houseOutputDTO.setName( house.getName() );
        houseOutputDTO.setAllOn( house.isAllOn() );
        houseOutputDTO.setAnyOn( house.isAnyOn() );
        houseOutputDTO.setFavorite( house.isFavorite() );

        return houseOutputDTO;
    }

    @Override
    public List<HouseOutputDTO> housesToHouseOutputDTOs(List<House> houses) {
        if ( houses == null ) {
            return null;
        }

        List<HouseOutputDTO> list = new ArrayList<HouseOutputDTO>( houses.size() );
        for ( House house : houses ) {
            list.add( houseToHouseOutputDTO( house ) );
        }

        return list;
    }
}
