package com.lacorp.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class UserInfoHouseOutputDTO {
    private String name;
    private boolean favorite;
    private List<UserInfoLightOutputDTO> lights;
}
