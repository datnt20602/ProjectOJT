package com.project.ojt.projectojt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String password;
    private boolean active;
    private String otp;
    private LocalDateTime otpGeneratedTime;

    @Enumerated(EnumType.STRING)
    private AuthenticationProvider auth_provider;



}
