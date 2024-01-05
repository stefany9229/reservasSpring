package com.example.reservasSpring.domain.dto.employee;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record EmployeeGetItemDto(
        Integer id,
        String firstName,
        String lastName,
        String email,
        String jobName,
        Boolean enable

) {
}
