package com.example.reservasSpring.domain.mapper;


import com.example.reservasSpring.domain.dto.employee.EmployeeCreateDTO;
import com.example.reservasSpring.domain.dto.employee.EmployeeGetItemDto;
import com.example.reservasSpring.domain.model.Employee;
import com.example.reservasSpring.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel  = MappingConstants.ComponentModel.SPRING)
public interface EmployeeMapper {

    @Mapping(target = "enable", ignore = true)
    User employeeCreateDtoTouser(EmployeeCreateDTO employeeCreateDTO);

    @Mapping(source = "employee.job.name",target = "jobName")
    @Mapping(source = "employee.user.firstName",target = "firstName")
    @Mapping(source = "user.lastName",target="lastName")
    @Mapping(source ="user.email",target="email")
    EmployeeGetItemDto employoToGetItemDto(Employee employee);


}
