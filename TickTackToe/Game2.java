package TickTackToe;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import TickTackToe.Entity.Users;
import TickTackToe.Repo.AuditlogRepo;
import TickTackToe.Service.UsersService;
import TickTackToe.Service.GamesService;
import TickTackToe.Service.ScoresService;

public class Game2 {
    private static final Scanner scanner = new Scanner(System.in);
    private static Users player1;
    private static Users player2;
    private static Users currentUser;

    // ANSI escape codes for colors
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_GREEN = null;


    public static final String[] COLORS = {
        ANSI_RED,   // X's background color
        ANSI_GREEN, // O's background color
        ANSI_BLUE,  // Blue
        "\u001B[41m", // Red
        "\u001B[42m", // Green
        "\u001B[43m", // Yellow
        "\u001B[44m", // Blue
        "\u001B[45m", // Magenta
        "\u001B[46m", // Cyan
        "\u001B[47m", // White
        "\u001B[100m", // Bright Black
        "\u001B[101m"  // Bright Red
    };

    public static void main(String[] args) {
        System.out.println("Welkom bij Tic Tac Toe!");

        while (true) {
            System.out.println("\n1. Registreren");
            System.out.println("2. Inloggen");
            System.out.println("3. Spel starten");
            System.out.println("4. Top 10 scores weergeven");
            System.out.println("5. Top 10 games weergeven");
            System.out.println("6. Audit");
            System.out.println("7. Afsluiten");
            System.out.print("Kies een optie: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        registerUser();
                        break;
                    case 2:
                        loginUser();
                        break;
                    case 3:
                        if (player1 != null && player2 != null) {
                            startGame();
                        } else {
                            System.out.println("Er moeten twee spelers zijn ingelogd om het spel te starten.");
                        }
                        break;
                    case 4:
                        ScoresService.displayTopScores();
                        break;
                    case 5:
                        GamesService.displayTopGames();
                        break;
                    case 6:
                        displayAllLogs(currentUser);
                        break;
                    case 7:
                        System.out.println("Bedankt voor het spelen van Tic Tac Toe! Tot ziens.");
                        System.exit(0);
                    default:
                        System.out.println("Ongeldige keuze. Probeer het opnieuw.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Ongeldige invoer. Voer een getal in.");
                scanner.nextLine(); // Clear the invalid input
            } catch (Exception e) {
                System.out.println("Er is een fout opgetreden: " + e.getMessage());
            }
        }
    }

    private static void registerUser() {
        System.out.println("\n--- Registreren ---");
        System.out.print("Gebruikersnaam: ");
        String username = scanner.nextLine();
        System.out.print("Wachtwoord: ");
        String password = scanner.nextLine();
        LocalDate birthDate = LocalDate.now();
        UsersService.registerUser(username, password, birthDate);
    }

    private static void loginUser() {
        System.out.println("\n--- Inloggen ---");
        System.out.print("Gebruikersnaam: ");
        String username = scanner.nextLine();
        System.out.print("Wachtwoord: ");
        String password = scanner.nextLine();

        Users user = UsersService.loginUser(username, password);
        if (user != null) {
            System.out.println("Inloggen succesvol voor " + user.getUsername());
            if (player1 == null) {
                player1 = user;
            } else if (player2 == null) {
                player2 = user;
            }
        } else {
            System.out.println("Onjuiste gebruikersnaam of wachtwoord. Probeer het opnieuw.");
        }
    }

    private static void registerAndLoginPlayers() {
        System.out.println("--- Speler 1 Registratie ---");
        player1 = registerAndLoginPlayer(1);
        System.out.println("--- Speler 2 Registratie ---");
        player2 = registerAndLoginPlayer(2);
    }

    private static Users registerAndLoginPlayer(int playerNumber) {
        System.out.println("Speler " + playerNumber + " - Registratie of Inloggen:");
        System.out.print("Gebruikersnaam: ");
        String username = scanner.nextLine();
        System.out.print("Wachtwoord: ");
        String password = scanner.nextLine();

        Users user = UsersService.loginUser(username, password);
        if (user != null) {
            System.out.println("Inloggen succesvol voor " + user.getUsername());
            return user;
        } else {
            System.out.println("Gebruiker niet gevonden. Registratie vereist.");
            LocalDate birthDate = LocalDate.now();
            UsersService.registerUser(username, password, birthDate);
            System.out.println("Registratie succesvol voor " + username);
            return UsersService.loginUser(username, password);
        }
    }

    private static void displayBoard(char[][] board) {
        System.out.println("Tic Tac Toe Bord:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                char cell = board[i][j];
                String color = COLORS[i * 3 + j]; // Choose color based on cell position

                if (cell == 'X') {
                    System.out.print(color + ANSI_YELLOW + cell + ANSI_RESET);
                } else if (cell == 'O') {
                    System.out.print(color + ANSI_RED + cell + ANSI_RESET);
                } else {
                    System.out.print(color + cell + ANSI_RESET);
                }

                if (j < 2) {
                    System.out.print(" | ");
                }
            }
            System.out.println();
            if (i < 2) {
                System.out.println("---------");
            }
        }
    }

    private static boolean checkWin(char[][] board, char player) {
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) ||
                (board[0][i] == player && board[1][i] == player && board[2][i] == player)) {
                return true;
            }
        }

        if ((board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
            (board[0][2] == player && board[1][1] == player && board[2][0] == player)) {
            return true;
        }

        return false;
    }

    private static void startGame() {
        if (player1 != null && player2 != null) {
            char[][] board = {
                {'1', '2', '3'},
                {'4', '5', '6'},
                {'7', '8', '9'}
            };

            displayBoard(board);

            boolean gameOver = false;
            char currentPlayer = 'X';
            int moves = 0;
            int gameId = 1; // Voor nu hardcoderen we het gameId, in de praktijk zou dit dynamisch moeten zijn

            while (!gameOver && moves < 9) {
                System.out.println("Speler " + currentPlayer + ", kies een positie (1-9):");
                int position = -1;

                try {
                    position = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Ongeldige invoer. Voer een getal in.");
                    scanner.nextLine(); // Clear the invalid input
                    continue;
                }

                if (position < 1 || position > 9) {
                    System.out.println("Ongeldige positie. Probeer opnieuw.");
                    continue;
                }

                int row = (position - 1) / 3;
                int col = (position - 1) % 3;

                if (board[row][col] == 'X' || board[row][col] == 'O') {
                    System.out.println("Deze positie is al ingenomen. Kies een andere.");
                    continue;
                }

                board[row][col] = currentPlayer;

                displayBoard(board);

                if (checkWin(board, currentPlayer)) {
                    System.out.println("Speler " + currentPlayer + " wint!");
                    int winnerId = (currentPlayer == 'X') ? player1.getUserId() : player2.getUserId();
                    int loserId = (currentPlayer == 'X') ? player2.getUserId() : player1.getUserId();

                    // Winner gets 1 point, loser gets 0 points
                    ScoresService.addScore(gameId, winnerId, 1);
                    ScoresService.addScore(gameId, loserId, 0);

                    gameOver = true;
                } else if (moves == 8) {
                    System.out.println("Het is een gelijkspel!");

                    // Both players get 1 point for a tie
                    ScoresService.addScore(gameId, player1.getUserId(), 1);
                    ScoresService.addScore(gameId, player2.getUserId(), 1);

                    gameOver = true;
                }

                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                moves++;
            }
        } else {
            System.out.println("Er moeten twee spelers zijn ingelogd om het spel te starten.");
        }
    }

    private static void displayTopScores() {
        ScoresService.displayTopScores();
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
