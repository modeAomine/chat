package com.example.demo.Model;

public enum MessageStatus {
    RECEIVED("RECEIVED"),

    DELIVERED("DELIVERED");

    private final String name;

    MessageStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
