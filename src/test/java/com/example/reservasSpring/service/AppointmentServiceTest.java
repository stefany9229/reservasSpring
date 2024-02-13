package com.example.reservasSpring.service;

import com.example.reservasSpring.application.service.AppointmetService;
import com.example.reservasSpring.application.service.EmployeeService;
import com.example.reservasSpring.application.service.UserService;
import com.example.reservasSpring.domain.dto.appointment.AppointmentCreateDto;
import com.example.reservasSpring.domain.model.User;
import com.example.reservasSpring.domain.dto.appointment.AppointmetGetDto;
import com.example.reservasSpring.domain.mapper.AppointmetMapper;
import com.example.reservasSpring.domain.model.Appointment;
import com.example.reservasSpring.domain.model.Employee;
import com.example.reservasSpring.domain.model.lasting.EStatus;
import com.example.reservasSpring.domain.repository.IAppointmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {
    @InjectMocks
    private AppointmetService appointmentService;

    @Mock
    private IAppointmentRepository appointmentRepository;


    @Mock
    private UserService userService;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private AppointmetMapper appointmetMapper;

    @Test
    public void findAll() {
        // Arrange
        List<Appointment> appointments = List.of(new Appointment(), new Appointment());
        List<AppointmetGetDto> appointmentGetDtos = List.of(
                new AppointmetGetDto(1, "John", "Doe", "Jane", "Roe", EStatus.ACTIVE, LocalDateTime.now(), LocalDateTime.now().plusHours(1), LocalDateTime.now()),
                new AppointmetGetDto(2, "Foo", "Bar", "Baz", "Qux", EStatus.CANCELLED, LocalDateTime.now(), LocalDateTime.now().plusHours(1), LocalDateTime.now())
        );
        when(appointmentRepository.findAll()).thenReturn(appointments);
        when(appointmetMapper.appointmentToAppointmentGetDtoList(appointments)).thenReturn(appointmentGetDtos);

        // Act
        List<AppointmetGetDto> result = appointmentService.findAll();

        // Assert
        assertEquals(appointmentGetDtos, result);
        Mockito.verify(appointmentRepository).findAll();
        Mockito.verify(appointmetMapper).appointmentToAppointmentGetDtoList(appointments);
    }

    @Test
    public void createAppointment_ShouldCreateAppointmentWhenUserAndEmployeeAreDifferent() {
        // Configure los emails que serán usados en la prueba
        String clientEmail = "client@example.com";
        String professionalEmail = "professional@example.com";
        // Crear el DTO con los emails correctos
        AppointmentCreateDto appointmentCreateDto = new AppointmentCreateDto(
                clientEmail,
                professionalEmail,
                EStatus.ACTIVE,
                LocalDateTime.now(),       // startTime
                LocalDateTime.now().plusHours(2) // finishTime
        );

        // Crear usuarios y empleados simulados con los emails correspondientes
        User client = new User();
        client.setEmail(clientEmail);
        User professional = new User();
        professional.setEmail(professionalEmail);
        Employee employee = new Employee();
        employee.setUser(professional);

        // Configurar los stubs para devolver los objetos simulados correctos
        when(userService.findByEmail(clientEmail)).thenReturn(client);
        when(employeeService.findEmployeeByEmail(professionalEmail)).thenReturn(employee);

        Appointment result = appointmentService.createAppointment(appointmentCreateDto);

        // Verificar que el resultado tenga las propiedades esperadas
        assertNotNull(result);
        assertEquals(clientEmail, result.getUser().getEmail());
        assertEquals(professionalEmail, result.getEmployee().getUser().getEmail());

    }

    @Test
    public void createAppointment_ShouldThrowExceptionWhenUserAndEmployeeAreTheSame() {
        // Configure los emails que serán usados en la prueba
        String email = "sameperson@example.com";
        AppointmentCreateDto appointmentCreateDto = new AppointmentCreateDto(
                email,
                email, // El mismo email para ambos indica que son la misma persona
                EStatus.ACTIVE,
                LocalDateTime.now(),       // startTime
                LocalDateTime.now().plusHours(2) // finishTime
        );
        // Crear el DTO con los emails correctos


        // Crear usuarios y empleados simulados con los emails correspondientes
        User client = new User();
        client.setEmail(email);
        User professional = new User();
        professional.setEmail(email);
        Employee employee = new Employee();
        employee.setUser(professional);

        // Configurar los stubs para devolver los objetos simulados correctos
        when(userService.findByEmail(email)).thenReturn(client);
        when(employeeService.findEmployeeByEmail(email)).thenReturn(employee);

        assertThrows(
                ResponseStatusException.class,
                () -> appointmentService.createAppointment(appointmentCreateDto)
        );

    }
}
