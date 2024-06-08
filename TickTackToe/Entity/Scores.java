package TickTackToe.Entity;

public class Scores {
    private int scoreId;
    private int gameId;
    private int userId;
    private int score;

    // Constructor, getters en setters
    public Scores(int scoreId, int gameId, int userId, int score) {
        this.scoreId = scoreId;
        this.gameId = gameId;
        this.userId = userId;
        this.score = score;
    }

    public int getScoreId() {
        return scoreId;
    }

    public int getGameId() {
        return gameId;
    }

    public int getUserId() {
        return userId;
    }

    public int getScore() {
        return score;
    }
}
