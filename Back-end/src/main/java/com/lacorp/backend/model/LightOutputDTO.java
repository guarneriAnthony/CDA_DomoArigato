package com.lacorp.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class LightOutputDTO {
    private Integer id;
    private String name;
    private String constructor_name;
    private boolean isOn;

}
