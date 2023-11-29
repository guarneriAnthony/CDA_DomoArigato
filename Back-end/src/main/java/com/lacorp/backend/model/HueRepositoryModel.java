package com.lacorp.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
@Entity
@Data
@NoArgsConstructor
@Table(name = "HueAccount")
public class HueRepositoryModel {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(nullable = false, unique = true)
        private String username;
        @Column(nullable = false, unique = true)
        private String accessToken;
        @Column(nullable = false, unique = true)
        private String refreshToken;
        @Column(nullable = false)
        private String tokenType;
        @Column(nullable = false)
        private String access_token_expires_in;
        @Column(nullable = false)
        private String refresh_token_expires_in;
}
