package com.lacorp.backend.controller;

import com.lacorp.backend.model.HouseRepositoryModel;
import com.lacorp.backend.model.LightRepositoryModel;
import com.lacorp.backend.model.RoomRepositoryModel;
import com.lacorp.backend.model.UserRepositoryModel;
import com.lacorp.backend.service.HouseService;
import com.lacorp.backend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class HouseController {
    @Autowired
    private HouseService houseService;


    @GetMapping("/houses")
    public  ResponseEntity<Set<HouseRepositoryModel>> getUserHouses(Authentication authentication){
        UserRepositoryModel user = (UserRepositoryModel) authentication.getPrincipal();
        if (user != null){
            Set<HouseRepositoryModel> userHouses = user.getHouseRepositoryModels();
            return ResponseEntity.ok(userHouses);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<HouseRepositoryModel> getHouseById(@PathVariable Integer id){
        return  ResponseEntity.ok(houseService.getHouseById(id));
    }

}
