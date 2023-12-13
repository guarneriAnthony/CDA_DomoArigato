package com.lacorp.backend.service;

import com.lacorp.backend.model.House;
import com.lacorp.backend.model.User;
import com.lacorp.backend.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class HouseService {
    @Autowired
    private HouseRepository houseRepository;

    // Changer les return null par des potentiels erreurs
    public House createHouse(User user, String house) {
        House newHouse = new House();
        newHouse.setName(house);
        newHouse.setUser(user);
        return houseRepository.save(newHouse);
    }

    public House getHouseById(Integer id) {
        if (houseRepository.findById(id).isPresent()) {
            return houseRepository.findById(id).get();
        }else {
            return null;
        }
    }

    // à voir si j'ai passs déjà l'ID de la maison
    public House updateHouse(Integer id, House house) {
        if (houseRepository.findById(id).isPresent()) {
            house.setId(id);
            return houseRepository.save(house);
        }else {
            return null;
        }
    }
    public void deleteHouse(House house) {
        houseRepository.delete(house);
    }

}
