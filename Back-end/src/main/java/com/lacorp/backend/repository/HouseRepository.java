package com.lacorp.backend.repository;

import com.lacorp.backend.model.House;
import org.springframework.data.repository.CrudRepository;

public interface HouseRepository extends CrudRepository<House, Integer> {
}
