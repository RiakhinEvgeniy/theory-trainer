package com.evgeniy.riakhin.backend.controller;

import com.evgeniy.riakhin.backend.dto.UserCreateDTO;
import com.evgeniy.riakhin.backend.dto.UserResponseDTO;
import com.evgeniy.riakhin.backend.entity.User;
import com.evgeniy.riakhin.backend.exception.UserDeleteException;
import com.evgeniy.riakhin.backend.exception.UserNotFoundById;
import com.evgeniy.riakhin.backend.exception.UserNotFoundByName;
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
        try {
            UserResponseDTO userResponseDTO = userService.findUserByName(name);
            return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);

        } catch (UserNotFoundByName ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("id/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") Long id) {
        try {
            UserResponseDTO userResponseDTO = userService.findUserById(id);
            return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
        } catch (UserNotFoundById ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateDTO userCreateDTO) {
        UserResponseDTO userResponseDTO = userService.saveUser(userCreateDTO);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserCreateDTO userCreateDTO) {
        try {
            UserResponseDTO userResponseDTO = userService.updateUser(id, userCreateDTO);
            return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
        } catch (UserNotFoundById ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id) {
        try {
            userService.deleteUser(id);
                return ResponseEntity.ok("User deleted successfully with id: " + id);
        } catch (UserDeleteException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
