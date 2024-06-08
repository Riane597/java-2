package TickTackToe.Entity;

import java.time.LocalDateTime;

public class Auditlog {
    private int logId;
    private int userId;
    private String action;
    private LocalDateTime actionDate;

    public Auditlog(int logId, int userId, String action, LocalDateTime actionDate) {
        this.logId = logId;
        this.userId = userId;
        this.action = action;
        this.actionDate = actionDate;
    }

    public int getLogId() {
        return logId;
    }

    public int getUserId() {
        return userId;
    }

    public String getAction() {
        return action;
    }

    public LocalDateTime getActionDate() {
        return actionDate;
    }
}
