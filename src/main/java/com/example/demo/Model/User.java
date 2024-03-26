package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.*;


@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long id;
    private String username;
    private String password;
    private String confirmPassword;
    @Column(name = "filename")
    private String filename;
    private String profileBackgroundFilename;
    private String email;
    private String name;
    private String discord;
    private String VK;

    @OneToMany(mappedBy = "userId")
    private List<News> publishNewsProfile;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "registration_date")
    private Date registrationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_active")
    private Date lastActive;

    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<Articles> articles;

    @CollectionTable(name = "user_status", joinColumns = @JoinColumn(name = "user_id"))
    @ElementCollection(targetClass = UserStatus.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<UserStatus> statuses = new HashSet<>();

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", filename='" + filename + '\'' +
                ", profileBackgroundFilename='" + profileBackgroundFilename + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", discord='" + discord + '\'' +
                ", VK='" + VK + '\'' +
                ", registrationDate=" + registrationDate +
                ", lastActive=" + lastActive +
                ", roles=" + roles +
                ", articles=" + articles +
                '}';
    }
}

