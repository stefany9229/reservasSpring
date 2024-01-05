package com.example.reservasSpring.domain.repository;

import com.example.reservasSpring.domain.model.lasting.EStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmployeeRepository extends JpaRepository<EStatus.Employee, Integer> {
}
