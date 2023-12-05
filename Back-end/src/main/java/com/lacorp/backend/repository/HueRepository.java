package com.lacorp.backend.repository;


import com.lacorp.backend.model.HueRepositoryModel;
import com.lacorp.backend.model.UserRepositoryModel;
import org.springframework.data.repository.CrudRepository;

public interface HueRepository extends CrudRepository<HueRepositoryModel, Integer> {
    HueRepositoryModel findByUser(UserRepositoryModel user);
}
