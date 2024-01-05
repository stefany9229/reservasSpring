package com.example.reservasSpring.application.controller;

import com.example.reservasSpring.application.service.AppointmetService;
import com.example.reservasSpring.domain.dto.appointment.AppointmentCreateDto;
import com.example.reservasSpring.domain.dto.employee.EmployeeCreateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentController {

    @Autowired
    private AppointmetService appointmetService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AppointmentCreateDto appointmentCreateDto){
        return  new ResponseEntity<>(appointmetService.create(appointmentCreateDto), HttpStatus.CREATED);
    }
}
