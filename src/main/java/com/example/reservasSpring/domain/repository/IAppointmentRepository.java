package com.example.reservasSpring.domain.repository;

import com.example.reservasSpring.domain.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAppointmentRepository extends JpaRepository<Appointment, Integer> {
}
