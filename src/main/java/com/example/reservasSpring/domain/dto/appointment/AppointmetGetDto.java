package com.example.reservasSpring.domain.dto.appointment;

import java.time.LocalDateTime;

public record AppointmetGetDto (
    Integer id,
    String clientName,
    String clientLastName,
    String professionalName,
    String professionalLastName,
    LocalDateTime startTime,
    LocalDateTime finishTime,

    LocalDateTime createdAt){

}
