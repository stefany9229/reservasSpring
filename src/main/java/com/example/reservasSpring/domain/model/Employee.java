package com.example.reservasSpring.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    private Integer id;
    private  User user;
    private Job job;
    private LocalDateTime creatAt;

}
