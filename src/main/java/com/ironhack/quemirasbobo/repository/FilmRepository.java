package com.ironhack.quemirasbobo.repository;

import com.ironhack.quemirasbobo.model.Film;
import com.ironhack.quemirasbobo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilmRepository extends JpaRepository<Film,Long> {

    Optional<Film> findFilmByName(String name);


    Optional<Film> findFilmByUserId(Long id);
}
