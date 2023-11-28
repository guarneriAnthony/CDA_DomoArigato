package com.lacorp.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;


@NoArgsConstructor
@Entity
@Data
@Table(name = "role")
public class RoleRepositoryModel implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}