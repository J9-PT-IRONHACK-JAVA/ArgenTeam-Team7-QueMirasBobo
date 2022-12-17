package com.ironhack.quemirasbobo.service;

import com.ironhack.quemirasbobo.model.Film;
import com.ironhack.quemirasbobo.model.User;
import com.ironhack.quemirasbobo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

}
