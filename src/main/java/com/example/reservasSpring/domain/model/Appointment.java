package com.example.reservasSpring.domain.model;

import java.time.LocalDateTime;

public class Appointment {
    private Integer id;
    private User user;
    private Employee employee;

    private EStatus status;
    private LocalDateTime createdAt;

}
