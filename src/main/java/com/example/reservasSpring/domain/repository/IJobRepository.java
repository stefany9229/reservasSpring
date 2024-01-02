package com.example.reservasSpring.domain.repository;
import com.example.reservasSpring.domain.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IJobRepository extends JpaRepository<Job, Integer> {
}
