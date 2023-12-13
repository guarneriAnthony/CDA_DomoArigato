package com.lacorp.backend.service;

import com.lacorp.backend.model.Room;
import com.lacorp.backend.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private LightService lightService;
    @Autowired
    private HueService hueService;


    public void turnOnAllLights(Room room, Authentication authentication) {
        room.getLights().forEach(light -> light.setTurnedOn(true));
        room.getLights().forEach(light  -> {
            if (Objects.equals(light.getConstructor_name(), "Hue")) {
               lightService.turnOnLight(light, authentication);
                System.out.println("ici" +light);
            } else if (Objects.equals(light.getConstructor_name(), "Goove")) {
                //GooveService.turnOnLight(light);
            }
        });
        room.setTurnedOn(true);
        roomRepository.save(room);
    }
    public void turnOffAllLights(Room room, Authentication authentication) {
        room.getLights().forEach(light -> light.setTurnedOn(false));
        room.getLights().forEach(light  -> {
            if (Objects.equals(light.getConstructor_name(), "Hue")) {
                lightService.turnOffLight(light, authentication);
            } else if (Objects.equals(light.getConstructor_name(), "Goove")) {
                //GooveService.turnOnLight(light);
            }
        });
        room.setTurnedOn(false);
        roomRepository.save(room);
    }



    public Room getRoomById(Integer id) {
        return roomRepository.findById(id).get();
    }
}
