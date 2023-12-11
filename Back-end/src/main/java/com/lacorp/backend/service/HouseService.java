package com.lacorp.backend.service;

import com.lacorp.backend.model.HouseRepositoryModel;
import com.lacorp.backend.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class HouseService {
    @Autowired
    private HouseRepository houseRepository;


    public HouseRepositoryModel getHouseById(Integer id) {
        return houseRepository.findById(id).get();
    }
}



