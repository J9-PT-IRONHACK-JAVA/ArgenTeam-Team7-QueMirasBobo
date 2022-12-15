package com.ironhack.quemirasbobo.model;

import com.ironhack.quemirasbobo.model.Film;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Platform {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany (mappedBy = "platforms")
    private List<Film> films;
}
