package com.lacorp.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserServiceModel {
    String username;
    String email;
    Boolean hasHueAccount;
}
