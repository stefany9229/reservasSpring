package com.example.reservasSpring.application.service;

import com.example.reservasSpring.domain.dto.JobDto;
import com.example.reservasSpring.domain.dto.employee.EmployeeCreateDTO;
import com.example.reservasSpring.domain.dto.user.UserCreateDto;
import com.example.reservasSpring.domain.model.Job;
import com.example.reservasSpring.domain.model.User;
import com.example.reservasSpring.domain.model.lasting.ERole;
import com.example.reservasSpring.domain.repository.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@AllArgsConstructor
public class DataLoader implements ApplicationRunner {

    private JobService jobService;
    private EmployeeService employeeService;
    private IUserRepository userService;

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

        //creo usuario que a la vez es empleado

        Set<ERole> roles = new HashSet<>();
        roles.add(ERole.CLIENT);
        roles.add(ERole.PROFESSIONAL);

        User user= userService.findUserByEmail("maria.lopez@example.com").get();
        user.setRoles(roles);
        userService.save(user);


    }
}