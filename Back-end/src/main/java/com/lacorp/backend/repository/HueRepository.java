package com.lacorp.backend.repository;


import com.lacorp.backend.model.AccountHue;
import org.springframework.data.repository.CrudRepository;

public interface HueRepository extends CrudRepository<AccountHue, Integer> {
}
