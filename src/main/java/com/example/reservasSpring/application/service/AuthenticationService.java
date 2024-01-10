package com.example.reservasSpring.application.service;


import com.example.reservasSpring.domain.dto.user.UserCreateDto;
import com.example.reservasSpring.domain.model.User;
import com.example.reservasSpring.domain.repository.IUserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.reservasSpring.domain.dto.AuthenticationDto;

@Service
public record AuthenticationService(
  IUserRepository userRepository,
  PasswordEncoder passwordEncoder,
  JwtService jwtService,
  AuthenticationManager authenticationManager

) {

  public String register(UserCreateDto userDto){
    User user = User.builder()
      .firstName(userDto.firstName())
      .lastName(userDto.lastName())
      .email(userDto.email())
      .enable(true)
      .password(passwordEncoder.encode(userDto.password()))
      .roles(userDto.roles())
      .build();
    userRepository.save(user);
    return jwtService.generateToken(user);
  }

  public String authenticate(AuthenticationDto authenticationDto){
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        authenticationDto.email(),
        authenticationDto.password()
      )
    );
    User user = userRepository.findUserByEmail(authenticationDto.email()).orElseThrow();
    return jwtService.generateToken(user);
  }

}
