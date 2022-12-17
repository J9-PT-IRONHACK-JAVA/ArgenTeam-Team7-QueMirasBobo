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

import java.util.*;

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
        String chau = "";

        utils.clearConsole();

        Optional<User> user = LoginSignUpMenu(scanner);
        while (!chau.equals("4") && user.isPresent()) {
            chau = userMenu(scanner, user.get()); // -3
            if (chau.equals("3")) {
                user = LoginSignUpMenu(scanner);
            }
        }
        utils.clearConsole();
        PrintUtils.goodBye();
        System.exit(0);
    }

    public String userMenu(Scanner scanner, User user) {
        String option ="";

        while(!option.equals("3")){
            utils.clearConsole();
            System.out.println("************************");
            System.out.println(user.getName() + " personal workspace");
            System.out.println("************************");
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
                    showAllWatchedFilms(scanner, user);
                    break;
                }
                case "3":{
                    System.out.println("Goodbye "+user.getName()+"!");
                    utils.pause(2000);
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
        return option;
    }

    private void searchOption(Scanner scanner, User user) {
        utils.clearConsole();
        System.out.println("Insert name of film to search: ");
        var film = scanner.nextLine();
        var result = filmProxy.searchFilmsByName(film);

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
            List<PlatformDto> platforms = new ArrayList<PlatformDto>();
            try {
                platforms = platformProxy.getAllPlatformsFromFilmId(filmToGetPlatforms.getId());
            }catch (Exception e){
                platforms.add(new PlatformDto("No Platforms"));
            }
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
            System.out.println("Do you want to see the movie? (Y/N):");
            var option = scanner.nextLine().toUpperCase();

            while (!option.equals ("Y") && !option.equals ("N")){
                System.out.println("You must write 'Y' or 'N'");
                option = scanner.nextLine().toUpperCase();
            }

            if (option.equals("Y")){

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

                var filmFromDatabase = filmRepository.findFilmByName(filmToSave.getName());
                if(filmFromDatabase.isPresent()) {
                    if(Objects.equals(filmFromDatabase.get().getUser().getId(), user.getId())) {
                        filmToSave = filmFromDatabase.get();
                    }
                }

                List<Platform> finalPlatforms = new ArrayList<>();
                for (int i = 0; i < platforms.size(); i++) {
                    var platformToSave = new Platform(platforms.get(i).getName());
                    var platformOptional = platformRepository.findPlatformByName(platforms.get(i).getName());
                    if(platformOptional.isPresent()){
                        platformOptional.get().setFilms(List.of(filmToSave));
                        var lastPlatform = platformRepository.save(platformOptional.get());
                        finalPlatforms.add(lastPlatform);
                    }else{
                        platformToSave = new Platform(platforms.get(i).getName());
                        platformToSave.setFilms(List.of(filmToSave));
                        platformToSave = platformRepository.save(platformToSave);
                        finalPlatforms.add(platformToSave);
                    }

                }
                filmToSave.setPlatforms(finalPlatforms);
                filmToSave.setUser(user);
                filmRepository.save(filmToSave);
                System.out.println("Film Watched and saved!");
                utils.pause(2000);

            }else{
                System.out.println("Film not Watched...");
                utils.pause(2000);
            }

        }
    }

    private void showAllWatchedFilms(Scanner scanner, User user) {
        utils.clearConsole();
        System.out.println("************************");
        System.out.println(user.getName() + " watched films");
        System.out.println("************************");
        var allFilms = filmService.findFilms(user);
        if (allFilms.size() > 0) {
            for (int i = 0; i < allFilms.size(); i++) {
                System.out.printf("%d - %s\n", i + 1, allFilms.get(i).getName());
            }
            System.out.println("Choose one to see in which platform you can watch it again");
            var option = scanner.nextLine();
            try {
                if (Integer.parseInt(option) > allFilms.size() || Integer.parseInt(option) < 1) {
                    System.err.println("This is not an option! Try again");
                    utils.pause(2000);
                } else {
                    var movie = allFilms.get(Integer.parseInt(option) - 1);
                    var listPlatforms = platformService.findPlatforms(movie);
                    for (int i = 0; i < listPlatforms.size(); i++) {
                        System.out.printf("%d - %s\n", i + 1, listPlatforms.get(i).getName());
                    }
                    utils.promptEnterKey();
                }
            } catch (NumberFormatException e) {
                    System.err.println("This is not a number. Returning to main menu...");
                    utils.pause(2000);
            }
        } else {
            System.out.println("You haven't watched any film yet! Returning to main menu...");
            utils.pause(4000);
        }
    }

    private Optional<User> LoginSignUpMenu(Scanner scanner) {
        String choseLog = "";
        Optional<User> user = Optional.empty();
        while (!choseLog.equals("3") && user.isEmpty()) {
            utils.clearConsole();
            System.out.println("Welcome to QUEMIRASBOBOSOFT!");
            System.out.println("""
                1) Login
                2) Sign up
                3) Exit
                """);
            choseLog = scanner.nextLine();
            utils.clearConsole();
            switch (choseLog) {
                case "1" -> user = loginMenu(scanner);
                case "2" -> signUpMenu(scanner);
                case "3" -> {
                    user = Optional.empty();
                }
                default -> System.err.println("This option does not exist, please try again");
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
        //TODO
        // ver si existe un usuario con el mismo username
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
