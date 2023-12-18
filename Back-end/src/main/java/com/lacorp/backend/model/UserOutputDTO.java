package com.lacorp.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserOutputDTO {
    UserInfoOutputDTO user;
    String token;
}
