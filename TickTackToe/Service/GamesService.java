package TickTackToe.Service;

import java.sql.SQLException;
import TickTackToe.Entity.Games;
import TickTackToe.Repo.GamesRepo;
import java.time.LocalDateTime;

public class GamesService {
    public static void addGame(int userId, LocalDateTime gameDate) {
        try {
            Games newGame = new Games(0, userId, gameDate);
            GamesRepo.addGame(newGame);
            System.out.println("Spel toegevoegd!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Toevoegen van spel mislukt. Probeer het opnieuw.");
        }
    }

    // Je kunt hier andere methoden toevoegen, zoals het ophalen van games per gebruiker, enzovoort.
}
