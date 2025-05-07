package com.evgeniy.riakhin.backend.dto;

import com.evgeniy.riakhin.backend.entity.Role;

public record UserResponseDTO(
        Long id,
        String name,
        Role role) {
}
