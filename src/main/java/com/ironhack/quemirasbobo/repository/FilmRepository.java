package com.ironhack.quemirasbobo.repository;

import com.ironhack.quemirasbobo.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends JpaRepository<Film,Long> {

}
