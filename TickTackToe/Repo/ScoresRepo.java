package TickTackToe.Repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import TickTackToe.JDBC;
import TickTackToe.Entity.Scores;

public class ScoresRepo {
    private static final String URL = "jdbc:mysql://localhost:3306/TicTacToe";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void addScore(Scores score) throws SQLException {
        String query = "INSERT INTO Scores (game_id, user_id, score) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, score.getGameId());
            stmt.setInt(2, score.getUserId());
            stmt.setInt(3, score.getScore());
            stmt.executeUpdate();
        }
    }

 public static ResultSet getTopScores() throws SQLException {
        String query = "SELECT u.username, s.score FROM Scores s JOIN Users u ON s.user_id = u.user_id ORDER BY s.score DESC LIMIT 10";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = JDBC.getConnection();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            return rs;
        } catch (SQLException e) {
            // Handle any errors
            e.printStackTrace();
            throw e;
        } finally {
            // Close connections in the finally block to ensure they are always closed
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
}
