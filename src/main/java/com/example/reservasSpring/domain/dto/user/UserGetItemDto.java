package com.example.reservasSpring.domain.dto.user;

import com.example.reservasSpring.domain.model.lasting.ERole;

import java.util.List;

public record UserGetItemDto(
        Integer id,
        String firstName,
        String lastName,
        String email,
        Boolean enable,
        List<ERole> roles
){

}
