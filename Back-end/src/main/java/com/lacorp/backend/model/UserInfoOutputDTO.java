package com.lacorp.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@NoArgsConstructor
public class UserInfoOutputDTO {
    String username;
    String email;
    Boolean hasHueAccount;
}
