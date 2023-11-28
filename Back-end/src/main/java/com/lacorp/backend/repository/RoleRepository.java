package com.lacorp.backend.repository;


import com.lacorp.backend.model.RoleRepositoryModel;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<RoleRepositoryModel, Integer> {
    RoleRepositoryModel getRoleByName(String name);
}
