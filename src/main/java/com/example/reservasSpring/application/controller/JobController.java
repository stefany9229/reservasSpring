package com.example.reservasSpring.application.controller;


import com.example.reservasSpring.application.service.JobService;
import com.example.reservasSpring.domain.dto.JobDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/job")
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping
    public ResponseEntity<List<?>> findAllJobs(){
        return new ResponseEntity<>(jobService.findAll(),HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<?> createJob(@RequestBody JobDto jobDto){

        return  new ResponseEntity<>(jobService.create(jobDto), HttpStatus.CREATED);
    }


}
