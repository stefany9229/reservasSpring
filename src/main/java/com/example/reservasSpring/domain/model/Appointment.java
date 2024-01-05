package com.example.reservasSpring.domain.model;

import com.example.reservasSpring.domain.model.lasting.EStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private EStatus.Employee employee;

    @Enumerated(EnumType.STRING)
    private EStatus status;
    private LocalDateTime createdAt;

}
