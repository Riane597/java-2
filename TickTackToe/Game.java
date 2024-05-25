package TickTackToe;

import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Game {
    private static final Scanner scanner = new Scanner(System.in);
    private static final char[] SYMBOLS = {'X', 'O'};
    private static char[][] board = new char[3][3];
    private static int currentUserId = -1;
    private static String currentUsername;

    public static void main(String[] args) {
        while (true) {
            System.out.println("Welkom bij Tic Tac Toe!");
            System.out.println("1. Aanmelden");
            System.out.println("2. Inloggen");
            System.out.println("3. Top 10 scores bekijken");
            System.out.println("4. Afsluiten");

            int keuze = Integer.parseInt(scanner.nextLine());
            switch (keuze) {
                case 1 -> aanmelden();
                case 2 -> inloggen();
                case 3 -> toonTopScores();
                case 4 -> System.exit(0);
                default -> System.out.println("Ongeldige keuze, probeer opnieuw.");
            }

            if (currentUserId != -1) {
                startSpel();
            }
        }
    }

    private static void aanmelden() {
        System.out.print("Gebruikersnaam: ");
        String username = scanner.nextLine();
        System.out.print("Wachtwoord: ");
        String password = scanner.nextLine();
        System.out.print("Geboortedatum (YYYY-MM-DD): ");
        String birthDate = scanner.nextLine();
        try {
            DbConn.createUser(username, password, birthDate);
            System.out.println("Aanmelding succesvol! U kunt nu inloggen.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Aanmelding mislukt. Probeer het opnieuw.");
        }
    }
    private static void inloggen() {
        System.out.print("Gebruikersnaam: ");
        String username = scanner.nextLine();
        System.out.print("Wachtwoord: ");
        String password = scanner.nextLine();
        try {
            if (DbConn.userExists(username, password)) {
                currentUserId = DbConn.getUserId(username);
                currentUsername = username;
                System.out.println("Inloggen succesvol!");
            } else {
                System.out.println("Ongeldige gebruikersnaam of wachtwoord. Probeer het opnieuw.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Inloggen mislukt. Probeer het opnieuw.");
        }
    }

    private static void toonTopScores() {
        try {
            ResultSet rs = DbConn.getTopScores();
            System.out.println("Top 10 Scores:");
            while (rs.next()) {
                System.out.println(rs.getString("username") + ": " + rs.getInt("score"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Kon de scores niet ophalen.");
        }
    }

    private static void startSpel() {
        resetBord();
        int beurt = 0;
        boolean spelAfgelopen = false;
        while (!spelAfgelopen) {
            printBord();
            System.out.println("Speler " + SYMBOLS[beurt % 2] + " is aan de beurt.");
            System.out.print("Kies een rij (1-3): ");
            int rij = Integer.parseInt(scanner.nextLine()) - 1;
            System.out.print("Kies een kolom (1-3): ");
            int kolom = Integer.parseInt(scanner.nextLine()) - 1;
            if (rij < 0 || rij >= 3 || kolom < 0 || kolom >= 3 || board[rij][kolom] != ' ') {
                System.out.println("Ongeldige zet, probeer opnieuw.");
            } else {
                board[rij][kolom] = SYMBOLS[beurt % 2];
                beurt++;
                if (checkWin(SYMBOLS[(beurt - 1) % 2])) {
                    printBord();
                    System.out.println("Speler " + SYMBOLS[(beurt - 1) % 2] + " wint!");
                    updateScoreInDatabase(10);  // Voeg 10 punten toe voor een overwinning
                    spelAfgelopen = true;
                } else if (beurt == 9) {
                    printBord();
                    System.out.println("Het is een gelijkspel!");
                    spelAfgelopen = true;
                }
            }
        }
        try {
            DbConn.incrementGamesPlayed(currentUserId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        currentUserId = -1;  // Log out the user after the game
    }

    private static void resetBord() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    private static void printBord() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j]);
                if (j < 2) System.out.print("|");
            }
            System.out.println();
            if (i < 2) System.out.println("-----");
        }
    }

    private static boolean checkWin(char symbool) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == symbool && board[i][1] == symbool && board[i][2] == symbool)
                return true;
            if (board[0][i] == symbool && board[1][i] == symbool && board[2][i] == symbool)
                return true;
        }
        if (board[0][0] == symbool && board[1][1] == symbool && board[2][2] == symbool)
            return true;
        if (board[0][2] == symbool && board[1][1] == symbool && board[2][0] == symbool)
            return true;
        return false;
    }

    private static void updateScoreInDatabase(int score) {
        try {
            DbConn.updateScore(currentUserId, score);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Kon de score niet bijwerken.");
        }
    }
}
