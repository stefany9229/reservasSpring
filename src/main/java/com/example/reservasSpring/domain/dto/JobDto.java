package com.example.reservasSpring.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record JobDto(
        Integer id,
        String name
) {
}
