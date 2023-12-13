package com.lacorp.backend.service;

import com.lacorp.backend.model.House;
import com.lacorp.backend.model.Room;
import com.lacorp.backend.model.User;
import com.lacorp.backend.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class HouseService {
    @Autowired
    private HouseRepository houseRepository;
    @Autowired
    private RoomService roomService;

    public House save(User user, String house) {
        House newHouse = new House();
        newHouse.setName(house);
        newHouse.setUser(user);
        return houseRepository.save(newHouse);
    }

    public List<House> findByUser(User user) {
        return houseRepository.findByUser(user);
    }

    public House findById(Integer id) {
        return houseRepository.findById(id).get();
    }

     public House updateHouseName(Integer id, String name) {
        if (houseRepository.findById(id).isPresent()) {
            House house = houseRepository.findById(id).get();
            house.setName(name);
            return houseRepository.save(house);
        }else {
            return null;
        }
    }

    public List<House> updateHouseFavorite(Integer id, User user) {
        houseRepository.findByUser(user).forEach(house -> {
            house.setFavorite(Objects.equals(house.getId(), id));
            houseRepository.save(house);
        });
        return houseRepository.findByUser(user);
    }

    public House turnOnAllLightsInHouse(Integer id, User user) {
        if (houseRepository.findById(id).isPresent()) {
            House house = houseRepository.findById(id).get();
            house.getRooms().forEach(room -> {
                roomService.turnOnAllLights(room, user);
            });
            house.setAllOn(house.getRooms().stream().allMatch(Room::isAllOn));
            return houseRepository.save(house);
        }else {
            return null;
        }
    }

    public House turnOffAllLightsInHouse(Integer id, User user) {
        if (houseRepository.findById(id).isPresent()) {
            House house = houseRepository.findById(id).get();
            house.getRooms().forEach(room -> {
                roomService.turnOffAllLights(room, user);
            });
            house.setAllOn(house.getRooms().stream().allMatch(Room::isAllOn));
            return houseRepository.save(house);
        }else {
            return null;
        }
    }

    public House refreshHouseAnyOn(Integer id) {
        if (houseRepository.findById(id).isPresent()) {
            House house = houseRepository.findById(id).get();
            house.getRooms().forEach(room -> {
                roomService.refreshRoomAnyOn(room);
                if (room.isAnyOn()) {
                    house.setAnyOn(true);
                }
            });
            return houseRepository.save(house);
        }else {
            return null;
        }
    }

    public void deleteById(Integer id) {
        houseRepository.deleteById(id);
    }

}
