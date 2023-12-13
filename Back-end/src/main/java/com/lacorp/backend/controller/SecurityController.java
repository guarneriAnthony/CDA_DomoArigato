package com.lacorp.backend.controller;

import com.lacorp.backend.execption.AccountExistsException;
import com.lacorp.backend.execption.UnauthorizedException;
import com.lacorp.backend.mapper.UserMapper;
import com.lacorp.backend.model.*;
import com.lacorp.backend.service.HouseService;
import com.lacorp.backend.service.impl.JwtUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
public class SecurityController {

    @Autowired
    private JwtUserServiceImpl userService;
    @Autowired
    private HouseService houseService;

    private  final UserMapper userMapper = UserMapper.INSTANCE;

    @PostMapping("/register")
    public ResponseEntity<UserOutputDTO> register(@RequestBody UserInputDTO dto) throws AccountExistsException {
        User user = userService.save(dto.username(), dto.password(), dto.email());
        House house = houseService.save(user, dto.house());
        user.setHouses(List.of(house));
        UserInfoOutputDTO userInfoOutputDTO = userMapper.userToUserInfoOutputDTO(user);
        String token = userService.generateJwtForUser(user);
        return ResponseEntity.ok(new UserOutputDTO(userInfoOutputDTO, token));
    }

    @PostMapping("/authorize")
    public ResponseEntity<UserOutputDTO> authorize(@RequestBody UserInputDTO userInputDTO) throws UnauthorizedException {
        Authentication authentication = null;
        try {
            authentication = userService.authenticate(userInputDTO.username(), userInputDTO.password());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = (User) authentication.getPrincipal();
            UserInfoOutputDTO userInfoOutputDTO = userMapper.userToUserInfoOutputDTO(user);
            String token = userService.generateJwtForUser(user);
            return ResponseEntity.ok(new UserOutputDTO(userInfoOutputDTO, token));
        } catch (AuthenticationException e) {
            throw new UnauthorizedException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public ResponseEntity<List<House>> getUserHouses(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(houseService.findByUser(user));
    }

}
