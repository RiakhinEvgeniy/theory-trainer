package com.evgeniy.riakhin.backend.service;

import com.evgeniy.riakhin.backend.dto.UserCreateDTO;
import com.evgeniy.riakhin.backend.dto.UserResponseDTO;
import com.evgeniy.riakhin.backend.entity.Role;
import com.evgeniy.riakhin.backend.entity.User;
import com.evgeniy.riakhin.backend.exception.UserMapperException;
import com.evgeniy.riakhin.backend.exception.UserNotFound;
import com.evgeniy.riakhin.backend.exception.UserNotFoundById;
import com.evgeniy.riakhin.backend.exception.UserNotFoundByName;
import com.evgeniy.riakhin.backend.mapper.UserMapper;
import com.evgeniy.riakhin.backend.repository.UserRepository;
import com.evgeniy.riakhin.backend.util.NameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private User evgeniyUser;

    private User nataliaUser;

    // todo added method setUp for initialization
    @BeforeEach
    void setUp() {
        evgeniyUser = User.builder()
                .id(1L)
                .name("Evgeniy")
                .role(Role.ADMIN)
                .email("evgeniy@gmail.com").build();

        nataliaUser = User.builder()
                .id(2L)
                .name("Natalia")
                .role(Role.GUEST)
                .email("natalia@gmail.com")
                .build();
    }

    @Test
    void findAllUsersFromDataBase() {
        List<User> allUsers = List.of(evgeniyUser, nataliaUser);
        Mockito.when(userRepository.getAllUsers()).thenReturn(allUsers);
        List<User> userFromRepo = userRepository.getAllUsers();

        assertEquals(allUsers.size(), userFromRepo.size());
        Mockito.verify(userRepository, Mockito.times(1)).getAllUsers();
    }

    @Test
    void shouldReturnUserByNameFromRepositoryWhenUserExists() {
        Mockito.when(userRepository.findUserByName("Evgeniy")).thenReturn(Optional.of(evgeniyUser));
        Optional<User> userByName = userRepository.findUserByName(evgeniyUser.getName());

        assertTrue(userByName.isPresent());
        assertEquals(evgeniyUser.getName(), userByName.get().getName());
        assertEquals(evgeniyUser.getId(), userByName.get().getId());
        assertEquals(evgeniyUser.getRole(), userByName.get().getRole());

        Mockito.verify(userRepository, Mockito.times(1)).findUserByName("Evgeniy");
    }

    @Test
    void shouldReturnListOfUsersDTOWhenUserExists() {
        List<User> allUsers = List.of(evgeniyUser, nataliaUser);
        UserResponseDTO evgeniyDTO = new UserResponseDTO(
                evgeniyUser.getId(),
                evgeniyUser.getName(),
                evgeniyUser.getRole());

        UserResponseDTO nataliaDTO = new UserResponseDTO(
                nataliaUser.getId(),
                nataliaUser.getName(),
                nataliaUser.getRole());

        Mockito.when(userRepository.getAllUsers()).thenReturn(allUsers);
        Mockito.when(userMapper.toDTO(evgeniyUser)).thenReturn(evgeniyDTO);
        Mockito.when(userMapper.toDTO(nataliaUser)).thenReturn(nataliaDTO);

        List<UserResponseDTO> userResponseDTOS = userService.findAllUsers();

        assertEquals(2, userResponseDTOS.size());

        assertEquals(evgeniyDTO.id(), userResponseDTOS.getFirst().id());
        assertEquals(evgeniyDTO.name(), userResponseDTOS.getFirst().name());
        assertEquals(evgeniyDTO.role(), userResponseDTOS.getFirst().role());

        assertEquals(nataliaDTO.id(), userResponseDTOS.get(1).id());
        assertEquals(nataliaDTO.name(), userResponseDTOS.get(1).name());
        assertEquals(nataliaDTO.role(), userResponseDTOS.get(1).role());

        Mockito.verify(userRepository, Mockito.times(1)).getAllUsers();
        Mockito.verify(userMapper, Mockito.times(1)).toDTO(evgeniyUser);
        Mockito.verify(userMapper, Mockito.times(1)).toDTO(nataliaUser);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenInDBNotUsers() {
        List<User> emptyList = List.of();
        Mockito.when(userRepository.getAllUsers()).thenReturn(emptyList);
        UserNotFound userNotFound = assertThrows(UserNotFound.class, () -> userService.findAllUsers());

        assertEquals(NameException.USERS_NOT_FOUND_IN_DB, userNotFound.getMessage());
        Mockito.verify(userRepository, Mockito.times(1)).getAllUsers();
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundByName() {
        String nameNotExists = "not_exists_name";
        Mockito.when(userRepository.findUserByName(nameNotExists)).thenReturn(Optional.empty());
        UserNotFoundByName thrown = assertThrows(
                UserNotFoundByName.class,
                () -> userService.findUserByName(nameNotExists));

        assertEquals(NameException.USER_NOT_FOUND_BY_NAME + nameNotExists, thrown.getMessage());
        Mockito.verify(userRepository, Mockito.times(1)).findUserByName(nameNotExists);
    }

    @Test
    void shouldReturnUserByIdFromRepositoryWhenUserExists() {
        Mockito.when(userRepository.findUserById(1L)).thenReturn(Optional.of(evgeniyUser));
        Optional<User> userById = userRepository.findUserById(evgeniyUser.getId());

        assertTrue(userById.isPresent());
        assertEquals(evgeniyUser.getId(), userById.get().getId());
        assertEquals(evgeniyUser.getName(), userById.get().getName());
        assertEquals(evgeniyUser.getRole(), userById.get().getRole());

        Mockito.verify(userRepository, Mockito.times(1)).findUserById(evgeniyUser.getId());
    }

    @Test
    void findUserByIdFromUserService() {
        UserResponseDTO userResponseDTO = new UserResponseDTO(
                evgeniyUser.getId(),
                evgeniyUser.getName(),
                evgeniyUser.getRole());

        Mockito.when(userMapper.toDTO(evgeniyUser)).thenReturn(userResponseDTO);
        Mockito.when(userRepository.findUserById(evgeniyUser.getId())).thenReturn(Optional.of(evgeniyUser));

        UserResponseDTO userById = userService.findUserById(evgeniyUser.getId());
        assertEquals(userById.id(), evgeniyUser.getId());
        assertEquals(userById.name(), evgeniyUser.getName());
        assertEquals(userById.role(), evgeniyUser.getRole());

        Mockito.verify(userMapper, Mockito.times(1)).toDTO(evgeniyUser);
        Mockito.verify(userRepository, Mockito.times(1)).findUserById(evgeniyUser.getId());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundById() {
        long notExistingUserId = 9999L;
        Mockito.when(userRepository.findUserById(notExistingUserId)).thenReturn(Optional.empty());
        UserNotFoundById thrown = assertThrows(
                UserNotFoundById.class,
                () -> userService.findUserById(notExistingUserId));

        assertEquals(NameException.USER_NOT_FOUND_BY_ID + notExistingUserId, thrown.getMessage());
        Mockito.verify(userRepository, Mockito.times(1)).findUserById(notExistingUserId);
    }

    @Test
    void saveUser() {
        UserResponseDTO expectedResponseDTO = new UserResponseDTO(
                evgeniyUser.getId(),
                evgeniyUser.getName(),
                evgeniyUser.getRole());

        UserCreateDTO evgeniyCreateDTO = new UserCreateDTO();
        evgeniyCreateDTO.setName("Evgeniy");
        evgeniyCreateDTO.setRole(Role.ADMIN);
        evgeniyCreateDTO.setEmail("evgeniy@gmail.com");
        evgeniyCreateDTO.setPassword("1234");

        Mockito.when(userMapper.toEntity(evgeniyCreateDTO)).thenReturn(evgeniyUser);
        Mockito.when(userMapper.toDTO(Mockito.any(User.class))).thenReturn(expectedResponseDTO);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(evgeniyUser);

        UserResponseDTO actualResponseDTO = userService.saveUser(evgeniyCreateDTO);
        assertEquals(actualResponseDTO, expectedResponseDTO);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository).save(userCaptor.capture());

        assertEquals("Evgeniy", userCaptor.getValue().getName());
        assertEquals(Role.ADMIN, userCaptor.getValue().getRole());
        assertEquals("evgeniy@gmail.com", userCaptor.getValue().getEmail());
    }

    @Test
    void saveUserWhenSaveThrowsExceptionThenThrowsSameException() {
        UserCreateDTO evgeniyCreateDTO = new UserCreateDTO();
        evgeniyCreateDTO.setName("Evgeniy");
        evgeniyCreateDTO.setRole(Role.ADMIN);
        evgeniyCreateDTO.setEmail("evgeniy@gmail.com");
        evgeniyCreateDTO.setPassword("1234");

        Mockito.when(userMapper.toEntity(evgeniyCreateDTO)).thenReturn(evgeniyUser);

        Mockito.when(userRepository.save(Mockito.any(User.class)))
                .thenThrow(new UserMapperException(NameException.USER_MAPPING_FAILED));

        UserMapperException userMapperException = assertThrows(UserMapperException.class,
                () -> userService.saveUser(evgeniyCreateDTO));

        assertEquals(NameException.USER_MAPPING_FAILED, userMapperException.getMessage());
        Mockito.verify(userMapper, Mockito.never()).toDTO(Mockito.any());
    }

    @Test
    void updateUser() {
        long userId = evgeniyUser.getId();

        UserCreateDTO newCreateDTO = new UserCreateDTO();
        newCreateDTO.setName("Natalia");
        newCreateDTO.setRole(Role.ADMIN);
        newCreateDTO.setEmail("natalia@gmail.com");
        newCreateDTO.setPassword("4321");

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setName(newCreateDTO.getName());
        updatedUser.setEmail(newCreateDTO.getEmail());
        updatedUser.setRole(newCreateDTO.getRole());
        updatedUser.setPassword(newCreateDTO.getPassword());

        UserResponseDTO userResponseDTO = new UserResponseDTO(
                updatedUser.getId(),
                updatedUser.getName(),
                updatedUser.getRole());

        Mockito.when(userRepository.findUserById(evgeniyUser.getId())).thenReturn(Optional.of(evgeniyUser));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(updatedUser);
        Mockito.when(userMapper.toDTO(evgeniyUser)).thenReturn(userResponseDTO);

        UserResponseDTO actualResponseDTO = userService.updateUser(userId, newCreateDTO);

        assertEquals(actualResponseDTO, userResponseDTO);
        assertEquals("Natalia", actualResponseDTO.name());
        assertEquals(Role.ADMIN, actualResponseDTO.role());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository).save(userCaptor.capture());

        assertEquals("Natalia", userCaptor.getValue().getName());
        assertEquals(Role.ADMIN, userCaptor.getValue().getRole());
        assertEquals("natalia@gmail.com", userCaptor.getValue().getEmail());
        assertEquals("4321", userCaptor.getValue().getPassword());
    }

    @Test
    void deleteUser() {
    }
}