package com.example.reservasSpring.service;

import com.example.reservasSpring.domain.dto.user.UserCreateDto;
import com.example.reservasSpring.domain.dto.user.UserGetItemDto;
import com.example.reservasSpring.domain.mapper.UserMapper;
import com.example.reservasSpring.domain.model.User;
import com.example.reservasSpring.domain.model.lasting.ERole;
import com.example.reservasSpring.domain.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserService {
    @Mock
    private IUserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private com.example.reservasSpring.application.service.UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void createShouldReturnUserGetItemDto() {
        // Configuración
        Set<ERole> roles = Set.of(ERole.CLIENT); // Asumiendo que ERole es un enum de roles
        UserCreateDto userCreateDto = new UserCreateDto(
                null, // El ID generalmente se genera en la base de datos, así que puede ser null si es un nuevo registro
                "Nombre", // firstName
                "Apellido", // lastName
                "email@example.com", // email
                "password", // password
                true, // enable
                roles // roles
        );
        User user = new User(); // Suponiendo que tienes un constructor o setters para configurar esta entidad
        UserGetItemDto userGetItemDto = new UserGetItemDto(
                1, // id generado ficticiamente para la prueba
                "Nombre", // firstName
                "Apellido", // lastName
                "email@example.com", // email
                true, // enable
                roles // roles
        );

        when(userMapper.userCreateToUser(any(UserCreateDto.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.userToUserItem(any(User.class))).thenReturn(userGetItemDto);

        // Acción
        UserGetItemDto result = userService.create(userCreateDto);

        // Verificación
        assertNotNull(result);
        assertEquals("Nombre", result.firstName());
        assertEquals("Apellido", result.lastName());
        assertEquals("email@example.com", result.email());
        assertTrue(result.enable());
        assertEquals(roles, result.roles());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void findAllShouldReturnListOfUserGetItemDto() {
        // Configuración
        User user1 = new User();
        user1.setId(1);
        user1.setFirstName("Nombre1");
        user1.setLastName("Apellido1");
        user1.setEmail("email1@example.com");
        user1.setPassword("password1");
        user1.setEnable(true);
        user1.setRoles(Set.of(ERole.CLIENT));

        User user2 = new User();
        user2.setId(2);
        user2.setFirstName("Nombre2");
        user2.setLastName("Apellido2");
        user2.setEmail("email2@example.com");
        user2.setPassword("password2");
        user2.setEnable(true);
        user2.setRoles(Set.of(ERole.CLIENT));

        List<User> users = List.of(user1, user2);

        List<UserGetItemDto> userGetItemDtos = List.of(
                new UserGetItemDto(1, "Nombre1", "Apellido1", "email1@example.com", true, Set.of(ERole.CLIENT)),
                new UserGetItemDto(2, "Nombre2", "Apellido2", "email2@example.com", true, Set.of(ERole.CLIENT))
        );

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.userToUserItemList(users)).thenReturn(userGetItemDtos);

        // Acción
        List<UserGetItemDto> result = userService.findAll();

        // Verificación
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Nombre1", result.get(0).firstName());
        assertEquals("Nombre2", result.get(1).firstName());

        verify(userRepository).findAll(); // Verifica que se llama al método findAll del repositorio
        verify(userMapper).userToUserItemList(users); // Verifica que el mapeador está siendo utilizado
    }



}
