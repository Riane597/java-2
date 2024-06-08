package TickTackToe.Repo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import TickTackToe.JDBC;
import TickTackToe.Entity.Users;

public class UserRepo {
    public static void createUser(Users user) throws SQLException {
        String query = "INSERT INTO Users (username, password, birth_date) VALUES (?, ?, ?)";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setDate(3, java.sql.Date.valueOf(user.getBirthDate()));
            stmt.executeUpdate();
        }
    }

    public static Users getUserByUsername(String username) throws SQLException {
        String query = "SELECT * FROM Users WHERE username = ?";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Users(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getDate("birth_date").toLocalDate(),
                        rs.getInt("games_played")
                );
            }
        }
        return null;
    }
}

    // Voeg andere methoden toe voor gebruikersinteractie
