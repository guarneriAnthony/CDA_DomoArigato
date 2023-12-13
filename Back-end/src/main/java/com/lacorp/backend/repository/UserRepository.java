package com.lacorp.backend.repository;

import com.lacorp.backend.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);

    User findByEmail(String email);
}
