package com.lacorp.backend.repository;

import com.lacorp.backend.model.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, Integer> {
}
