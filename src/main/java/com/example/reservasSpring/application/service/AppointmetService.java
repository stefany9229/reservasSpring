package com.example.reservasSpring.application.service;


import com.example.reservasSpring.domain.dto.appointment.AppointmentCreateDto;
import com.example.reservasSpring.domain.dto.appointment.AppointmetGetDto;
import com.example.reservasSpring.domain.mapper.AppointmetMapper;
import com.example.reservasSpring.domain.model.Appointment;
import com.example.reservasSpring.domain.model.Employee;
import com.example.reservasSpring.domain.model.User;
import com.example.reservasSpring.domain.model.lasting.EStatus;
import com.example.reservasSpring.domain.repository.IAppointmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@AllArgsConstructor
public class AppointmetService {
    private IAppointmentRepository appointmentRepository;
    private UserService userService;
    private EmployeeService employeeService;
    private AppointmetMapper appointmetMapper;

    public List<AppointmetGetDto> findAll(){
        List<Appointment> appointments= appointmentRepository.findAll();
        List<AppointmetGetDto> appointmetGetDtoList = appointmetMapper.appointmentToAppointmentGetDtoList(appointments);
        return appointmetGetDtoList;
    }

    public Appointment createAppointment(AppointmentCreateDto appointmentCreateDto){

        //Busco el usario
        User user = userService.findByEmail(appointmentCreateDto.clientEmail());
        //Busco el employe
        Employee employee= employeeService.findEmployeeByEmail(appointmentCreateDto.professionalEmail());

        if(user.equals(employee.getUser())){
            throw new  ResponseStatusException(
                    org.springframework.http.HttpStatus.BAD_REQUEST, "El cliente y el profesional no pueden correponder al mismo ususario");
        }

        Appointment appointment = new Appointment();
        appointment.setEmployee(employee);
        appointment.setUser(user);
        appointment.setStartTime(appointmentCreateDto.startTime());
        appointment.setFinishTime(appointmentCreateDto.finishTime());
        appointment.setStatus(appointmentCreateDto.status());
        return appointment;
    }

    public AppointmetGetDto  create(AppointmentCreateDto appointmentCreateDto){
        Appointment appointmentToSent=(this.createAppointment(appointmentCreateDto));
        appointmentToSent.setStatus(EStatus.ACTIVE);
        Appointment appointment= appointmentRepository.save(appointmentToSent);
        AppointmetGetDto response= appointmetMapper.appointmentToAppointmentGetDto(appointment);
        return response;
    }

    public Appointment findByIdAppoinment(Integer id){
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay un usuario asociado a ese id"));
        //AppointmetGetDto response= appointmetMapper.appointmentToAppointmentGetDto(appointment);
        return appointment;
    }

    public AppointmetGetDto findById(Integer id){
        AppointmetGetDto response=appointmetMapper.appointmentToAppointmentGetDto(this.findByIdAppoinment(id));
        return response;
    }

    public void deleteByid(Integer id){
        Appointment appointment= this.findByIdAppoinment(id);
        appointmentRepository.delete(appointment);

    }

    public void update(Integer id, AppointmentCreateDto appointmentCreateDto){
        Appointment appointmentValidate= this.findByIdAppoinment(id);
        Appointment appointment= this.createAppointment(appointmentCreateDto);
        appointment.setId(id);
        appointmentRepository.save(appointment) ;

    }
}
