package com.ironhack.quemirasbobo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Film {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String name;
    private Type type;
    private Integer year;

    @ManyToMany (mappedBy = "films")
    private List<User> users = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "film_platforms",
            joinColumns = @JoinColumn (name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "platform_id")
    )
    private List<Platform> platforms;

}
