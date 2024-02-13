package com.example.reservasSpring.application.service;

import com.example.reservasSpring.domain.dto.JobDto;
import com.example.reservasSpring.domain.mapper.JobMapper;
import com.example.reservasSpring.domain.model.Job;
import com.example.reservasSpring.domain.repository.IJobRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public Job findById( Integer id){
        Job job= jobRepository.findById(id).orElseThrow(() ->new ResponseStatusException(
                HttpStatus.NOT_FOUND, "trabajo no enocntrado"));
        return job;
    }

    public void update (Integer id, JobDto jobDto){
        Job job= this.findById(id);
        Job jobResponse= jobMapper.toJob(jobDto);
        jobResponse.setId(id);
        jobRepository.save(jobResponse);
    }

    public void delete (Integer id) {
        Job job= this.findById(id);
        jobRepository.delete(job);
    }
}
