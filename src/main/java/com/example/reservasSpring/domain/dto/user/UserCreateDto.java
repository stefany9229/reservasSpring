package com.example.reservasSpring.domain.dto.user;


import com.example.reservasSpring.domain.model.lasting.ERole;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserCreateDto(
        Integer id,
        String firstName,
        String lastName,
        String email,
        String password,
        Boolean enable,
        List<ERole> roles
) {
}
