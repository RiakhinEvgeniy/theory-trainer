package com.evgeniy.riakhin.backend.controller;

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
    public ResponseEntity<User> getUserByName(@PathVariable("name") String name) {
        User user = userService.findUserByName(name);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
