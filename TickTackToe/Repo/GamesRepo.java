package TickTackToe.Repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import TickTackToe.Entity.Games;
import java.time.LocalDateTime;

public class GamesRepo {
    private static final String URL = "jdbc:mysql://localhost:3306/TicTacToe";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void addGame(Games game) throws SQLException {
        String query = "INSERT INTO Games (user_id, game_date) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, game.getUserId());
            stmt.setObject(2, game.getGameDate());
            stmt.executeUpdate();
        }
    }

    public static ResultSet getTopGames() throws SQLException {
        String query = "SELECT g.game_id, g.user_id, g.game_date, s.score " +
                       "FROM Games g " +
                       "JOIN Scores s ON g.game_id = s.game_id " +
                       "ORDER BY s.score DESC " +
                       "LIMIT 10";
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        return stmt.executeQuery();
    }
    }
