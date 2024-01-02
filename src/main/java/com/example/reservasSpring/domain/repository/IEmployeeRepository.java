package com.example.reservasSpring.domain.repository;

import com.example.reservasSpring.domain.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmployeeRepository extends JpaRepository<Employee, Integer> {
}
