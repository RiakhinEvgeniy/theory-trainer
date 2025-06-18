package com.evgeniy.riakhin.backend.service;

import com.evgeniy.riakhin.backend.dto.UserCreateDTO;
import com.evgeniy.riakhin.backend.dto.UserResponseDTO;
import com.evgeniy.riakhin.backend.entity.User;
import com.evgeniy.riakhin.backend.exception.*;
import com.evgeniy.riakhin.backend.mapper.UserMapper;
import com.evgeniy.riakhin.backend.repository.UserRepository;
import com.evgeniy.riakhin.backend.util.NameException;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAllUsers() {
        List<User> allUsers = userRepository.getAllUsers();
        if (allUsers.isEmpty()) {
            throw new UserNotFound(NameException.USERS_NOT_FOUND_IN_DB);
        }
        List<UserResponseDTO> userResponseDTOS = new ArrayList<>(allUsers.size());
        for (User allUser : allUsers) {
            userResponseDTOS.add(userMapper.toDTO(allUser));
        }
        return userResponseDTOS;
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findUserByName(String name) {
        User user = userRepository.findUserByName(name)
                .orElseThrow(() -> new UserNotFoundByName(NameException.USER_NOT_FOUND_BY_NAME + name));
        System.out.println(user.getName());
        return userMapper.toDTO(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findUserById(Long id) {
        User user = userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundById(NameException.USER_NOT_FOUND_BY_ID + id));
        return userMapper.toDTO(user);
    }

    @Transactional
    public UserResponseDTO saveUser(UserCreateDTO userCreateDTO) {
        if (userCreateDTO == null) {
            throw new UserCreateDTOException(NameException.USER_CREATE_DTO);
        }

        User user = userMapper.toEntity(userCreateDTO);

        if (user == null) {
            throw new UserMapperException(NameException.USER_MAPPING_FAILED);
        }

        User saveUser = userRepository.save(user);
        UserResponseDTO userResponseDTO = userMapper.toDTO(saveUser);

        if (userResponseDTO == null) {
            throw new UserMapperException(NameException.FAILED_TO_MAP_SAVED_USER_TO_DTO);
        }

        return userResponseDTO;
    }

    @Transactional
    public UserResponseDTO updateUser(Long id, UserCreateDTO userCreateDTO) {
        User user = userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundById(NameException.USER_NOT_FOUND_FOR_UPDATING + id));
        updateFieldsOfUser(user, userCreateDTO);
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Transactional(timeout = 3, rollbackFor = UserDeleteException.class)
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserDeleteException(NameException.USER_NOT_FOUND_FOR_DELETING + id);
        }
        userRepository.deleteById(id);
    }

    private void updateFieldsOfUser(User user, UserCreateDTO userCreateDTO) {

        if (userCreateDTO.getName() != null
                && !userCreateDTO.getName().isEmpty()
                && !Objects.equals(userCreateDTO.getName(), user.getName())) {
            user.setName(userCreateDTO.getName());
        }

        if (userCreateDTO.getEmail() != null
                && !userCreateDTO.getEmail().isEmpty()
                && !Objects.equals(userCreateDTO.getEmail(), user.getEmail())) {
            user.setEmail(userCreateDTO.getEmail());
        }

        if (userCreateDTO.getPassword() != null
                && !userCreateDTO.getPassword().isEmpty()
                && !Objects.equals(userCreateDTO.getPassword(), user.getPassword())) {
            user.setPassword(userCreateDTO.getPassword());
        }

        if (userCreateDTO.getRole() != null && !Objects.equals(userCreateDTO.getRole(), user.getRole())) {
            user.setRole(userCreateDTO.getRole());
        }
    }
}
