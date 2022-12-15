package com.ironhack.quemirasbobo.proxy;

import com.ironhack.quemirasbobo.dto.FilmDto;
import com.ironhack.quemirasbobo.dto.ResultFilmDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient (name="watchmode",url ="https://api.watchmode.com/v1/")
public interface FilmProxy {

    @GetMapping("autocomplete-search/?apiKey=WRck00tqIA09ecOp6o4iF2pLnXkAF3p3L0aCRgT7&search_value={name}&search_type=1")
    ResultFilmDto searchFilmsByName(@PathVariable String name);
}
