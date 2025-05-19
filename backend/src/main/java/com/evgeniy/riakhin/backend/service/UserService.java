package com.evgeniy.riakhin.backend.service;

import com.evgeniy.riakhin.backend.dto.UserCreateDTO;
import com.evgeniy.riakhin.backend.dto.UserResponseDTO;
import com.evgeniy.riakhin.backend.entity.User;
import com.evgeniy.riakhin.backend.exception.UserDeleteException;
import com.evgeniy.riakhin.backend.exception.UserNotFoundById;
import com.evgeniy.riakhin.backend.exception.UserNotFoundByName;
import com.evgeniy.riakhin.backend.mapper.UserMapper;
import com.evgeniy.riakhin.backend.repository.UserRepository;
import com.evgeniy.riakhin.backend.util.NameException;
import lombok.Data;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ConcurrentModificationException;
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
        User user = userMapper.toEntity(userCreateDTO);
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Transactional
    public UserResponseDTO updateUser(Long id, UserCreateDTO userCreateDTO) {
        try {
            User user = userRepository.findUserById(id)
                    .orElseThrow(() -> new UserNotFoundById(NameException.USER_NOT_FOUND_FOR_UPDATING + id));
            updateFieldsOfUser(user, userCreateDTO);
            userRepository.save(user);
            return userMapper.toDTO(user);
        } catch (ObjectOptimisticLockingFailureException ex) {
            throw new ConcurrentModificationException(NameException.USER_MODIFIED_BY_ANOTHER_THREAD + ex);
        }
    }

    @Transactional(timeout = 3, rollbackFor = UserDeleteException.class)
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserDeleteException(NameException.USER_NOT_FOUND_FOR_DELETING + id);
        }
        userRepository.deleteById(id);
    }

    private void updateFieldsOfUser(User user, UserCreateDTO userCreateDTO) {
        if (!userCreateDTO.getName().equals(user.getName())) {
            user.setName(userCreateDTO.getName());
        }
        if (!userCreateDTO.getPassword().equals(user.getPassword())) {
            user.setPassword(userCreateDTO.getPassword());
        }
        if (!userCreateDTO.getEmail().equals(user.getEmail())) {
            user.setEmail(userCreateDTO.getEmail());
        }
        if (!userCreateDTO.getRole().toString().equals(user.getRole().toString())) {
            user.setRole(userCreateDTO.getRole());
        }
    }
}
