package TickTackToe;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import TickTackToe.Entity.Users;
import TickTackToe.Repo.AuditlogRepo;
import TickTackToe.Repo.ScoresRepo;
import TickTackToe.Service.UsersService;


public class Game {
    private static final Scanner scanner = new Scanner(System.in);
    private static Users player1;
    private static Users player2;
    private static Users currentUser; 

    public static void main(String[] args) {
        System.out.println("Welkom bij Tic Tac Toe!");
        
        while (true) {
            System.out.println("\n1. Registreren");
            System.out.println("2. Inloggen");
            System.out.println("3. Spel starten");
            System.out.println("4. Top 10 scores weergeven");
            System.out.println("5. Audit");
            System.out.println("6. Afsluiten");
            System.out.print("Kies een optie: ");
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
                    displayTopScores();
                    break;
                case 5:
                    displayAllLogs(currentUser);
                    break;                
                case 6:
                    System.out.println("Bedankt voor het spelen van Tic Tac Toe! Tot ziens.");
                    System.exit(0);
                default:
                    System.out.println("Ongeldige keuze. Probeer het opnieuw.");
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
                System.out.print(board[i][j]);
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
    
            // Display the initial board
            displayBoard(board);
    
            // Game loop
            boolean gameOver = false;
            char currentPlayer = 'X'; // Start met speler X
            int moves = 0;
    
            while (!gameOver && moves < 9) {
                // Prompt the current player to make a move
                System.out.println("Speler " + currentPlayer + ", kies een positie (1-9):");
                int position = scanner.nextInt();
    
                // Check if the position is valid
                if (position < 1 || position > 9) {
                    System.out.println("Ongeldige positie. Probeer opnieuw.");
                    continue;
                }
    
                // Convert position to array indices
                int row = (position - 1) / 3;
                int col = (position - 1) % 3;
    
                // Check if the position is already taken
                if (board[row][col] == 'X' || board[row][col] == 'O') {
                    System.out.println("Deze positie is al ingenomen. Kies een andere.");
                    continue;
                }
    
                // Place the current player's symbol on the board
                board[row][col] = currentPlayer;
    
                // Display the updated board
                displayBoard(board);
    
                // Check for a win or tie
                if (checkWin(board, currentPlayer)) {
                    System.out.println("Speler " + currentPlayer + " wint!");
                    gameOver = true;
                } else if (moves == 8) {
                    System.out.println("Het is een gelijkspel!");
                    gameOver = true;
                }
    
                // Switch to the next player
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                moves++;
            }
        } else {
            System.out.println("Er moeten twee spelers zijn ingelogd om het spel te starten.");
        }
    }
    

    private static void displayTopScores() {
        System.out.println("\n--- Top 10 Scores ---");
        try {
            ResultSet topScores = ScoresRepo.getTopScores();
            int rank = 1;
            while (topScores.next()) {
                String username = topScores.getString("username");
                int score = topScores.getInt("score");
                System.out.println(rank + ". " + username + ": " + score);
                rank++;
            }
        } catch (SQLException e) {
            System.out.println("Fout bij het ophalen van top 10 scores: " + e.getMessage());
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
