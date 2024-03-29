package com.example.reservasSpring.application.controller;


import com.example.reservasSpring.application.service.JobService;
import com.example.reservasSpring.application.service.UserService;
import com.example.reservasSpring.domain.dto.JobDto;
import com.example.reservasSpring.domain.dto.user.UserCreateDto;
import com.example.reservasSpring.domain.model.lasting.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<?>> findAllUsers(){
        return new ResponseEntity<>(userService.findAll(),HttpStatus.OK);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<?>> findAllUsersByRole(@PathVariable String role){
        return new ResponseEntity<>(userService.findAllByRole(ERole.valueOf(role)),HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<?> createJob(@RequestBody UserCreateDto userCreateDto){

        return  new ResponseEntity<>(userService.create(userCreateDto), HttpStatus.CREATED);
    }
}
