package com.ironhack.quemirasbobo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FilmDto {

    private Long id;
    private String name;
    private String type;
    private int year;

}
