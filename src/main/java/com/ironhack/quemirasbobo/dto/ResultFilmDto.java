package com.ironhack.quemirasbobo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ResultFilmDto {

    private List<FilmDto> results;
}
