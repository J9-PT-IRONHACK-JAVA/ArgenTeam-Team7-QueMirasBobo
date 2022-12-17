package com.ironhack.quemirasbobo.repository;

import com.ironhack.quemirasbobo.model.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface PlatformRepository extends JpaRepository<Platform, Long> {

    Optional<Platform> findPlatformByName(String name);
    List<Platform> findAllByFilmsId(Long id);
}