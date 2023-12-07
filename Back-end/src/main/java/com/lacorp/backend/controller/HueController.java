package com.lacorp.backend.controller;

import com.lacorp.backend.model.HueRepositoryModel;
import com.lacorp.backend.service.HueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(path = "hue")
public class HueController {

    @Autowired
    private HueService hueService;

    @GetMapping("oauth/redirect")
    public String generateLink(Authentication authentication) {
        return hueService.generateLink(authentication);
    }


    @GetMapping("oauth/callback")
    private void callback(@RequestParam("code") String code,@RequestParam("state") String state) throws IOException {
        hueService.saveAccount(code, state);
    }

    @PutMapping("oauth/delete_account")
    public void delete(Authentication authentication){
        hueService.delete(authentication);
    }






    @GetMapping("get_lights")
    public String getLights(Authentication authentication) {
        return hueService.getLights(authentication);
    }





}
