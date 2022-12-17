package com.ironhack.quemirasbobo.service;

import com.ironhack.quemirasbobo.model.Film;
import com.ironhack.quemirasbobo.model.User;
import com.ironhack.quemirasbobo.repository.FilmRepository;
import com.ironhack.quemirasbobo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmRepository filmRepository;

    public Optional<Film> findFilmByName(String name) {
        return filmRepository.findByName(name);
    }
    public List<Film> findFilms(User user) {
        return filmRepository.findFilmsById(user.getId());
    }
    public Film saveFilm(Film film) {
        return filmRepository.save(film);
    }
}
