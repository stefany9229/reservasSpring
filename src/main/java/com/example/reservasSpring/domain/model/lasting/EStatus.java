package com.example.reservasSpring.domain.model.lasting;

import com.example.reservasSpring.domain.model.Job;
import com.example.reservasSpring.domain.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
public enum EStatus {


    ACTIVO("ACTIVO"),
    FINALIZADO("FINALIZADO"),
    CANCELADO("CANCELADO");


    EStatus(String name) {
        this.name = name;
    }
    private String name;
}
