package com.example.reservasSpring.domain.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record AuthRegisterDTO(
        UserGetItemDto user,
        String token
){
}
