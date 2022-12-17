package com.ironhack.quemirasbobo.service;

import com.ironhack.quemirasbobo.model.Film;
import com.ironhack.quemirasbobo.model.Platform;
import com.ironhack.quemirasbobo.model.User;
import com.ironhack.quemirasbobo.repository.PlatformRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlatformService {
    private final PlatformRepository platformRepository;

    public List<Platform> findPlatforms(Film film) {
        return platformRepository.findAllByFilmsId(film.getId());
    }

    public Platform savePlatform(Platform platform) {
        return platformRepository.save(platform);
    }
}
