package com.example.demo.Model;


import org.springframework.security.core.GrantedAuthority;

public enum Role {

    USER("Пользователь"),
    SUPPORT("Потдержка"),
    ADMIN("Администратор");

    private final String name;

    Role(String name) { this.name = name; }

    public String getName() { return  name; }

}
