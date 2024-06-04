-- Maak de database aan
CREATE DATABASE TicTacToeDB;

-- Gebruik de aangemaakte database
USE TicTacToeDB;

-- Maak de Users tabel aan
CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(60) NOT NULL,
    birth_date DATE,
    games_played INT DEFAULT 0
);

-- Maak de Games tabel aan
CREATE TABLE Games (
    game_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    game_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

-- Maak de Scores tabel aan
CREATE TABLE Scores (
    score_id INT AUTO_INCREMENT PRIMARY KEY,
    game_id INT,
    user_id INT,
    score INT,
    FOREIGN KEY (game_id) REFERENCES Games(game_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

-- Maak de AuditLog tabel aan
CREATE TABLE AuditLog (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    action VARCHAR(255),
    action_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

-- Voeg voorbeeldgebruikers toe
INSERT INTO Users (username, password, birth_date) VALUES 
('john_doe', '$2a$10$7Q1O5HVQ2nC5/.rTzP8We.Lf5d6/dJcWnSDlV34ZD6i5AaFmMKZbi', '1990-01-01'),  -- 'password1' gehashed met bcrypt
('jane_doe', '$2a$10$EixZaYVK1fsbw1ZfbX3OXenp2FS.j9DIWkOePSO2UhVaWnJZG1rAC', '1992-02-02');  -- 'password2' gehashed met bcrypt

-- Voeg voorbeeldspellen toe
INSERT INTO Games (user_id) VALUES 
(1),
(2);

-- Voeg voorbeeldscores toe
INSERT INTO Scores (game_id, user_id, score) VALUES 
(1, 1, 10),
(2, 2, 15);

-- Voeg voorbeeld auditlog entries toe
INSERT INTO AuditLog (user_id, action) VALUES 
(1, 'Account created'),
(2, 'Password changed');

-- Haal de top 10 scores op
SELECT u.username, s.score 
FROM Scores s 
JOIN Users u ON s.user_id = u.user_id 
ORDER BY s.score DESC 
LIMIT 10;

-- Haal het aantal spellen gespeeld door een specifieke gebruiker op
SELECT games_played 
FROM Users 
WHERE username = 'john_doe';

-- Haal alle acties uit het auditlog voor een specifieke gebruiker op
SELECT action, action_date 
FROM AuditLog 
WHERE user_id = 1;
