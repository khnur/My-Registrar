package com.example.myregistrar.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table
@Data
@NoArgsConstructor
public class EndUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(
            nullable = false,
            length = 30,
            unique = true
    )
    private String username;

    @Column(
            nullable = false,
            length = 100
    )
    private String password;

    @Column(
            nullable = false,
            length = 30
    )
    private String role;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "registered_at")
    private LocalDateTime registeredAt;


    public EndUser(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.registeredAt = LocalDateTime.now();
    }
}
