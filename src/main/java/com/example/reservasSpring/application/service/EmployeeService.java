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
import org.springframework.transaction.annotation.Transactional;
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


    public Employee createEmployee(EmployeeCreateDTO employeeCreateDTO) {
        Employee employee= new Employee();
        User user_ = new User();
        Optional<User> userOptional =userRepository.findUserByEmail(employeeCreateDTO.email());

        // validando y agregando la parte de usuario
        if(userOptional.isPresent()){

            user_= userOptional.get();
            user_.setEnable(true);
            Set<ERole> roles = user_.getRoles();
            roles.add(ERole.PROFESSIONAL);
            user_.setRoles(roles);
        }else{
            user_= userRepository.save(employeeMapper.employeeCreateDtoTouser(employeeCreateDTO));
            user_.setRoles(Collections.singleton(ERole.PROFESSIONAL));
            user_.setEnable(true);
        }
        employee.setUser(user_);

        // vaidando y agregado el job

        Optional<Job> jobOptional= jobRepository.findById(employeeCreateDTO.idJob());
        if(!jobOptional.isPresent()){
            throw new ResponseStatusException(
                    org.springframework.http.HttpStatus.BAD_REQUEST, "el id de cargo que intenta agregar no existe");
        }
        employee.setJob(jobOptional.get());
        employee.setEnable(employeeCreateDTO.enable());
       // EmployeeGetItemDto response= employeeMapper.employoToGetItemDto(employeeRepository.save(employee));

        return employee;


    }

    public EmployeeGetItemDto create (EmployeeCreateDTO employeeCreateDT){
        Employee employeeTemp= this.createEmployee(employeeCreateDT);
        Employee employee= employeeRepository.save(employeeTemp);
        EmployeeGetItemDto response= employeeMapper.employoToGetItemDto(employee);
        return response;

    }

    public Employee findEmployeeByEmail(String email) {
        return employeeRepository.findByUserEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        org.springframework.http.HttpStatus.BAD_REQUEST, "Empleado no econtrado"));
    }
}
