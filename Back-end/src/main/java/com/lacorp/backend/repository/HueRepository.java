package com.lacorp.backend.repository;


import com.lacorp.backend.model.HueRepositoryModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface HueRepository extends CrudRepository<HueRepositoryModel, Integer> {
}
