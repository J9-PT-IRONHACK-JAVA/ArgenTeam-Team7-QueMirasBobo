package com.ironhack.quemirasbobo.service;

import com.ironhack.quemirasbobo.model.Film;
import com.ironhack.quemirasbobo.model.User;
import com.ironhack.quemirasbobo.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmRepository filmRepository;

    public Optional<Film> findFilmByName(String name) {
        return filmRepository.findFilmByName(name);
    }
    public List<Film> findFilms(User user) {
        return filmRepository.findFilmsByUserId(user.getId());
    }
    public Film saveFilm(Film film) {
        return filmRepository.save(film);
    }
}
