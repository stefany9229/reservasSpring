package com.example.reservasSpring.service;

import com.example.reservasSpring.application.service.AppointmetService;
import com.example.reservasSpring.application.service.AuthenticationService;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    @Mock
    private AuthenticationService authenticationService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void findAll() {
        // Arrange
        List<Appointment> appointments = List.of(new Appointment(), new Appointment());
        List<AppointmetGetDto> appointmentGetDtos = List.of(
                new AppointmetGetDto(1, "John", "Doe", "Jane", "Roe", EStatus.ACTIVO, LocalDateTime.now(), LocalDateTime.now().plusHours(1), LocalDateTime.now()),
                new AppointmetGetDto(2, "Foo", "Bar", "Baz", "Qux", EStatus.CANCELADO, LocalDateTime.now(), LocalDateTime.now().plusHours(1), LocalDateTime.now())
        );
        when(appointmentRepository.findAll()).thenReturn(appointments);
        when(appointmetMapper.appointmentToAppointmentGetDtoList(appointments)).thenReturn(appointmentGetDtos);

        // Act
        List<AppointmetGetDto> result = appointmentService.findAll();

        // Assert
        assertEquals(appointmentGetDtos, result);
        verify(appointmentRepository).findAll();
        verify(appointmetMapper).appointmentToAppointmentGetDtoList(appointments);
    }


    @Test
    public void createAppointment_ShouldThrowExceptionWhenUserAndEmployeeAreTheSame() {
        // Configurar el email que será usado en la prueba
        String email = "sameperson@example.com";

        // Configurar el DTO de la cita
        AppointmentCreateDto appointmentCreateDto = new AppointmentCreateDto(
                email, // El mismo email para cliente y profesional
                EStatus.ACTIVO,
                LocalDateTime.now(),       // startTime
                LocalDateTime.now().plusHours(2) // finishTime
        );

        // Crear el usuario simulado que será retornado por authenticationService.getPrincipal()
        User mockedUser = new User();
        mockedUser.setEmail(email);

        // Crear el empleado simulado que será retornado por employeeService.findEmployeeByEmail()
        Employee mockedEmployee = new Employee();
        mockedEmployee.setUser(mockedUser);

        // Configurar los mocks
        when(authenticationService.getPrincipal()).thenReturn(mockedUser);
        when(employeeService.findEmployeeByEmail(email)).thenReturn(mockedEmployee);

        // Ejecutar la prueba esperando que se lance la excepción
        assertThrows(
                ResponseStatusException.class,
                () -> appointmentService.createAppointment(appointmentCreateDto),
                "El cliente y el profesional no pueden corresponder al mismo usuario"
        );
    }

    @Test
    void createAppointment_ShouldSucceedWhenUserAndEmployeeAreDifferent() {
        // Datos de prueba
        String clientEmail = "cliente@example.com";
        String professionalEmail = "profesional@example.com";
        AppointmentCreateDto appointmentCreateDto = new AppointmentCreateDto(
                professionalEmail, // Email del profesional
                EStatus.ACTIVO,    // Estado de la cita
                LocalDateTime.now(),       // startTime
                LocalDateTime.now().plusHours(2) // finishTime
        );

        // Crear usuario y empleado simulados con correos electrónicos diferentes
        User client = new User();
        client.setEmail(clientEmail);
        User professional = new User();
        professional.setEmail(professionalEmail);
        Employee employee = new Employee();
        employee.setUser(professional);

        // Configurar los mocks para devolver los objetos simulados
        when(authenticationService.getPrincipal()).thenReturn(client);
        when(employeeService.findEmployeeByEmail(professionalEmail)).thenReturn(employee);

        // Llamar al método que estamos probando
        Appointment result = appointmentService.createAppointment(appointmentCreateDto);

        // Verificar el resultado
        assertNotNull(result, "El resultado de la cita no debe ser nulo");
        assertEquals(client, result.getUser(), "El usuario de la cita debe ser el cliente");
        assertEquals(employee, result.getEmployee(), "El empleado de la cita debe ser el profesional");
        assertEquals(appointmentCreateDto.startTime(), result.getStartTime(), "La hora de inicio de la cita debe coincidir");
        assertEquals(appointmentCreateDto.finishTime(), result.getFinishTime(), "La hora de finalización de la cita debe coincidir");
        assertEquals(appointmentCreateDto.status(), result.getStatus(), "El estado de la cita debe coincidir");
    }

    @Test
    void findByIdAppoinment_ShouldReturnAppointmentWhenIdExists() {
        // Configuración de la prueba para un caso exitoso
        Integer id = 1;
        Appointment mockAppointment = new Appointment();
        // ... Configura el mockAppointment con los datos necesarios

        when(appointmentRepository.findById(id)).thenReturn(Optional.of(mockAppointment));

        // Acción
        Appointment foundAppointment = appointmentService.findByIdAppoinment(id);

        // Verificación
        assertEquals(mockAppointment, foundAppointment, "El appointment encontrado debe ser igual al mockAppointment");
    }

    @Test
    void findByIdAppoinment_ShouldThrowExceptionWhenIdDoesNotExist() {
        // Configuración de la prueba para un caso de falla
        Integer id = 1;
        when(appointmentRepository.findById(id)).thenReturn(Optional.empty());

        // Acción y Verificación
        assertThrows(
                ResponseStatusException.class,
                () -> appointmentService.findByIdAppoinment(id),
                "Se debe lanzar ResponseStatusException cuando el id no existe"
        );
    }

    @Test
    void update_ShouldUpdateAppointmentCorrectly() {
        // Datos de prueba
        Integer id = 1;
        String clientEmail = "cliente@example.com";
        String professionalEmail = "profesional@example.com";
        EStatus status = EStatus.ACTIVO;
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime finishTime = LocalDateTime.now().plusHours(2);

        // DTO de creación de la cita
        AppointmentCreateDto appointmentCreateDto = new AppointmentCreateDto(
                professionalEmail,
                status,
                startTime,
                finishTime
        );

        // Configuración de las dependencias del método
        User user = new User();
        user.setEmail(clientEmail);

        Employee employee = new Employee();
        employee.setUser(new User());
        employee.getUser().setEmail(professionalEmail);

        Appointment existingAppointment = new Appointment();
        existingAppointment.setId(id);
        existingAppointment.setUser(user);
        existingAppointment.setEmployee(employee);

        when(appointmentRepository.findById(id)).thenReturn(Optional.of(existingAppointment));
        when(authenticationService.getPrincipal()).thenReturn(user);
        when(employeeService.findEmployeeByEmail(professionalEmail)).thenReturn(employee);

        // Ejecución del método a probar
        appointmentService.update(id, appointmentCreateDto);

        // Verificaciones
        verify(appointmentRepository).findById(id);
        verify(appointmentRepository).save(any(Appointment.class));
    }


}
