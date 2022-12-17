package com.ironhack.quemirasbobo.repository;

import com.ironhack.quemirasbobo.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmRepository extends JpaRepository<Film,Long> {

    Optional<Film> findFilmByName(String name);
    Optional<Film> findFilmByUserId(Long id);
    List<Film> findFilmsById(Long id);
}