package com.example.reservasSpring.domain.dto.employee;

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
