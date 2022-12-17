package com.ironhack.quemirasbobo.service;

import com.ironhack.quemirasbobo.dto.PlatformDto;
import com.ironhack.quemirasbobo.model.Film;
import com.ironhack.quemirasbobo.model.Platform;
import com.ironhack.quemirasbobo.model.Type;
import com.ironhack.quemirasbobo.model.User;
import com.ironhack.quemirasbobo.proxy.FilmProxy;
import com.ironhack.quemirasbobo.proxy.PlatformProxy;
import com.ironhack.quemirasbobo.repository.FilmRepository;
import com.ironhack.quemirasbobo.repository.PlatformRepository;
import com.ironhack.quemirasbobo.repository.UserRepository;
import com.ironhack.quemirasbobo.utils.PrintUtils;
import com.ironhack.quemirasbobo.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class Menu {

    private final FilmProxy filmProxy;
    private final PlatformProxy platformProxy;
    private final Utils utils;
    private final UserRepository userRepository;
    private final FilmRepository filmRepository;
    private final FilmService filmService;
    private final UserService userService;
    private final PlatformRepository platformRepository;
    private final PlatformService platformService;

    public void run(){
        var scanner = new Scanner(System.in);

        System.out.println("Welcome!");
        var user = LoginSignUpMenu(scanner);
        if (user.isPresent())
            userMenu(scanner, user.get());
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
            utils.clearConsole();
            System.out.println("********************");
            System.out.println(user.getName() + " personal workspace");
            System.out.println("********************");
            System.out.println("Select option:");
            System.out.println("    1) Search Film.");
            System.out.println("    2) See all Watched Films.");
            System.out.println("    3) Logout.");
            System.out.println("    4) Chau! (shutdown)");

            option = scanner.nextLine();

            switch (option){
                case "1":{
                    searchOption(scanner, user);
                    break;
                }
                case "2":{
                    // TODO Las films que se guardan para cada user
                    showAllWatchedFilms(scanner, user);
                    System.out.println("see all watched films");
                    break;
                }
                case "3":{
                    System.out.println("Goodbye "+user.getName()+"!");
                    break;
                }
                case "4":{
                    PrintUtils.goodBye();
                    System.exit(0);
                }
                default:{
                    System.out.println("\nPlease insert a valid option!");
                    utils.pause(1500);
                }
            }
        }
    }

    private void searchOption(Scanner scanner, User user) {
        System.out.println("Insert name of film to search: ");
        var film = scanner.nextLine();
        var result = filmProxy.searchFilmsByName(film);

        //TODO: Ver si el size es 0, decirle que busque otro nombre
        if(result.getResults().size() == 0){
            System.out.println("Your search did not return any results, please try again...");
            utils.pause(1500);
        }else {
            int resultsPerPage = 15;
            int actualResultCount = 0;

            for (int i = 0; i < result.getResults().size(); i++) {
                var filmDto = result.getResults().get(i);
                actualResultCount++;
                System.out.println((i + 1) + ") " +
                        filmDto.getName() + ", Year: " +
                        filmDto.getYear() + ", Type: " +
                        filmDto.getType()
                );
                // Detiene a los 15
                if (actualResultCount == resultsPerPage) {
                    actualResultCount = 0;
                    utils.promptEnterKey();
                }
            }

            System.out.println("\nSelect Nº:");
            boolean flag = true;
            int number = 0;
            var op = "";
            do {
                try {
                    op = scanner.nextLine();
                    number = Integer.parseInt(op);
                    if(number<=0 || number >result.getResults().size()){
                        System.out.println("Error! Put a number from the list...");
                    }else {
                        flag = false;
                    }
                } catch (Exception ignored) {
                    System.out.println("Error! Please insert only numbers...");
                }
            } while (flag);

            var filmToGetPlatforms = result.getResults().get(number - 1);

            var platforms = platformProxy.getAllPlatformsFromFilmId(filmToGetPlatforms.getId());
            if (platforms.size() == 0){
                System.out.println("The film does not have platforms...");
                platforms.add(new PlatformDto("No Platforms"));
            }else {
                platforms = utils.deleteDuplicate(platforms);
                System.out.println("The film have the next platforms:");
                for (int i = 0; i < platforms.size(); i++) {
                    System.out.println(platforms.get(i).getName());
                }
            }
            System.out.println("Do you want to see the movie? (Y/N");
            var option = scanner.nextLine().toUpperCase();

            while (!option.equals ("Y") && !option.equals ("N")){
                System.out.println("You must write 'Y' or 'N'");
                option = scanner.nextLine().toUpperCase();
            }

            if (option.equals("Y")){
                System.out.println("guardar peli");

                Type filmType;
                switch (filmToGetPlatforms.getType().toLowerCase()){
                    //MOVIE, TV_SERIES, TV_MOVIE, SHORT_FILM,
                    case "movie":{
                        filmType = Type.MOVIE;
                    }
                    case "tv_series":{
                        filmType = Type.TV_SERIES;
                    }
                    case "tv_movie":{
                        filmType = Type.TV_MOVIE;
                    }
                    case "short_film":{
                        filmType = Type.SHORT_FILM;
                    }
                    default:{
                        filmType = Type.OTHER;
                    }
                }
                var filmToSave = new Film(filmToGetPlatforms.getName(),
                        filmType,
                        filmToGetPlatforms.getYear()
                        );
                filmToSave.setUser(user);
                filmToSave = filmRepository.save(filmToSave);

                //var platformToSave = new Platform()
                // TODO, seguir esto!



            }else{
                System.out.println("no guardar");
            }




            //TODO: Que pregunte si "desea ver la pelicula"?
            // Si la quiere ver, que lo guarde en la DB, sino, que vuelva al inicio del usuario
            // En la DB tiene que guardar LA PELICULA y LAS PLATAFORMAS
        }
    }

    private void showAllWatchedFilms(Scanner scanner, User user) {
        var allFilms = filmService.findFilms(user);
        for (int i = 0; i < allFilms.size(); i++) {
            System.out.printf("%d - %s\n", i + 1, allFilms.get(i).getName());
        }
        System.out.println("Choose one to see in which platform you can watch it again");
        var option = scanner.nextLine();
        var movie = allFilms.get(Integer.parseInt(option) - 1);
        var listPlatforms = platformService.findPlatforms(movie);
        for (int i = 0; i < listPlatforms.size(); i++) {
            System.out.printf("%d - %s\n", i + 1, listPlatforms.get(i).getName());
        }
    }

    private Optional<User> LoginSignUpMenu(Scanner scanner) {
        String choseLog;
        Optional<User> user = Optional.empty();
        while (user.isEmpty()) {
            System.out.println("""
                1) Login
                2) Sign up
                3) Exit
                """);
            choseLog = scanner.nextLine();
            switch (choseLog) {
                case "1" -> user = loginMenu(scanner);
                case "2" -> signUpMenu(scanner);
                case "3" -> {
                    System.out.println("Chau!");
                    System.exit(0);
                }
                default -> System.out.println("This option does not exist, please try again");
            }
        }
        return user;
    }
    // How can I test without running?
    private Optional<User> loginMenu(Scanner scanner) {
        Optional<User> user;
        boolean valid = false;
        String exit = null;
        do {
            System.out.println("Insert username:");
            var username = scanner.nextLine();
            System.out.println("Insert password:");
            var password = scanner.nextLine();
            user = userService.findUserByUsername(username);
            if (user.isPresent())
                valid = user.get().getPassword().equals(password);
            if (!valid) {
                System.err.println("""
                        Invalid credentials, what do you want to do?
                        1) Try again
                        2) Exit
                        """);
                exit = scanner.nextLine();
            }
        } while ((!valid && exit.equals("1")));
        if (valid) return user;
        else return Optional.empty();
    }

    private void signUpMenu(Scanner scanner) {
        System.out.println("Insert NAME:");
        var name = scanner.nextLine();
        System.out.println("Insert USERNAME:");
        var username = scanner.nextLine();
        System.out.println("Insert PASSWORD:");
        var password = scanner.nextLine();

        var user = userService.saveUser(new User(name,username,password));

        System.out.println("User " + username + " created!");
    }


}
