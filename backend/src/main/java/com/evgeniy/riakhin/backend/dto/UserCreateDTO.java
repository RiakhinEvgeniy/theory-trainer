package com.evgeniy.riakhin.backend.dto;

import com.evgeniy.riakhin.backend.entity.Role;
import lombok.Data;

@Data
public class UserCreateDTO {
    private String name;
    private String password;
    private String email;
    private Role role;
}
