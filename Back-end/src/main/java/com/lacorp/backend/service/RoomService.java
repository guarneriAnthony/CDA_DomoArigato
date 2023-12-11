package com.lacorp.backend.service;

import com.lacorp.backend.model.RoomRepositoryModel;
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


    public void turnOnAllLights(RoomRepositoryModel room, Authentication authentication) {
        room.getLightRepositoryModels().forEach(light -> light.setTurnedOn(true));
        room.getLightRepositoryModels().forEach(light  -> {
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
    public void turnOffAllLights(RoomRepositoryModel room, Authentication authentication) {
        room.getLightRepositoryModels().forEach(light -> light.setTurnedOn(false));
        room.getLightRepositoryModels().forEach(light  -> {
            if (Objects.equals(light.getConstructor_name(), "Hue")) {
                lightService.turnOffLight(light, authentication);
            } else if (Objects.equals(light.getConstructor_name(), "Goove")) {
                //GooveService.turnOnLight(light);
            }
        });
        room.setTurnedOn(false);
        roomRepository.save(room);
    }



    public RoomRepositoryModel getRoomById(Integer id) {
        return roomRepository.findById(id).get();
    }
}
