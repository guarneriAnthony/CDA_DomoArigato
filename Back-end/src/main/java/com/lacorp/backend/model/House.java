package com.lacorp.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "house")
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column(name = "all_on")
    private boolean allOn = false;
    @Column(name = "any_on")
    private boolean anyOn = false;
    @Column
    private boolean favorite = false;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Room> rooms;

    public void addRoom(Room room) {
        rooms.add(room);
    }

}
