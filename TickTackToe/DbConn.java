package TickTackToe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbConn {

    private static final String URL = "jdbc:mysql://localhost:3306/TicTacToeDB";
    private static final String USER = "root"; // verander naar jouw MySQL gebruikersnaam
    private static final String PASSWORD = ""; // verander naar jouw MySQL wachtwoord

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static boolean userExists(String username, String password) throws SQLException {
        String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public static void createUser(String username, String password, String birthDate) throws SQLException {
        String query = "INSERT INTO Users (username, password, birth_date) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, birthDate);
            stmt.executeUpdate();
        }
    }

    public static void updateScore(int userId, int score) throws SQLException {
        String query = "INSERT INTO Scores (user_id, score) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, score);
            stmt.executeUpdate();
        }
    }

    public static void incrementGamesPlayed(int userId) throws SQLException {
        String query = "UPDATE Users SET games_played = games_played + 1 WHERE user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }

    public static ResultSet getTopScores() throws SQLException {
        String query = "SELECT u.username, s.score FROM Scores s JOIN Users u ON s.user_id = u.user_id ORDER BY s.score DESC LIMIT 10";
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        return stmt.executeQuery();
    }

    public static int getUserId(String username) throws SQLException {
        String query = "SELECT user_id FROM Users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("user_id");
            } else {
                return -1;
            }
        }
    }
}
