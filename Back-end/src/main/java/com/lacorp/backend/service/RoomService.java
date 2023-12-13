package com.lacorp.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lacorp.backend.model.HueRepositoryModel;
import com.lacorp.backend.model.LightRepositoryModel;
import com.lacorp.backend.model.RoomRepositoryModel;
import com.lacorp.backend.model.UserRepositoryModel;
import com.lacorp.backend.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class RoomService {
    @Value("${api.hue.baseUrl}")
    private String baseUrl;
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
            } else if (Objects.equals(light.getConstructor_name(), "Goove")) {
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
            }
        });
        room.setTurnedOn(false);
        roomRepository.save(room);
    }


    public void updateRoomsFromHueGroups(UserRepositoryModel user) throws JsonProcessingException {
        JsonNode groupsJsonString = hueService.getGroups(user);

        for (JsonNode group : groupsJsonString) {
            String groupName = group.get("name").asText();
            String groupTypeNode = group.get("type").asText();
            if (groupTypeNode.equals("Room")) {
                RoomRepositoryModel room = new RoomRepositoryModel();
                room.setName(groupName);
                List<LightRepositoryModel> lights = new ArrayList<>();

                JsonNode lightsInRoomNode = group.get("lights");
                for (JsonNode lightIdNode : lightsInRoomNode) {
                    String lightId = lightIdNode.asText();
                    LightRepositoryModel light = lightService.saveHueLight(lightIdNode.asText(), user);
                    lights.add(light);
                }
                room.setLightRepositoryModels(lights);
                roomRepository.save(room);
            }
        }
    }



    public RoomRepositoryModel getRoomById(Integer id) {
        return roomRepository.findById(id).get();
    }
}
