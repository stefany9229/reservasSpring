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


    EStatus(String name) {
        this.name = name;
    }

    private String name;
}
