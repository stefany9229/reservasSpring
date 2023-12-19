package com.example.reservasSpring.domain.model;

public enum EStatus {


    ACTIVE("ACTIVE"),
    CLOSED("CLOSED"),
    CANCELLED("CANCELLED");


    EStatus(String name){
        this.name = name;
    }
    private String name;
}
