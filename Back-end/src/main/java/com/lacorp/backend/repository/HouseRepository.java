package com.lacorp.backend.repository;

import com.lacorp.backend.model.House;
import com.lacorp.backend.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HouseRepository extends CrudRepository<House, Integer> {
    List<House> findByUser(User user);
}
