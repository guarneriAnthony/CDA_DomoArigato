package com.lacorp.backend.mapper;

import com.lacorp.backend.model.Light;
import com.lacorp.backend.model.LightOutputDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-23T15:35:11+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (Amazon.com Inc.)"
)
public class LightMapperImpl implements LightMapper {

    @Override
    public LightOutputDTO lightToLightOutputDTO(Light light) {
        if ( light == null ) {
            return null;
        }

        LightOutputDTO lightOutputDTO = new LightOutputDTO();

        lightOutputDTO.setId( light.getId() );
        lightOutputDTO.setName( light.getName() );
        lightOutputDTO.setConstructor_name( light.getConstructor_name() );
        lightOutputDTO.setOn( light.isOn() );

        return lightOutputDTO;
    }

    @Override
    public List<LightOutputDTO> lightToLightsOutputDTOs(List<Light> lights) {
        if ( lights == null ) {
            return null;
        }

        List<LightOutputDTO> list = new ArrayList<LightOutputDTO>( lights.size() );
        for ( Light light : lights ) {
            list.add( lightToLightOutputDTO( light ) );
        }

        return list;
    }
}
