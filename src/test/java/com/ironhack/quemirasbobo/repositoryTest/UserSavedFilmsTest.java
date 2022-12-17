package com.ironhack.quemirasbobo.repositoryTest;

import com.ironhack.quemirasbobo.Day26ProyectoEquiposTvSeriesApplication;
import com.ironhack.quemirasbobo.model.Film;
import com.ironhack.quemirasbobo.model.Type;
import com.ironhack.quemirasbobo.model.User;
import com.ironhack.quemirasbobo.repository.FilmRepository;
import com.ironhack.quemirasbobo.repository.UserRepository;
import com.ironhack.quemirasbobo.service.FilmService;
import com.ironhack.quemirasbobo.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Lazy intialization.......................
 */

@SpringBootTest
public class UserSavedFilmsTest {
    @MockBean
    Day26ProyectoEquiposTvSeriesApplication day26ProyectoEquiposTvSeriesApplication;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private FilmService filmService;

    @BeforeEach
    void setUp() {
        userService.saveUser(new User("Alfred", "alfredito", "1234"));
        userService.saveUser(new User("Andres", "andresito", "4321"));

        var film1 = new Film("Good Will Hunting", Type.MOVIE, 1998);
        var film2 = new Film("Piratas del Caribe", Type.MOVIE, 2003);
        var film3 = new Film("Avatar", Type.MOVIE, 2022);
        film1.setUser(userService.findUserByUsername("alfredito").get());
        filmRepository.save(film1);
        film2.setUser(userService.findUserByUsername("alfredito").get());
        filmRepository.save(film2);
        film3.setUser(userService.findUserByUsername("alfredito").get());
        filmRepository.save(film3);
        //filmService.saveFilm(film1);
        //filmService.saveFilm(film2);
        //filmService.saveFilm(film3);

        /*
        filmService.saveFilm(new Film("Good Will Hunting", Type.MOVIE, 1998));
        filmService.saveFilm(new Film("Piratas del Caribe", Type.MOVIE, 2003));
        filmService.saveFilm(new Film("Avatar", Type.MOVIE, 2022));
         */
    }
    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        filmRepository.deleteAll();
    }

    @Test
    void findFilmsWatchedByUser() {
        var user = userService.findUserByUsername("alfredito");

        var film1 = filmService.findFilmByName("Avatar");
        if (user.isPresent() && film1.isPresent()) saveWatchedFilm(user.get(), film1.get());

        var film2 = filmService.findFilmByName("Piratas del Caribe");
        if (user.isPresent() && film2.isPresent()) saveWatchedFilm(user.get(), film2.get());

        var film3 = filmService.findFilmByName("Good Will Hunting");
        if (user.isPresent() && film3.isPresent()) saveWatchedFilm(user.get(), film3.get());

        var repoUser = userService.findUserByUsername("alfredito");

        //repoUser.ifPresent(value -> assertEquals(3, value.getFilms().size()));
    }
    public void saveWatchedFilm(User user, Film film) {

        film.setUser(user);
        filmService.saveFilm(film);
        /*
        List<Film> filmsWatched = new ArrayList<>();;
        if (user.getFilms().size() > 0) {
            filmsWatched = user.getFilms();
        }
        filmsWatched.add(film);
        user.setFilms(filmsWatched);
        userService.saveUser(user);
        */
    }
}
