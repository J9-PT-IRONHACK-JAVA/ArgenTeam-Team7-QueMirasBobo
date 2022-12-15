package com.ironhack.quemirasbobo.service;

import com.ironhack.quemirasbobo.model.User;
import com.ironhack.quemirasbobo.proxy.FilmProxy;
import com.ironhack.quemirasbobo.proxy.PlatformProxy;
import com.ironhack.quemirasbobo.repository.UserRepository;
import com.ironhack.quemirasbobo.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
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
        //userMenu(scanner);

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

    public void userMenu(Scanner scanner, User user) {
        String option ="";

        while(!option.equals("3")){
            // TODO : Clear Screen
            System.out.println("********************");
            System.out.println(user.getName() + " workspace");
            System.out.println("********************");
            System.out.println("Select option:");
            System.out.println("    1) Search Film.");
            System.out.println("    2) See all Watched Films.");
            System.out.println("    3) Logout.");
            System.out.println("    4) Chau! (shutdown)");

            option = scanner.nextLine();

            switch (option){
                case "1":{
                    System.out.println("search film menu");
                    break;
                }
                case "2":{
                    System.out.println("see all watched films");
                    break;
                }
                case "3":{
                    System.out.println("Goodbye "+user.getName()+"!");
                    break;
                }
                case "4":{
                    //TODO: Logo cara messi ascii + "andapayá bobo"
                    System.out.println("Goodbye!");
                    System.exit(0);
                    break;
                }
                default:{
                    System.out.println("Please insert a valid option!");
                    Utils.pause(1500);
                }
            }

        }
        System.out.println("Insert name of film to search: ");
        var film = scanner.nextLine();

        var result = filmProxy.searchFilmsByName(film);
        //TODO: Ver si el size es 0, decirle que busque otro nombre
        //TODO: Ponerle un maximo de 15 resultados,y "enter" para proxima linea
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
        //TODO: Catchear que aca pongan un numero si osi sino peta
        var filmToGetPlatforms = result.getResults().get(Integer.parseInt(number)-1);
        //TODO: Si la lista es vacia, que le ponga un "no platforms"
        var platforms = platformProxy.getAllPlatformsFromFilmId(filmToGetPlatforms.getId());
        //TODO: Que elimine duplicados de plataformas
        for (int i = 0; i < platforms.size(); i++) {
            System.out.println(platforms.get(i).getName());
        }

        //TODO: Que pregunte si "desea ver la pelicula"?
        // Si la quiere ver, que lo guarde en la DB, sino, que vuelva al inicio del usuario
        // En la DB tiene que guardar LA PELICULA y LAS PLATAFORMAS

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
