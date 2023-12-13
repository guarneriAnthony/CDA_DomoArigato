package com.lacorp.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;
    @Column(name = "all_on")
    private boolean allOn = false;
    @Column(name = "any_on")
    private boolean anyOn = false;

    @ManyToOne
    @JoinColumn(name = "house_id")
    @JsonIgnore
    private House house;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Light> lights;

    public void addLight(Light light) {
        lights.add(light);
    }

}
