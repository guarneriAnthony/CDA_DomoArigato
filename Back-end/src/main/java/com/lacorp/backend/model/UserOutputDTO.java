package com.lacorp.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@AllArgsConstructor
public class UserOutputDTO {
    UserDetails username;
    String token;
}
