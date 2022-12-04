package com.paymybuddy.paymybuddy.model;

import jakarta.persistence.*;

import java.time.LocalDate;

//@DynamicUpdate
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    private LocalDate birthdate;

    private String email;

    private String address;

    private int wallet;

    private String password;

    public String getFirstname(){
        return firstName;
    }



}
