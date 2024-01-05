package com.example.reservasSpring.domain.dto.employee;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record EmployeeCreateDTO(
    Integer id,
    String firstName,
    String lastName,
    String email,
    String password,
    Integer idJob,
    Boolean enable

    ) {
}
