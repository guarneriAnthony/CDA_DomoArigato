package com.lacorp.backend.controller;

import com.lacorp.backend.model.RoomRepositoryModel;
import com.lacorp.backend.model.UserRepositoryModel;
import com.lacorp.backend.service.RoomService;
import lombok.Data;
import org.aspectj.lang.annotation.AfterThrowing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    private RoomService roomService;


    @PutMapping("/{roomId}/lights/on")
    public ResponseEntity<RoomRepositoryModel> turnOnAllLights(@PathVariable Integer roomId, Authentication authentication) {
        RoomRepositoryModel room = roomService.getRoomById(roomId);
        if (room != null){
            roomService.turnOnAllLights(room, (Authentication) authentication);
            return ResponseEntity.ok(room);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{roomId}/lights/off")
    public ResponseEntity<RoomRepositoryModel> turnOffAllLights(@PathVariable Integer roomId, Authentication authentication) {
        RoomRepositoryModel room = roomService.getRoomById(roomId);
        if (room != null){
            roomService.turnOffAllLights(room, (Authentication) authentication);
            return ResponseEntity.ok(room);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @GetMapping("/{id}")
    public ResponseEntity<RoomRepositoryModel> getRoomById(@PathVariable Integer id){
        return  ResponseEntity.ok(roomService.getRoomById(id));
    }
}
