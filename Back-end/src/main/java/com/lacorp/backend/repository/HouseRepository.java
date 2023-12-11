package com.lacorp.backend.repository;

import com.lacorp.backend.model.HouseRepositoryModel;
import org.springframework.data.repository.CrudRepository;

public interface HouseRepository extends CrudRepository<HouseRepositoryModel, Integer> {
}
