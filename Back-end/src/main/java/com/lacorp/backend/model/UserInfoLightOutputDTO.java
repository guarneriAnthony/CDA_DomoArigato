package com.lacorp.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class UserInfoLightOutputDTO {
    private Integer id;
    private String name;
    private String constructor_name;
}
