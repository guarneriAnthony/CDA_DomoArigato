package com.lacorp.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserInfoOutputDTO {
    String username;
    String email;
    Boolean hasHueAccount;
    List<House> houses;
}
