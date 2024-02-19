package com.example.bookstoreecommerceapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private long id;
    private String fullName;
    private String username;
    private String password;
    private String email;
    private String role;
    @Temporal(TemporalType.DATE)
    private Date birthday;
    private String gender;
    private String address;
    private String phone;
    @Temporal(TemporalType.DATE)
    private Date dateRegistered;
    private String avatar;
}
