package com.example.reservasSpring.application.controller;

import com.example.reservasSpring.application.service.EmployeeService;
import com.example.reservasSpring.domain.dto.JobDto;
import com.example.reservasSpring.domain.dto.employee.EmployeeCreateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /*
    @GetMapping
    public ResponseEntity<List<?>> findAllJobs(){
        return new ResponseEntity<>(employeeService.findAll(), HttpStatus.OK);
    }
    */



    @PostMapping
    public ResponseEntity<?> create(@RequestBody EmployeeCreateDTO employeeCreateDTO){
        return  new ResponseEntity<>(employeeService.create(employeeCreateDTO), HttpStatus.CREATED);
    }

}
