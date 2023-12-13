package com.lacorp.backend.repository;

import com.lacorp.backend.model.House;
import com.lacorp.backend.model.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoomRepository extends CrudRepository<Room, Integer> {
    List<Room> findByHouse(House house);
}
