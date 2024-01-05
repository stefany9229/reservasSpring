package com.example.reservasSpring.domain.repository;

import com.example.reservasSpring.domain.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Query("SELECT a FROM Appointment a WHERE a.employee.id = :employeeId AND ((a.startTime < :startDateTime AND a.finishTime > :startDateTime) OR (a.startTime < :endDateTime AND a.finishTime > :endDateTime))")
    List<Appointment> findAppointmentsWithinRange(
            @Param("employeeId") Integer employeeId,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime);
}
