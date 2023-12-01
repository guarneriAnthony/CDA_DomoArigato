package com.lacorp.backend.repository;

import com.lacorp.backend.model.UserRepositoryModel;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserRepositoryModel, Integer> {
    UserRepositoryModel findByUsername(String username);

    UserRepositoryModel findByEmail(String email);
}
