package com.lacorp.backend.repository;


import com.lacorp.backend.model.HueRepositoryModel;
import org.springframework.data.repository.CrudRepository;

public interface HueRepository extends CrudRepository<HueRepositoryModel, Integer> {
}
