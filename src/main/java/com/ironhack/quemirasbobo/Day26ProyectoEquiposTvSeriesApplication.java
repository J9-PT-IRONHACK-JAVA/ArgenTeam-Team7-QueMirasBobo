package com.ironhack.quemirasbobo;

import com.ironhack.quemirasbobo.proxy.FilmProxy;
import com.ironhack.quemirasbobo.proxy.PlatformProxy;
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

	private final FilmProxy filmProxy;

	private final PlatformProxy platformProxy;


	public static void main(String[] args) {
		SpringApplication.run(Day26ProyectoEquiposTvSeriesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		//var result = filmProxy.searchFilmsByName("simpsons");
		//System.out.println(result);


		//var platforms = platformProxy.getAllPlatformsFromFilmId(3173903L);
		//for (int i = 0; i < platforms.size(); i++) {
		//	System.out.println(platforms.get(i).getName());
		//}
		menu.run();

	}
}
