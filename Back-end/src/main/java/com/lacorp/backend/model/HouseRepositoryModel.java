package com.lacorp.backend.model;

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


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "house_id")
    private Set<RoomRepositoryModel> roomRepositoryModels;

}
