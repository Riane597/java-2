package TickTackToe.Service;

import java.sql.SQLException;
import TickTackToe.Entity.Auditlog;
import TickTackToe.Entity.Users;
import TickTackToe.Repo.AuditlogRepo;
import java.time.LocalDateTime;
import java.util.List;

public class AuditlogService {
    public static void addLog(int userId, String action, LocalDateTime actionDate) {
        try {
            Auditlog newLog = new Auditlog(0, userId, action, actionDate);
            AuditlogRepo.addLog(newLog);
            System.out.println("Log toegevoegd!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Toevoegen van log mislukt. Probeer het opnieuw.");
        }
    }

    public static void logAction(int userId, String action) {
        LocalDateTime actionDate = LocalDateTime.now();
        Auditlog newLog = new Auditlog(0, userId, action, actionDate);
        try {
            AuditlogRepo.addLog(newLog);
            System.out.println("Actie gelogd!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Loggen van actie mislukt. Probeer het opnieuw.");
        }
    }

    public static void displayAuditLogs() {
        try {
            List<String> logs = AuditlogRepo.getAllLogs();
            System.out.println("--- Audit Logs ---");
            for (String log : logs) {
                System.out.println(log);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ophalen van auditlogs mislukt. Probeer het opnieuw.");
        }
    }

        public static void displayAllLogs(Users currentUser) {
        if (currentUser != null && currentUser.getUsername().equals("aria")) {
            try {
                List<String> logs = AuditlogRepo.getAllLogs();
                System.out.println("--- All Logs ---");
                for (String log : logs) {
                    System.out.println(log);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Ophalen van logs mislukt. Probeer het opnieuw.");
            }
        } else {
            System.out.println("Toegang geweigerd. Alleen de admin kan alle logs bekijken.");
        }
    }
    
}
