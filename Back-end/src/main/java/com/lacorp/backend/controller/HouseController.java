package com.lacorp.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lacorp.backend.mapper.HouseMapper;
import com.lacorp.backend.mapper.LightMapper;
import com.lacorp.backend.mapper.RoomMapper;
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
    private final HouseMapper houseMapper = HouseMapper.INSTANCE;
    private final RoomMapper roomMapper = RoomMapper.INSTANCE;
    private final LightMapper lightMapper = LightMapper.INSTANCE;

    @Autowired
    private HouseService houseService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private LightService lightService;

    @PostMapping
    public ResponseEntity<List<HouseOutputDTO>> saveHouse(Authentication authentication, @RequestBody HouseInputDTO house) {
        User user = (User) authentication.getPrincipal();
        user.addHouse(houseService.save(user, house.name()));
        return ResponseEntity.ok(houseMapper.housesToHouseOutputDTOs(user.getHouses()));
    }

    @GetMapping("/{house_id}")
    public ResponseEntity<HouseOutputDTO> findHouseById(@PathVariable Integer house_id) {
        return ResponseEntity.ok(houseMapper.houseToHouseOutputDTO(houseService.findById(house_id)));
    }

    @PutMapping("/{house_id}")
    public ResponseEntity<HouseOutputDTO> updateHouseName(@PathVariable Integer house_id, @RequestBody HouseInputDTO house) {
        return ResponseEntity.ok(houseMapper.houseToHouseOutputDTO(houseService.updateHouseName(house_id, house.name())));
    }

    @PutMapping("/{house_id}/favorite")
    public ResponseEntity<List<HouseOutputDTO>> updateHouseFavorite(@PathVariable Integer house_id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(houseMapper.housesToHouseOutputDTOs(houseService.updateHouseFavorite(house_id, user)));
    }

    @PutMapping("/{house_id}/turn_on")
    public ResponseEntity<HouseOutputDTO> updateHouseTurnedAllOn(@PathVariable Integer house_id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(houseMapper.houseToHouseOutputDTO(houseService.turnOnAllLightsInHouse(house_id, user)));
    }

    @PutMapping("/{house_id}/turn_off")
    public ResponseEntity<HouseOutputDTO> updateHouseTurnedAllOff(@PathVariable Integer house_id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(houseMapper.houseToHouseOutputDTO(houseService.turnOffAllLightsInHouse(house_id, user)));
    }

    @PutMapping("/{house_id}/refresh/any_on")
    public ResponseEntity<HouseOutputDTO> updateHouseTurnedAnyOn(@PathVariable Integer house_id) {
        return ResponseEntity.ok(houseMapper.houseToHouseOutputDTO(houseService.refreshHouseAnyOn(house_id)));
    }

    @DeleteMapping("/{house_id}")
    public ResponseEntity<Boolean> deleteHouse(@PathVariable Integer house_id) {
        houseService.deleteById(house_id);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/{house_id}/room")
    public ResponseEntity<List<RoomOutputDTO>> saveRoom(@PathVariable Integer house_id, @RequestBody RoomInputDTO room) {
        House house = houseService.findById(house_id);
        house.addRoom(roomService.save(house, room.name()));
        return ResponseEntity.ok(roomMapper.roomToRoomsOutputDTOs(house.getRooms()));
    }

    @GetMapping("/{house_id}/rooms")
    public ResponseEntity<List<RoomOutputDTO>> FindRoomsByHouse(@PathVariable Integer house_id) {
        House house = houseService.findById(house_id);
        return ResponseEntity.ok(roomMapper.roomToRoomsOutputDTOs(house.getRooms()));
    }

    @PutMapping("{house_id}/room/refresh")
    public ResponseEntity<List<RoomOutputDTO>> refreshHueRooms(@PathVariable Integer house_id, Authentication authentication) throws JsonProcessingException {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(roomMapper.roomToRoomsOutputDTOs(roomService.saveRoomsWithHueAccount(house_id, user)));
    }

    @GetMapping("/room/{room_id}")
    public ResponseEntity<RoomOutputDTO> findRoomById(@PathVariable Integer room_id) {
        return ResponseEntity.ok(roomMapper.roomToRoomOutputDTO(roomService.findById(room_id)));
    }

    @PutMapping("/room/{room_id}")
    public ResponseEntity<RoomOutputDTO> updateRoomName(@PathVariable Integer room_id, @RequestBody RoomInputDTO room) {
        return ResponseEntity.ok(roomMapper.roomToRoomOutputDTO(roomService.updateRoomName(room_id, room.name())));
    }
    @PutMapping("/room/{room_id}/turn_on")
    public ResponseEntity<RoomOutputDTO> updateRoomTurnedAllOn(@PathVariable Integer room_id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Room room = roomService.findById(room_id);
        return ResponseEntity.ok(roomMapper.roomToRoomOutputDTO(roomService.turnOnAllLights(room, user)));
    }

    @PutMapping("/room/{room_id}/turn_off")
    public ResponseEntity<RoomOutputDTO> updateRoomTurnedAllOff(@PathVariable Integer room_id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Room room = roomService.findById(room_id);
        return ResponseEntity.ok(roomMapper.roomToRoomOutputDTO(roomService.turnOffAllLights(room, user)));
    }
    @PostMapping("/room/{room_id}/light")
    public ResponseEntity<List<LightOutputDTO>> saveLight(@PathVariable Integer room_id, @RequestBody LightInputDTO light) {
        Room room = roomService.findById(room_id);
        room.addLight(lightService.save(room, light.name()));
        return ResponseEntity.ok(lightMapper.lightToLightsOutputDTOs(room.getLights()));
    }

    @GetMapping("/room/{room_id}/lights")
    public ResponseEntity<List<LightOutputDTO>> findLightsByRoom(@PathVariable Integer room_id) {
        Room room = roomService.findById(room_id);
        return ResponseEntity.ok(lightMapper.lightToLightsOutputDTOs(room.getLights()));
    }

    @GetMapping("/room/light/{light_id}")
    public ResponseEntity<LightOutputDTO> findLightById(@PathVariable Integer light_id) {
        return ResponseEntity.ok(lightMapper.lightToLightOutputDTO(lightService.findById(light_id)));
    }

    @PutMapping("/room/light/{light_id}")
    public ResponseEntity<LightOutputDTO> updateLightName(@PathVariable Integer light_id, @RequestBody LightInputDTO light) {
        return ResponseEntity.ok(lightMapper.lightToLightOutputDTO(lightService.updateLightName(light_id, light.name())));
    }

    @PutMapping("/room/light/{light_id}/turn_on")
    public ResponseEntity<LightOutputDTO> updateLightTurnedOn(@PathVariable Integer light_id, Authentication authentication) throws JsonProcessingException {
        User user = (User) authentication.getPrincipal();
        Light light = lightService.findById(light_id);
        return ResponseEntity.ok(lightMapper.lightToLightOutputDTO(lightService.turnOn(light, user)));
    }

    @PutMapping("/room/light/{light_id}/turn_off")
    public ResponseEntity<LightOutputDTO> updateLightTurnedOff(@PathVariable Integer light_id, Authentication authentication) throws JsonProcessingException {
        User user = (User) authentication.getPrincipal();
        Light light = lightService.findById(light_id);
        return ResponseEntity.ok(lightMapper.lightToLightOutputDTO(lightService.turnOff(light, user)));
    }
}
