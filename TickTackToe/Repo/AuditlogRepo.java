package TickTackToe.Repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import TickTackToe.Entity.Auditlog;

public class AuditlogRepo {
    private static final String URL = "jdbc:mysql://localhost:3306/TicTacToe";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void addLog(Auditlog log) throws SQLException {
        String query = "INSERT INTO Auditlog (user_id, action, action_date) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, log.getUserId());
            stmt.setString(2, log.getAction());
            stmt.setObject(3, log.getActionDate());
            stmt.executeUpdate();
        }
    }

    public static List<String> getAllLogs() throws SQLException {
        List<String> logs = new ArrayList<>();
        String query = "SELECT log_details FROM Auditlog";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                logs.add(rs.getString("log_details"));
            }
        }
        return logs;
    }
}