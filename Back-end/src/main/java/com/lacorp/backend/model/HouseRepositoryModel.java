package com.lacorp.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


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
    private UserRepositoryModel userRepositoryModel;

    @OneToMany(mappedBy = "houseRepositoryModel")
    private List<RoomRepositoryModel> roomRepositoryModels;


}
