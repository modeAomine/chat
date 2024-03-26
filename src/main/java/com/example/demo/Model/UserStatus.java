package com.example.demo.Model;

public enum UserStatus {

    ONLINE("ONLINE"),

    OFFLINE("OFFLINE");

    private final String name;

    UserStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
