package com.example.reservasSpring.application.service;


import com.example.reservasSpring.domain.dto.user.AuthRegisterDTO;
import com.example.reservasSpring.domain.dto.user.UserCreateDto;
import com.example.reservasSpring.domain.mapper.UserMapper;
import com.example.reservasSpring.domain.model.User;
import com.example.reservasSpring.domain.repository.IUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.reservasSpring.domain.dto.AuthenticationDto;
import org.springframework.web.server.ResponseStatusException;

@Service
public record AuthenticationService(
  IUserRepository userRepository,
  PasswordEncoder passwordEncoder,
  JwtService jwtService,
  AuthenticationManager authenticationManager,
  UserMapper userMapper

) {

  public AuthRegisterDTO register(UserCreateDto userDto){
    User user = User.builder()
      .firstName(userDto.firstName())
      .lastName(userDto.lastName())
      .email(userDto.email())
      .enable(true)
      .password(passwordEncoder.encode(userDto.password()))
      .roles(userDto.roles())
      .build();
    User userRepositiry= userRepository.save(user);
    AuthRegisterDTO response = new AuthRegisterDTO(userMapper.userToUserItem(userRepositiry),jwtService.generateToken(user));

    return response;
  }

  public AuthRegisterDTO authenticate(AuthenticationDto authenticationDto){
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        authenticationDto.email(),
        authenticationDto.password()
      )
    );
    User user = userRepository.findUserByEmail(authenticationDto.email()).orElseThrow();
    AuthRegisterDTO response = new AuthRegisterDTO(userMapper.userToUserItem(user),jwtService.generateToken(user));
    return response;
  }

  public User getPrincipal() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    // si authentication no es una instancia de UsernamePasswordAuthenticationToken
    if (!(authentication instanceof UsernamePasswordAuthenticationToken)) {
      throw new ResponseStatusException(
              HttpStatus.BAD_REQUEST, "Empleado no encontrado");
    }

    UsernamePasswordAuthenticationToken jwtAuthToken = (UsernamePasswordAuthenticationToken) authentication;

    // el principal es una instancia de User antes de hacer el casting
    if (!(jwtAuthToken.getPrincipal() instanceof User)) {
      throw new ResponseStatusException(
              HttpStatus.BAD_REQUEST, "Información del usuario no disponible");
    }

    User principal = (User) jwtAuthToken.getPrincipal();
    return principal;
  }

}
