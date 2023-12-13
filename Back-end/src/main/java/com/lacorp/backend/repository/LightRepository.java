package com.lacorp.backend.repository;

import com.lacorp.backend.model.Light;
import com.lacorp.backend.model.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LightRepository extends CrudRepository<Light, Integer> {
    List<Light> findByRoom(Room room);
}
