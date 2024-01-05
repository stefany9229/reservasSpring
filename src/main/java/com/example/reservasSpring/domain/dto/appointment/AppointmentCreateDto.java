package com.example.reservasSpring.domain.dto.appointment;

import com.example.reservasSpring.domain.model.lasting.EStatus;

import java.time.LocalDateTime;

public record AppointmentCreateDto(
String clientEmail,
String professionalEmail,
LocalDateTime startTime,
LocalDateTime finishTime
) {
}
