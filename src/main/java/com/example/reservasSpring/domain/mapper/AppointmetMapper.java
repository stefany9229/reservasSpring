package com.example.reservasSpring.domain.mapper;


import com.example.reservasSpring.domain.dto.appointment.AppointmetGetDto;
import com.example.reservasSpring.domain.model.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel  = MappingConstants.ComponentModel.SPRING)
public interface AppointmetMapper {

    @Mapping(source = "user.firstName",target = "clientName")
    @Mapping(source = "user.lastName",target = "clientLastName")
    @Mapping(source = "employee.user.firstName",target = "professionalName")
    @Mapping(source = "employee.user.lastName",target = "professionalLastName")
    @Mapping(source = "status", target = "status")
    AppointmetGetDto appointmentToAppointmentGetDto(Appointment appointment);



}
