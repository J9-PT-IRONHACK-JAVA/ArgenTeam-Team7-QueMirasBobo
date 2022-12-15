package com.ironhack.quemirasbobo.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

    @ManyToMany
    @JoinTable(
            name = "user_films",
            joinColumns = @JoinColumn (name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "film_id")
    )
    private List<Film> films = new ArrayList<>();

    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }
}
