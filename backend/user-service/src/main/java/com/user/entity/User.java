package com.user.entity;

import jakarta.persistence.*;

import lombok.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.sql.Timestamp;

/*
 * User account
 */
@Table(name= "user")
@Setter
@Getter
@Entity
@NoArgsConstructor // for JPA
public class User {

    private @Id String id;

    @Email
    @Column(unique = true)
    @NotBlank(message = "Email should not be blank")
    private String email;

    @NotBlank(message = "First name should not be blank")
    private String first_name;

    @NotBlank(message = "Last name should not be blank")
    private String last_name;

    @NotBlank(message = "Enroll date should not be blank")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp enroll_date; // Format for MySQL "yyyy-MM-dd HH:mm:ss" DATETIME, remember to replace table

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    public User(String id, String email, String first_name, String last_name, Role role) {
        this.id = id;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.role = role;
        this.enroll_date = new Timestamp(System.currentTimeMillis());
    }
}
