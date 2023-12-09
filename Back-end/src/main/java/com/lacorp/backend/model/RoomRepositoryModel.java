package com.lacorp.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "room")
public class RoomRepositoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private HouseRepositoryModel houseRepositoryModel;
    @OneToMany(mappedBy = "roomRepositoryModel")
    private List<LightRepositoryModel> lightRepositoryModels;

}
