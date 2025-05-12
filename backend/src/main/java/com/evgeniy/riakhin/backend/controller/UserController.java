package com.evgeniy.riakhin.backend.controller;

import com.evgeniy.riakhin.backend.dto.UserCreateDTO;
import com.evgeniy.riakhin.backend.dto.UserResponseDTO;
import com.evgeniy.riakhin.backend.entity.User;
import com.evgeniy.riakhin.backend.repository.UserRepository;
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
    private final UserRepository userRepository;

    @GetMapping
    public List<User> getUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<UserResponseDTO> getUserByName(@PathVariable("name") String name) {
        UserResponseDTO userResponseDTO = userService.findUserByName(name);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") Long id) {
        UserResponseDTO userResponseDTO = userService.findUserById(id);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateDTO userCreateDTO) {
        UserResponseDTO userResponseDTO = userService.saveUser(userCreateDTO);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserCreateDTO userCreateDTO) {
        UserResponseDTO userResponseDTO = userService.updateUser(id, userCreateDTO);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully with id: " + id);
    }
}
