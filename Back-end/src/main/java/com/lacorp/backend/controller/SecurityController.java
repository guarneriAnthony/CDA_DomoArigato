package com.lacorp.backend.controller;

import com.lacorp.backend.execption.AccountExistsException;
import com.lacorp.backend.execption.UnauthorizedException;
import com.lacorp.backend.mapper.UserMapper;
import com.lacorp.backend.model.*;
import com.lacorp.backend.service.impl.JwtUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class SecurityController {

    @Autowired
    private JwtUserServiceImpl userService;

    private  final UserMapper userMapper = UserMapper.INSTANCE;

    @PostMapping("/register")
    public ResponseEntity<UserOutputDTO> register(@RequestBody UserInputDTO dto) throws AccountExistsException {
        UserRepositoryModel user = userService.save(dto.username(), dto.password(), dto.email());
        UserInfoOutputDTO userInfoOutputDTO = userMapper.userDetailsToUserInfoOutputDTO(user);
        String token = userService.generateJwtForUser(user);
        return ResponseEntity.ok(new UserOutputDTO(userInfoOutputDTO, token));
    }

    @PostMapping("/authorize")
    public ResponseEntity<UserOutputDTO> authorize(@RequestBody UserInputDTO userInputDTO) throws UnauthorizedException {
        Authentication authentication = null;
        try {
            authentication = userService.authenticate(userInputDTO.username(), userInputDTO.password());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserRepositoryModel user = (UserRepositoryModel) authentication.getPrincipal();
            UserInfoOutputDTO userInfoOutputDTO = userMapper.userDetailsToUserInfoOutputDTO(user);
            String token = userService.generateJwtForUser(user);
            return ResponseEntity.ok(new UserOutputDTO(userInfoOutputDTO, token));
        } catch (AuthenticationException e) {
            throw new UnauthorizedException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    @GetMapping("/show_account")
//        public ResponseEntity<UserOutputDTO> readAccountDomoarigato(Authentication authentication) {
//        UserDetails user = (UserDetails) authentication.getPrincipal();
//        return ResponseEntity.ok(new UserOutputDTO(user, null ));
//    }

}
