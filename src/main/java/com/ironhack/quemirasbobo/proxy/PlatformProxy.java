package com.ironhack.quemirasbobo.proxy;

import com.ironhack.quemirasbobo.dto.PlatformDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient (name = "plattforms", url = "https://api.watchmode.com/v1/title/")
public interface PlatformProxy {

    @GetMapping("{id}/sources/?apiKey=WRck00tqIA09ecOp6o4iF2pLnXkAF3p3L0aCRgT7")
    List<PlatformDto> getAllPlatformsFromFilmId(@PathVariable Long id);
}
