package com.example.reservasSpring.domain.dto;

public record AuthenticationDto(
  String email,
  String password
) {
}
