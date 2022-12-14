package com.ironhack.quemirasbobo;

import com.ironhack.quemirasbobo.proxy.FilmProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@RequiredArgsConstructor
public class Day26ProyectoEquiposTvSeriesApplication implements CommandLineRunner {

	private final FilmProxy filmProxy;
	public static void main(String[] args) {
		SpringApplication.run(Day26ProyectoEquiposTvSeriesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		var result = filmProxy.searchFilmsByName("simpsons");
		System.out.println(result);

	}
}
