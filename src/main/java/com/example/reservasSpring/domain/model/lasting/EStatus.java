package com.example.reservasSpring.domain.model.lasting;

import com.example.reservasSpring.domain.model.Job;
import com.example.reservasSpring.domain.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public enum EStatus {


    ACTIVE("ACTIVE"),
    CLOSED("CLOSED"),
    CANCELLED("CANCELLED");


    EStatus(String name){
        this.name = name;
    }
    private String name;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Entity
    public static class Employee {
    
        @Id
        @SequenceGenerator(
                name = "employee_id_sequence",
                sequenceName = "employee_id_sequence",
                allocationSize = 1
        )
        @GeneratedValue(
                strategy = GenerationType.SEQUENCE,
                generator = "employee_id_sequence"
        )
        private Integer id;
    
        @ManyToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "user_id", referencedColumnName = "id")
        private User user;
    
        @ManyToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "job_id", referencedColumnName = "id")
        private Job job;
    
        private LocalDateTime creatAt;
    
    }
}
