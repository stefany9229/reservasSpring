package com.example.reservasSpring.application.controller;

import com.example.reservasSpring.application.service.AppointmetService;
import com.example.reservasSpring.domain.dto.appointment.AppointmentCreateDto;
import com.example.reservasSpring.domain.dto.employee.EmployeeCreateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentController {

    @Autowired
    private AppointmetService appointmetService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AppointmentCreateDto appointmentCreateDto){
        return  new ResponseEntity<>(appointmetService.create(appointmentCreateDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        return  new ResponseEntity<>(appointmetService.findAll(),HttpStatus.OK);
    }

    @GetMapping(("/byUser"))
    public ResponseEntity<?> findAllByUSre(){
        return  new ResponseEntity<>(appointmetService.findByUserId(),HttpStatus.OK);
    }

    @GetMapping(("/byEmployee"))
    public ResponseEntity<?> findAllByEmployee(){
        return  new ResponseEntity<>(appointmetService.findByEmployeeEmail(),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update (@PathVariable Integer id, @RequestBody AppointmentCreateDto appointmentCreateDto){
        appointmetService.update(id, appointmentCreateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete (@PathVariable Integer id ){
        appointmetService.deleteByid(id);
        return new ResponseEntity<>("Borrado con éxito",HttpStatus.OK);
    }

}
