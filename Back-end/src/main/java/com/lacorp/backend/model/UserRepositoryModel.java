package com.lacorp.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@Table(name = "user")
public class UserRepositoryModel implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String username;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<RoleRepositoryModel> roleRepositoryModels;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hue_id", referencedColumnName = "id")
    private HueRepositoryModel hueAccount;

    @OneToMany(mappedBy = "userRepositoryModel", cascade = CascadeType.ALL)
    private List<HouseRepositoryModel> houseRepositoryModels;


    public UserRepositoryModel(String username, String password, String email, List<RoleRepositoryModel> roleRepositoryModels) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roleRepositoryModels = roleRepositoryModels;
    }

    public boolean hasHueAccount() {
        return hueAccount != null;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleRepositoryModels;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
