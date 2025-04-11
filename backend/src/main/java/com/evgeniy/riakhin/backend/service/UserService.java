package com.evgeniy.riakhin.backend.service;

import com.evgeniy.riakhin.backend.entity.User;
import com.evgeniy.riakhin.backend.repository.UserRepository;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Data
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public List<User> findAllUsers() {
        return userRepository.getAllUsers();
    }

    @Transactional
    public User findUserByName(String name) {
        User userByName = userRepository.findByUserName(name);
        if (userByName == null) {
            throw new RuntimeException("User not found");
        }
        return userByName;
    }
}
