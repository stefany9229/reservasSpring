package com.example.reservasSpring.application.controller;

import com.example.reservasSpring.application.service.AuthenticationService;
import com.example.reservasSpring.domain.dto.AuthenticationDto;
import com.example.reservasSpring.domain.dto.user.UserCreateDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public record AuthenticationController(
  AuthenticationService authenticationService
) {

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody UserCreateDto userDto) {
    String token = authenticationService.register(userDto);
    return new ResponseEntity<>(token, HttpStatus.CREATED);
  }

  @PostMapping("/authenticate")
  public ResponseEntity<?> authenticate(@RequestBody AuthenticationDto authenticationDto) {
    String token = authenticationService.authenticate(authenticationDto);
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    System.out.println(authentication.getAuthorities());
        return new ResponseEntity<>(token, HttpStatus.OK);
  }
}
