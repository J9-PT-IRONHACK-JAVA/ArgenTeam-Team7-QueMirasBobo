package com.ironhack.quemirasbobo.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String username;
    private String password;

    @OneToMany (mappedBy = "user")
    private List<Film> filmList;

    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }
}