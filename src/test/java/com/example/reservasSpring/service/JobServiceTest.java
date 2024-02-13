package com.example.reservasSpring.service;

import com.example.reservasSpring.application.service.JobService;
import com.example.reservasSpring.domain.dto.JobDto;
import com.example.reservasSpring.domain.mapper.JobMapper;
import com.example.reservasSpring.domain.model.Job;
import com.example.reservasSpring.domain.repository.IJobRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class JobServiceTest {

    @InjectMocks
    private JobService jobService;

    @Mock
    private IJobRepository jobRepository;

    @Mock
    private JobMapper jobMapper;

    @Test
    public void findAllTest() {

        List<Job> expectedJobs = Arrays.asList(new Job(), new Job());
        when(jobRepository.findAll()).thenReturn(expectedJobs);


        List<Job> actualJobs = jobService.findAll();


        assertEquals(expectedJobs, actualJobs);
        //verify(jobRepository, times(1)).findAll();
    }

    @Test
    public void create() {

        JobDto jobDto = new JobDto(1,"developer"); // Suponiendo que JobDto es una clase con datos relevantes
        Job expectedJob = new Job(1,"developer");
        when(jobMapper.toJob(jobDto)).thenReturn(expectedJob);
        when(jobRepository.save(expectedJob)).thenReturn(expectedJob);


        Job actualJob = jobService.create(jobDto);


        assertEquals(expectedJob, actualJob);

    }

    @Test
    public void findById_ShouldReturnJobWhenFound() {

        Integer id = 1;
        Job expectedJob = new Job(1,"developer");
        when(jobRepository.findById(id)).thenReturn(Optional.of(expectedJob));

        Job actualJob = jobService.findById(id);

        assertEquals(expectedJob, actualJob);
    }


    @Test
    public void findById_ShouldThrowExceptionWhenNotFound() {
        // Arrange
        Integer id = 1;
        when(jobRepository.findById(id)).thenReturn(Optional.empty());


        assertThrows(ResponseStatusException.class, () -> {
            jobService.findById(id);
        });
    }

    @Test
    public void update() {
        // Arrange
        Integer id = 1;
        JobDto jobDto = new JobDto(1,"Senior developer"); // Suponiendo que JobDto es una clase con datos relevantes
        Job existingJob = new Job(1,"developer");
        existingJob.setId(id);
        Job updatedJob = new Job();

        when(jobRepository.findById(id)).thenReturn(Optional.of(existingJob));
        when(jobMapper.toJob(jobDto)).thenReturn(updatedJob);

        // Act
        jobService.update(id, jobDto);

        // Assert

        assertEquals(id, updatedJob.getId());
    }
    @Test
    public void delete() {
        // Arrange
        Integer id = 1;
        Job jobToDelete = new Job();
        jobToDelete.setId(id);

        when(jobRepository.findById(id)).thenReturn(Optional.of(jobToDelete));

        // Act
        jobService.delete(id);

        // Assert
        Mockito.verify(jobRepository,Mockito.times(1)).findById(id);
        Mockito.verify(jobRepository, Mockito.times(1)).delete(jobToDelete);
    }

}
