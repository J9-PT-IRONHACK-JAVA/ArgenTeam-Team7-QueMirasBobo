package com.ironhack.quemirasbobo;

import com.ironhack.quemirasbobo.model.User;
import com.ironhack.quemirasbobo.proxy.FilmProxy;
import com.ironhack.quemirasbobo.proxy.PlatformProxy;
import com.ironhack.quemirasbobo.repository.UserRepository;
import com.ironhack.quemirasbobo.service.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Scanner;

@SpringBootApplication
@EnableFeignClients
@RequiredArgsConstructor
public class Day26ProyectoEquiposTvSeriesApplication implements CommandLineRunner {

	private final Menu menu;

	private final FilmProxy filmProxy;

	private final PlatformProxy platformProxy;

	private final UserRepository userRepository;

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
		var scanner = new Scanner(System.in);
		var user1 = new User("Juan", "juan", "pass");
		userRepository.save(user1);
		menu.userMenu(scanner,user1);
		//menu.run();


	}
}
