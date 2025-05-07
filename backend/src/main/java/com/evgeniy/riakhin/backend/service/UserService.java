package com.evgeniy.riakhin.backend.service;

import com.evgeniy.riakhin.backend.dto.UserCreateDTO;
import com.evgeniy.riakhin.backend.dto.UserResponseDTO;
import com.evgeniy.riakhin.backend.entity.User;
import com.evgeniy.riakhin.backend.exception.UserNotFoundByName;
import com.evgeniy.riakhin.backend.mapper.UserMapper;
import com.evgeniy.riakhin.backend.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Data
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.getAllUsers();
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findUserByName(String name) {
        User user = userRepository.findByUserName(name)
                .orElseThrow(() -> new UserNotFoundByName("User not found by name" + name));
        System.out.println(user.getName());
        return userMapper.toDTO(user);
    }

    @Transactional
    public UserResponseDTO saveUser(UserCreateDTO userCreateDTO) {
        User user = userMapper.toEntity(userCreateDTO);
        userRepository.save(user);
        return userMapper.toDTO(user);
    }
}
