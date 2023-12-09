package com.lacorp.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "light")
public class LightRepositoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String constructor_id;
    @Column(nullable = false)
    private String constructor_name;
    @Column(nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomRepositoryModel roomRepositoryModel;

}
