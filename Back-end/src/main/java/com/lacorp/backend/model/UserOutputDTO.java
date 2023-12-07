package com.lacorp.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@AllArgsConstructor
public class UserOutputDTO {
    UserInfoOutputDTO user;
    String token;
}
