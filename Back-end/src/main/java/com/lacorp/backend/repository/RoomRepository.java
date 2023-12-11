package com.lacorp.backend.repository;

import com.lacorp.backend.model.RoomRepositoryModel;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<RoomRepositoryModel, Integer> {
}
