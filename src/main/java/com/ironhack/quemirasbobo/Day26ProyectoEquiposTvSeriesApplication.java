package com.ironhack.quemirasbobo;

import com.ironhack.quemirasbobo.service.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@RequiredArgsConstructor
public class Day26ProyectoEquiposTvSeriesApplication implements CommandLineRunner {

	private final Menu menu;

	public static void main(String[] args) {
		SpringApplication.run(Day26ProyectoEquiposTvSeriesApplication.class, args);
	}

	@Override
	public void run(String... args) {
		menu.run();
	}
}
