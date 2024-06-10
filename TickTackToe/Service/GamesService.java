package TickTackToe.Service;

import java.sql.ResultSet;
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

    public static void displayTopGames() {
        System.out.println("\n--- Top 10 Games ---");
        ResultSet topGames = null;
        try {
            topGames = GamesRepo.getTopGames();
            int rank = 1;
            while (topGames.next()) {
                int gameId = topGames.getInt("game_id");
                int userId = topGames.getInt("user_id");
                LocalDateTime gameDate = topGames.getObject("game_date", LocalDateTime.class);
                int score = topGames.getInt("score");
                System.out.println(rank + ". Game ID: " + gameId + ", User ID: " + userId + 
                                   ", Date: " + gameDate + ", Score: " + score);
                rank++;
            }
        } catch (SQLException e) {
            System.out.println("Fout bij het ophalen van top 10 games: " + e.getMessage());
        } finally {
            if (topGames != null) {
                try {
                    topGames.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
