package com.example.reservasSpring.domain.model;

import lombok.Getter;

@Getter
public enum ERole {

    CLIENT("CLIENT"),
    PROFESSIONAL("PROFESSIONAL");

    ERole(String name){
        this.name = name;
    }
    private String name;
}
