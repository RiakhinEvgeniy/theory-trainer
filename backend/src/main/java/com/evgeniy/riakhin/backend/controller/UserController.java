package com.evgeniy.riakhin.backend.controller;

import com.evgeniy.riakhin.backend.dto.UserCreateDTO;
import com.evgeniy.riakhin.backend.dto.UserResponseDTO;
import com.evgeniy.riakhin.backend.entity.User;
import com.evgeniy.riakhin.backend.service.UserService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{name}")
    public ResponseEntity<UserResponseDTO> getUserByName(@PathVariable("name") String name) {
        UserResponseDTO userResponseDTO = userService.findUserByName(name);
        if (userResponseDTO != null) {
            System.out.println(userResponseDTO.name());
            return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateDTO userCreateDTO) {
        UserResponseDTO userResponseDTO = userService.saveUser(userCreateDTO);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }
}
