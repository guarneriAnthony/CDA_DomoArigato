package com.lacorp.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.lacorp.backend.model.House;
import com.lacorp.backend.model.Light;
import com.lacorp.backend.model.Room;
import com.lacorp.backend.model.User;
import com.lacorp.backend.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private LightService lightService;
    @Autowired
    private HueService hueService;

    public Room save(House house, String name) {
        Room newRoom = new Room();
        newRoom.setName(name);
        newRoom.setHouse(house);
        return roomRepository.save(newRoom);
    }

    public List<Room> findByHouse(House house) {
        return roomRepository.findByHouse(house);
    }

    public Room findById(Integer roomId) {
        return roomRepository.findById(roomId).get();
    }

    public Room updateRoomName(Integer roomId, String name) {
        if (roomRepository.findById(roomId).isPresent()) {
            Room room = roomRepository.findById(roomId).get();
            room.setName(name);
            return roomRepository.save(room);
        }else {
            return null;
        }
    }
    public void updateRoomTurnedAllOn(Room room, User user) {
        room.setAllOn(true);
        room.getLights().forEach(light -> {
            try {
                lightService.updateTurnOn(light, user);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        roomRepository.save(room);
    }

    public void updateRoomTurnedAllOff(Room room, User user){
        room.setAllOn(false);
        room.getLights().forEach(light -> {
            try {
                lightService.updateTurnOff(light, user);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        roomRepository.save(room);
    }

    public void updateRoomTurnedAnyOn(Room room) {
        room.getLights().forEach(light -> {
            if (light.isOn()){
                room.setAnyOn(true);
            }
        });
        roomRepository.save(room);
    }

    public void deleteRoom(Room room){
        roomRepository.delete(room);
    }











    public void saveRoomsWithHueAccount(User user) throws JsonProcessingException {
        JsonNode rooms = hueService.getRooms(user);
        rooms.forEach(room -> {
            String name = room.get("name").asText();
            String type = room.get("type").asText();
            if (type.equals("Room")) {

                Room roomModel = new Room();
                roomModel.setName(name);
                List<Light> lights = new ArrayList<>();

                room.get("lights").forEach(light -> {
                    String lightId = light.asText();
                    try {
                        lights.add(lightService.saveHue(lightId, user));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }

                });
                roomModel.setLights(lights);
                roomRepository.save(roomModel);
            }
        });
    }

    public Room getRoomById(Integer id) {
        if (roomRepository.findById(id).isPresent()) {
            return roomRepository.findById(id).get();
        }else {
            return null;
        }
    }

}
