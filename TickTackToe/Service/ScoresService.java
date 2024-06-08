package TickTackToe.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import TickTackToe.Entity.Scores;
import TickTackToe.Repo.ScoresRepo;

public class ScoresService {
    public static void addScore(int gameId, int userId, int score) {
        try {
            Scores newScore = new Scores(0, gameId, userId, score);
            ScoresRepo.addScore(newScore);
            System.out.println("Score toegevoegd!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Toevoegen van score mislukt. Probeer het opnieuw.");
        }
    }

public static void displayTopScores() {
        System.out.println("\n--- Top 10 Scores ---");
        try {
            ResultSet topScores = ScoresRepo.getTopScores();
            int rank = 1;
            while (topScores.next()) {
                String username = topScores.getString("username");
                int score = topScores.getInt("score");
                System.out.println(rank + ". " + username + ": " + score);
                rank++;
            }
        } catch (SQLException e) {
            System.out.println("Fout bij het ophalen van top 10 scores: " + e.getMessage());
        }
    }
}
