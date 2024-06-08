package TickTackToe.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;
import TickTackToe.Entity.Users;
import TickTackToe.Repo.UserRepo;

public class UsersService {
    private static final Scanner scanner = new Scanner(System.in);

    public static void registerUser(String username, String password, LocalDate birthDate) {
        try {
            Users newUser = new Users(0, username, password, birthDate, 0);
            UserRepo.createUser(newUser);
            System.out.println("Registratie succesvol!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Registratie mislukt. Probeer het opnieuw.");
        }
    }

    public static Users loginUser(String username, String password) {
        try {
            Users user = UserRepo.getUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                System.out.println("Inloggen succesvol!");
                return user;
            } else {
                System.out.println("Onjuiste gebruikersnaam of wachtwoord.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Inloggen mislukt. Probeer het opnieuw.");
        }
        return null;
    }

    public static Users registerAndLoginPlayer(int playerNumber, String username, String password, LocalDate birthDate) {
        System.out.println("Speler " + playerNumber + " - Registratie of Inloggen:");
        // Probeer de gebruiker in te loggen
        Users user = loginUser(username, password);
        if (user != null) {
            System.out.println("Inloggen succesvol voor " + user.getUsername());
            return user; // Gebruiker bestaat al, geef de gebruiker terug
        } else {
            // Gebruiker niet gevonden, registreer de gebruiker
            System.out.println("Gebruiker niet gevonden. Registratie vereist.");
            registerUser(username, password, birthDate);
            System.out.println("Registratie succesvol voor " + username);
            
            // Probeer opnieuw in te loggen na registratie
            return loginUser(username, password);
        }
    }
    
}
