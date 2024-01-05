package com.example.reservasSpring.domain.dto.appointment;

import com.example.reservasSpring.domain.model.lasting.EStatus;

import java.time.LocalDateTime;

public record AppointmetGetDto (
    Integer id,
    String clientName,
    String clientLastName,
    String professionalName,
    String professionalLastName,
    EStatus status,
    LocalDateTime startTime,
    LocalDateTime finishTime,

    LocalDateTime createdAt){

}
