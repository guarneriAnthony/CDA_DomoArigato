package com.lacorp.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class RoomOutputDTO {

    private Integer id;
    private String name;
    private boolean allOn;
    private boolean anyOn;

}
