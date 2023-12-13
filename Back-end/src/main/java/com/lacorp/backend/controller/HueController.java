package com.lacorp.backend.controller;

import com.lacorp.backend.execption.UnauthorizedException;
import com.lacorp.backend.model.User;
import com.lacorp.backend.service.HueService;
import com.lacorp.backend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(path = "hue")
public class HueController {

    @Autowired
    private HueService hueService;
    @Autowired
    private RoomService roomService;

    @GetMapping("oauth/redirect")
    public String generateLink(Authentication authentication) {
        return hueService.generateLink(authentication);
    }

    @GetMapping("oauth/callback")
    private void callback(@RequestParam("code") String code,@RequestParam("state") String state) throws IOException, UnauthorizedException {
        User user =hueService.saveAccount(code, state);
        roomService.saveRoomsWithHueAccount(user);
    }

    @DeleteMapping("oauth/delete_account")
    public void delete(Authentication authentication){
        hueService.delete(authentication);
    }
}
