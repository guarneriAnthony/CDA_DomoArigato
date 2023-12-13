package com.lacorp.backend.repository;


import com.lacorp.backend.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role getRoleByName(String name);
}
