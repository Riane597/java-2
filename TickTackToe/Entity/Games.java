package TickTackToe.Entity;

import java.time.LocalDateTime;

public class Games {
    private int gameId;
    private int userId;
    private LocalDateTime gameDate;

    public Games(int gameId, int userId, LocalDateTime gameDate) {
        this.gameId = gameId;
        this.userId = userId;
        this.gameDate = gameDate;
    }

    public int getGameId() {
        return gameId;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDateTime getGameDate() {
        return gameDate;
    }
}
