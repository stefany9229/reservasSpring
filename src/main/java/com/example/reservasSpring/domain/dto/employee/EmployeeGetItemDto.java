package com.example.reservasSpring.domain.dto.employee;

public record EmployeeGetItemDto(
        Integer id,
        String firstName,
        String lastName,
        String email,
        String jobName,
        Boolean enable

) {
}
