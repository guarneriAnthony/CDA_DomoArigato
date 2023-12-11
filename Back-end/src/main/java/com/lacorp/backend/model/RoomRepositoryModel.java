package com.lacorp.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    @Column(nullable = false)
    private boolean turnedOn;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id")
    private Set<LightRepositoryModel> lightRepositoryModels;



}
