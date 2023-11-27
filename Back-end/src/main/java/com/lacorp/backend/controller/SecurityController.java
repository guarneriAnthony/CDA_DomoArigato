package com.lacorp.backend.controller;

import com.lacorp.backend.execption.AccountExistsException;
import com.lacorp.backend.execption.UnauthorizedException;
import com.lacorp.backend.model.UserInputDTO;
import com.lacorp.backend.model.UserOutputDTO;
import com.lacorp.backend.service.JwtUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @Autowired
    private JwtUserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserOutputDTO> register(@RequestBody UserInputDTO dto) throws AccountExistsException {
        UserDetails user = userService.save(dto.username(), dto.password(), dto.email());
        String token = userService.generateJwtForUser(user);
        return ResponseEntity.ok(new UserOutputDTO(user, token));
    }
    @PostMapping("/authorize")
    public ResponseEntity<UserOutputDTO> authorize(@RequestBody UserInputDTO userInputDTO) throws UnauthorizedException {
        Authentication authentication = null;
        try {
            authentication = userService.authenticate(userInputDTO.username(), userInputDTO.password());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails user = (UserDetails) authentication.getPrincipal();
            String token = userService.generateJwtForUser(user);
            return ResponseEntity.ok(new UserOutputDTO(user, token));
        } catch (AuthenticationException e) {
            throw new UnauthorizedException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
