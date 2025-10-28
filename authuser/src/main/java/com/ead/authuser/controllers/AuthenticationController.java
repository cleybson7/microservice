package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/auth")
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<Object> registerUser(@RequestBody
                                                   @Validated(UserDto.UserView.RegistrationPost.class)
                                                   @JsonView(UserDto.UserView.RegistrationPost.class)
                                                   UserDto userDto){
        if (userService.existsByUsername(userDto.getUsername())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: username is already taken!");
        }
        if (userService.existsByEmail(userDto.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: email is already taken!");
        }
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);

        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setUserType(UserType.STUDENT);
        userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        userService.save(userModel);

        var userCreatedDto = new UserDto();
        BeanUtils.copyProperties(userModel, userCreatedDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(userCreatedDto);
    }
}
