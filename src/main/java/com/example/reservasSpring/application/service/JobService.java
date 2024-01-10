package com.example.reservasSpring.application.service;

import com.example.reservasSpring.domain.dto.JobDto;
import com.example.reservasSpring.domain.mapper.JobMapper;
import com.example.reservasSpring.domain.model.Job;
import com.example.reservasSpring.domain.repository.IJobRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class JobService {

    private IJobRepository jobRepository;
    private JobMapper jobMapper;

    public List<Job> findAll(){
        return jobRepository.findAll();
    }
    public Job create ( JobDto jobDto){
        Job job= jobRepository.save(jobMapper.toJob(jobDto));
        return job;
    }



}
