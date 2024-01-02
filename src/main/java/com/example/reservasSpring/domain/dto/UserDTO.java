package com.example.reservasSpring.domain.dto;


import com.example.reservasSpring.domain.model.ERole;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDTO(
        Integer id,
        String firstName,
        String lastName,
        String email,
        String password,
        Boolean enable,
        List<ERole> role
) {
}
