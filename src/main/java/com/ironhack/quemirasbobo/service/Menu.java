package com.ironhack.quemirasbobo.service;

import com.ironhack.quemirasbobo.model.User;
import com.ironhack.quemirasbobo.proxy.FilmProxy;
import com.ironhack.quemirasbobo.proxy.PlatformProxy;
import com.ironhack.quemirasbobo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class Menu {

    private final UserRepository userRepository;


    private final FilmProxy filmProxy;


    private final PlatformProxy platformProxy;



    public void run(){
        var scanner = new Scanner(System.in);

        System.out.println("Welcome!");
        userMenu(scanner);

        /*
        System.out.println("Select LOGIN or CREATE user: ");
        var option = scanner.nextLine();

        switch (option.toLowerCase()) {
            case "login": {
                var login = loginMenu(scanner);
                if (login){
                    System.out.println("user logged!");
                }else{
                    System.out.println("error at login");
                }
                break;
            }
            case "create": {
                userCreate(scanner);
                break;
            }
            default: {
                System.out.println("Option Error");
            }
        }
        var login = loginMenu(scanner);
        if (login){
            userMenu(scanner);
        }else{
            System.out.println("error at login");
        }




         */



    }

    private void userMenu(Scanner scanner) {
        System.out.println("Welcome to your menu!");
        System.out.println("insert tv show to search:");
        var film = scanner.nextLine();

        var result = filmProxy.searchFilmsByName(film);
        for (int i = 0; i < result.getResults().size(); i++) {
            var filmDto = result.getResults().get(i);
            System.out.println((i+1)+ ") "+
                    filmDto.getName()+ ", Year: "+
                    filmDto.getYear()+ ", Type: "+
                    filmDto.getType()
            );
        }
        System.out.println("Select Nº:");
        var number = scanner.nextLine();

        var filmToGetPlatforms = result.getResults().get(Integer.parseInt(number)-1);

        var platforms = platformProxy.getAllPlatformsFromFilmId(filmToGetPlatforms.getId());

        for (int i = 0; i < platforms.size(); i++) {
            System.out.println(platforms.get(i).getName());
        }

        System.out.println("FIN");
    }

    private Boolean loginMenu(Scanner scanner) {
        System.out.println("Insert username:");
        var username = scanner.nextLine();
        System.out.println("Insert password:");
        var password = scanner.nextLine();

        var user = userRepository.findUserByUsername(username);

        return user.getPassword().equals(password);

    }

    private void userCreate(Scanner scanner) {
        System.out.println("Insert NAME:");
        var name = scanner.nextLine();
        System.out.println("Insert USERNAME:");
        var username = scanner.nextLine();
        System.out.println("Insert PASSWORD:");
        var password = scanner.nextLine();

        var user = new User(name,username,password);

        userRepository.save(user);

        System.out.println("User "+username+" created!");
    }


}
