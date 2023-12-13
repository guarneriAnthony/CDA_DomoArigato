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
        return lightRepository.findById(lightId).get();
    }

    public Light updateLightName(Integer lightId, String name) {
        if (lightRepository.findById(lightId).isPresent()) {
            Light light = lightRepository.findById(lightId).get();
            light.setName(name);
            return lightRepository.save(light);
        }else {
            return null;
        }
    }

    public void updateTurnOn(Light light, User user) throws JsonProcessingException {
        String constructorName = light.getConstructor_name();
        switch (constructorName){
            case "hue":
                hueService.turnOnLight(light, user);
                light.setOn(true);
                lightRepository.save(light);
            default:
        }
    }

    public void updateTurnOff(Light light, User user) throws JsonProcessingException {
        String constructorName = light.getConstructor_name();
        switch (constructorName){
            case "hue":
                hueService.turnOffLight(light, user);
                light.setOn(false);
                lightRepository.save(light);
            default:
        }
    }

    public Light saveHue(String lightId, User user) throws JsonProcessingException {
        JsonNode jsonLight = hueService.getLights(lightId, user);

        Light light = new Light();
        light.setConstructor_id(lightId);
        light.setConstructor_name("Hue");
        light.setName(jsonLight.get("name").asText());

        return lightRepository.save(light);
    }

}
