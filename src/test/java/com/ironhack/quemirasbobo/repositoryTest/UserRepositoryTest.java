package com.ironhack.quemirasbobo.repositoryTest;

import com.ironhack.quemirasbobo.Day26ProyectoEquiposTvSeriesApplication;
import com.ironhack.quemirasbobo.model.User;
import com.ironhack.quemirasbobo.repository.UserRepository;
import com.ironhack.quemirasbobo.service.Menu;
import com.ironhack.quemirasbobo.service.UserService;
import lombok.experimental.StandardException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepositoryTest {
    @MockBean
    Day26ProyectoEquiposTvSeriesApplication day26ProyectoEquiposTvSeriesApplication;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userService.createUser(new User("Alfred", "Alfredito", "1234"));
        userService.createUser(new User("Andres", "Andresito", "4321"));
    }
    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void totalUsersTest() {
        assertEquals(2, userRepository.findAll().size());
    }

    @Test
    void createUserAndFindUserByUsernameTest() {
        userService.createUser(new User("Silvia", "Silvi", "qwerty"));
        assertTrue(userService.findUserByUsername("Silvi").isPresent());
        assertEquals("Silvi", userService.findUserByUsername("Silvi").get().getUsername());
    }
    @Test
    void noUserPresentTest() {
        var noUser = userService.findUserByUsername("Lionel");
        assertFalse(noUser.isPresent());
    }
}
