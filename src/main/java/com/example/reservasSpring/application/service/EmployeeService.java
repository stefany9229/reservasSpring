package com.example.reservasSpring.application.service;


import com.example.reservasSpring.domain.dto.employee.EmployeeCreateDTO;
import com.example.reservasSpring.domain.dto.employee.EmployeeGetItemDto;
import com.example.reservasSpring.domain.mapper.EmployeeMapper;
import com.example.reservasSpring.domain.model.Employee;
import com.example.reservasSpring.domain.model.Job;
import com.example.reservasSpring.domain.model.User;
import com.example.reservasSpring.domain.model.lasting.ERole;
import com.example.reservasSpring.domain.repository.IEmployeeRepository;
import com.example.reservasSpring.domain.repository.IJobRepository;
import com.example.reservasSpring.domain.repository.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class EmployeeService {


    private IEmployeeRepository employeeRepository;
    private EmployeeMapper employeeMapper;
    private IUserRepository userRepository;
    private IJobRepository jobRepository;

    public EmployeeGetItemDto create(EmployeeCreateDTO employeeCreateDTO) {
        Employee employee= new Employee();
        User user = null;
        Optional<User> userOptional =userRepository.findUserByEmail(employeeCreateDTO.email());

        // validando y agregando la parte de usuario
        if(userOptional.isPresent()){

            user= userOptional.get();
            user.setEnable(true);
            Set<ERole> roles = user.getRoles();
            roles.add(ERole.PROFESSIONAL);
            user.setRoles(roles);
        }else{
            user= userRepository.save(employeeMapper.employeeCreateDtoTouser(employeeCreateDTO));
            user.setRoles(Collections.singleton(ERole.PROFESSIONAL));
            user.setEnable(true);
        }
        employee.setUser(user);

        // vaidando y agregado el job

        Optional<Job> jobOptional= jobRepository.findById(employeeCreateDTO.idJob());
        if(!jobOptional.isPresent()){
            throw new ResponseStatusException(
                    org.springframework.http.HttpStatus.BAD_REQUEST, "el id de cargo que intenta agregar no existe");
        }
        employee.setJob(jobOptional.get());
        employee.setEnable(employeeCreateDTO.enable());
        EmployeeGetItemDto response= employeeMapper.employoToGetItemDto(employeeRepository.save(employee));

        return response;


    }
}
