package com.lacorp.backend.controller;

import com.lacorp.backend.service.RoomService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController("/room")
@Data
public class RoomController {
    @Autowired
    private RoomService roomService;
}
