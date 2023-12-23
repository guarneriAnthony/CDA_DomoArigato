package com.lacorp.backend.controller;

import com.lacorp.backend.execption.UnauthorizedException;
import com.lacorp.backend.model.User;
import com.lacorp.backend.service.HueService;
import jakarta.servlet.http.HttpServletResponse;
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
    private void callback(@RequestParam("code") String code, @RequestParam("state") String state, HttpServletResponse response) throws IOException, UnauthorizedException {
        User user = hueService.saveAccount(code, state);
        response.sendRedirect("http://localhost:4200/auth/house");
    }

    @DeleteMapping("oauth/delete_account")
    public void delete(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        hueService.delete(user);
    }
}
