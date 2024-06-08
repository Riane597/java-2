package TickTackToe.Entity;

import java.time.LocalDate;

public class Users {
    private int userId;
    private String username;
    private String password;
    private LocalDate birthDate;
    private int gamesPlayed;

    public Users(int userId, String username, String password, LocalDate birthDate, int gamesPlayed) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.birthDate = birthDate;
        this.gamesPlayed = gamesPlayed;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }
}
