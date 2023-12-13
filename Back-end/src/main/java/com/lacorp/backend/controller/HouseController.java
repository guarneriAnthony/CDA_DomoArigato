package com.lacorp.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lacorp.backend.model.*;
import com.lacorp.backend.service.HouseService;
import com.lacorp.backend.service.LightService;
import com.lacorp.backend.service.RoomService;
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
    @Autowired
    private RoomService roomService;
    @Autowired
    private LightService lightService;

    @PostMapping
    public ResponseEntity<List<House>> saveHouse(Authentication authentication, @RequestBody HouseInputDTO house){
        User user = (User) authentication.getPrincipal();
        user.addHouse(houseService.save(user, house.name()));
        return ResponseEntity.ok(user.getHouses());
    }
    @GetMapping("/{house_id}")
    public ResponseEntity<House> findHouseById(@PathVariable Integer house_id){
        return ResponseEntity.ok(houseService.findById(house_id));
    }
    @PutMapping("/{house_id}")
    public ResponseEntity<House> updateHouseName(@PathVariable Integer house_id, @RequestBody HouseInputDTO house){
        return ResponseEntity.ok(houseService.updateHouseName(house_id, house.name()));
    }
    @PutMapping("/{house_id}/favorite")
    public ResponseEntity<List<House>> updateHouseFavorite(@PathVariable Integer house_id, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(houseService.updateHouseFavorite(house_id, user));
    }
    @PutMapping("/{house_id}/turn_on")
    public ResponseEntity<House> updateHouseTurnedAllOn(@PathVariable Integer house_id, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(houseService.turnOnAllLightsInHouse(house_id, user));
    }
    @PutMapping("/{house_id}/turn_off")
    public ResponseEntity<House> updateHouseTurnedAllOff(@PathVariable Integer house_id, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(houseService.turnOffAllLightsInHouse(house_id, user));
    }
    @PutMapping("/{house_id}/refresh/any_on")
    public ResponseEntity<House> updateHouseTurnedAnyOn(@PathVariable Integer house_id){
        return ResponseEntity.ok(houseService.refreshHouseAnyOn(house_id));
    }
    @DeleteMapping("/{house_id}")
    public ResponseEntity<Boolean> deleteHouse(@PathVariable Integer house_id){
        houseService.deleteById(house_id);
        return ResponseEntity.ok(true);
    }
    @PostMapping("/{house_id}/room")
    public ResponseEntity<List<Room>> saveRoom(@PathVariable Integer house_id, @RequestBody RoomInputDTO room){
        House house = houseService.findById(house_id);
        house.addRoom(roomService.save(house, room.name()));
        return ResponseEntity.ok(house.getRooms());
    }
    @GetMapping("/{house_id}/rooms")
    public ResponseEntity<List<Room>> FindRoomsByHouse(@PathVariable Integer house_id){
        House house = houseService.findById(house_id);
        return ResponseEntity.ok(roomService.findByHouse(house));
    }
    @PutMapping("{house_id}/room/refresh")
    public ResponseEntity<List<Room>> refreshHueRooms(@PathVariable Integer house_id, Authentication authentication) throws JsonProcessingException {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(roomService.saveRoomsWithHueAccount(house_id, user));
    }
    @GetMapping("/room/{room_id}")
    public ResponseEntity<Room> findRoomById(@PathVariable Integer room_id){
        return ResponseEntity.ok(roomService.findById(room_id));
    }
    @PutMapping("/room/{room_id}")
    public ResponseEntity<Room> updateRoomName(@PathVariable Integer room_id, @RequestBody RoomInputDTO room){
        return ResponseEntity.ok(roomService.updateRoomName(room_id, room.name()));
    }
    @PostMapping("/room/{room_id}/light")
    public ResponseEntity<List<Light>> saveLight(@PathVariable Integer room_id, @RequestBody LightInputDTO light){
        Room room = roomService.findById(room_id);
        room.addLight(lightService.save(room, light.name()));
        return ResponseEntity.ok(room.getLights());
    }
    @GetMapping("/room/{room_id}/lights")
    public ResponseEntity<List<Light>> findLightsByRoom(@PathVariable Integer room_id){
        Room room = roomService.findById(room_id);
        return ResponseEntity.ok(lightService.findByRoom(room));
    }
    @GetMapping("/room/light/{light_id}")
    public ResponseEntity<Light> findLightById(@PathVariable Integer light_id) {
        return ResponseEntity.ok(lightService.findById(light_id));
    }
    @PutMapping("/room/light/{light_id}")
    public ResponseEntity<Light> updateLightName(@PathVariable Integer light_id, @RequestBody LightInputDTO light){
        return ResponseEntity.ok(lightService.updateLightName(light_id, light.name()));
    }
}
