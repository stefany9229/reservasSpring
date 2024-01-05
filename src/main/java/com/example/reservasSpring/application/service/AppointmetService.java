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
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@AllArgsConstructor
public class AppointmetService {
    private IAppointmentRepository appointmentRepository;
    private UserService userService;
    private EmployeeService employeeService;
    private AppointmetMapper appointmetMapper;

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
        appointment.setStatus(EStatus.ACTIVE);

        return appointment;

    }

    public AppointmetGetDto  create(AppointmentCreateDto appointmentCreateDto){
        Appointment appointment= appointmentRepository.save(this.createAppointment(appointmentCreateDto));
        AppointmetGetDto response= appointmetMapper.appointmentToAppointmentGetDto(appointment);
        return response;
    }

}
