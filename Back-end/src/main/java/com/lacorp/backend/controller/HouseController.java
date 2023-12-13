package com.lacorp.backend.controller;

import com.lacorp.backend.model.House;
import com.lacorp.backend.model.HouseInputDTO;
import com.lacorp.backend.model.User;
import com.lacorp.backend.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/house")
public class HouseController {
    @Autowired
    private HouseService houseService;

    @PostMapping("/add")
    public ResponseEntity<List<House>> createHouse(Authentication authentication, @RequestBody HouseInputDTO house){
        User user = (User) authentication.getPrincipal();
        House houses = houseService.createHouse(user, house.name());
        user.addHouse(houses);
        return ResponseEntity.ok(user.getHouses());
    }

    // un lien style user/houses ?
    @GetMapping("/houses")
    public ResponseEntity<List<House>> getUserHouses(Authentication authentication){
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(user.getHouses());
    }

    

    // ici user/house/{id} ?
    @GetMapping("/{id}")
    public ResponseEntity<House> getHouseById(@PathVariable Integer id){
        return ResponseEntity.ok(houseService.getHouseById(id));
    }

}
