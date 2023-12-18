package com.lacorp.backend.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.lacorp.backend.model.Light;
import com.lacorp.backend.model.Room;
import com.lacorp.backend.model.User;
import com.lacorp.backend.repository.LightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LightService {
    @Autowired
    private HueService hueService;
    @Autowired
    private LightRepository lightRepository;

    public Light save(Room room, String name) {
        Light newLight = new Light();
        newLight.setName(name);
        newLight.setRoom(room);
        return lightRepository.save(newLight);
    }

    public List<Light> findByRoom(Room room) {
        return lightRepository.findByRoom(room);
    }

    public Light findById(Integer lightId) {
        if (lightRepository.findById(lightId).isPresent()) {
            return lightRepository.findById(lightId).get();
        } else {
            return null;
        }
    }

    public Light updateLightName(Integer lightId, String name) {
        if (lightRepository.findById(lightId).isPresent()) {
            Light light = lightRepository.findById(lightId).get();
            light.setName(name);
            return lightRepository.save(light);
        } else {
            return null;
        }
    }

    public void TurnOn(Light light, User user) throws JsonProcessingException {
        String constructorName = light.getConstructor_name();
        switch (constructorName) {
            case "Hue":
                hueService.turnOnLight(light, user);
                light.setOn(true);
                lightRepository.save(light);
            default:
        }
    }

    public void turnOff(Light light, User user) throws JsonProcessingException {
        String constructorName = light.getConstructor_name();
        switch (constructorName) {
            case "Hue":
                hueService.turnOffLight(light, user);
                light.setOn(false);
                lightRepository.save(light);
            default:
        }
    }

    public Light saveHue(String lightId, User user, Room room) throws JsonProcessingException {
        JsonNode jsonLight = hueService.getLights(lightId, user);

        Light light = new Light();
        light.setConstructor_id(lightId);
        light.setConstructor_name("Hue");
        light.setName(jsonLight.get("name").asText());
        light.setRoom(room);

        return lightRepository.save(light);
    }

}
