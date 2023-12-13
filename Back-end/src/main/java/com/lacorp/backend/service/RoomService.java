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
    public void turnOnAllLights(Room room, User user) {
        room.getLights().forEach(light -> {
            try {
                lightService.TurnOn(light, user);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        room.setAllOn(room.getLights().stream().allMatch(Light::isOn));
        roomRepository.save(room);
    }

    public void turnOffAllLights(Room room, User user){
        room.setAllOn(false);
        room.getLights().forEach(light -> {
            try {
                lightService.turnOff(light, user);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        roomRepository.save(room);
    }

    public void refreshRoomAnyOn(Room room) {
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

    public List<Room> saveRoomsWithHueAccount(Integer houseId, User user) throws JsonProcessingException {
        House house = user.getHouses().get(houseId);

        JsonNode rooms = hueService.getRooms(user);
        rooms.forEach(room -> {
            String name = room.get("name").asText();
            String type = room.get("type").asText();
            if (type.equals("Room")) {

                Room roomModel = new Room();
                roomModel.setName(name);
                roomModel.setHouse(house);

                // comprends pas pourquoi mais je dois faire comme ça
                Room finalRoomModel = roomRepository.save(roomModel);

                room.get("lights").forEach(light -> {
                    String lightId = light.asText();
                    try {
                        lightService.saveHue(lightId, user, finalRoomModel);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        });
        return roomRepository.findByHouse(house);
    }

    public Room getRoomById(Integer id) {
        if (roomRepository.findById(id).isPresent()) {
            return roomRepository.findById(id).get();
        }else {
            return null;
        }
    }

}
