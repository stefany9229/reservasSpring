package com.example.reservasSpring.application.service;

import com.example.reservasSpring.domain.dto.JobDto;
import com.example.reservasSpring.domain.dto.employee.EmployeeCreateDTO;
import com.example.reservasSpring.domain.model.Job;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class DataLoader implements ApplicationRunner {

    private JobService jobService;
    private EmployeeService employeeService;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        // creo el cargo de psicologa
        JobDto psicologia= new JobDto(1,"Psicologo(a)");
        //agrego a la BD
        Job job= jobService.create(psicologia);

        // creo los psicologos
        EmployeeCreateDTO employee1 = new EmployeeCreateDTO(null, "Carlos", "González", "carlos.gonzalez@example.com", "password123", 1, true);
        EmployeeCreateDTO employee2 = new EmployeeCreateDTO(null, "María", "López", "maria.lopez@example.com", "password456", 1, true);
        EmployeeCreateDTO employee3 = new EmployeeCreateDTO(null, "Juan", "Martínez", "juan.martinez@example.com", "password789", job.getId(), true);


        employeeService.create(employee1);
        employeeService.create(employee2);
        employeeService.create(employee3);

    }
}