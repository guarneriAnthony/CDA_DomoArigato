package com.lacorp.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;


@Entity
@Data
@NoArgsConstructor
@Table(name = "house")
public class HouseRepositoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserRepositoryModel userRepositoryModel;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "house_id")
    private List<RoomRepositoryModel> roomRepositoryModels;

}
