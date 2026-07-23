-- MySQL dump for Dungeons and Dragons Game Database
-- Iteration 5: Complete Database Schema

DROP DATABASE IF EXISTS DnD;
CREATE DATABASE DnD;
USE DnD;

-- Table for game boards
CREATE TABLE boards (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    size INT NOT NULL DEFAULT 64,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- Table for cells with content
CREATE TABLE cells (
    id INT AUTO_INCREMENT PRIMARY KEY,
    board_id INT NOT NULL,
    position INT NOT NULL,
    content_type ENUM('EMPTY', 'ENEMY', 'WEAPON', 'POTION', 'BOSS', 'MYSTERY_BOX') NOT NULL,
    content_id INT,
    FOREIGN KEY (board_id) REFERENCES boards(id) ON DELETE CASCADE,
    UNIQUE KEY (board_id, position)
) ENGINE=InnoDB;

-- Table for characters (players and enemies)
CREATE TABLE characters (
    id INT AUTO_INCREMENT PRIMARY KEY,
    character_type ENUM('WARRIOR', 'WIZARD', 'GOBLIN', 'ORC', 'SORCERER', 'DRAGON') NOT NULL,
    name VARCHAR(50) NOT NULL,
    health_points INT NOT NULL,
    attack_power INT NOT NULL,
    is_enemy BOOLEAN NOT NULL DEFAULT FALSE,
    is_boss BOOLEAN NOT NULL DEFAULT FALSE,
    cell_id INT,
    FOREIGN KEY (cell_id) REFERENCES cells(id) ON DELETE SET NULL
) ENGINE=InnoDB;

-- Table for equipment (weapons, spells, shields, potions)
CREATE TABLE equipment (
    id INT AUTO_INCREMENT PRIMARY KEY,
    equipment_type ENUM('WEAPON', 'SPELL', 'SHIELD', 'POTION') NOT NULL,
    name VARCHAR(50) NOT NULL,
    power INT NOT NULL,
    owner_id INT,
    cell_id INT,
    FOREIGN KEY (owner_id) REFERENCES characters(id) ON DELETE SET NULL,
    FOREIGN KEY (cell_id) REFERENCES cells(id) ON DELETE SET NULL
) ENGINE=InnoDB;

-- Table for saved games
CREATE TABLE saved_games (
    id INT AUTO_INCREMENT PRIMARY KEY,
    player_id INT NOT NULL,
    board_id INT NOT NULL,
    current_position INT NOT NULL,
    save_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (player_id) REFERENCES characters(id),
    FOREIGN KEY (board_id) REFERENCES boards(id)
) ENGINE=InnoDB;

-- Insert default board with predefined content
INSERT INTO boards (name, size) VALUES ('DefaultBoard', 64);

-- Set the default board ID
SET @default_board_id = LAST_INSERT_ID();

-- Insert cells with content based on position
INSERT INTO cells (board_id, position, content_type) VALUES
-- Boss cells
(@default_board_id, 64, 'BOSS'),
-- Enemy cells
(@default_board_id, 10, 'ENEMY'),
(@default_board_id, 20, 'ENEMY'),
(@default_board_id, 25, 'ENEMY'),
(@default_board_id, 32, 'ENEMY'),
(@default_board_id, 35, 'ENEMY'),
(@default_board_id, 36, 'ENEMY'),
(@default_board_id, 37, 'ENEMY'),
(@default_board_id, 40, 'ENEMY'),
(@default_board_id, 44, 'ENEMY'),
(@default_board_id, 47, 'ENEMY'),
-- Weapon cells
(@default_board_id, 2, 'WEAPON'),
(@default_board_id, 11, 'WEAPON'),
(@default_board_id, 19, 'WEAPON'),
(@default_board_id, 26, 'WEAPON'),
(@default_board_id, 42, 'WEAPON'),
(@default_board_id, 53, 'WEAPON'),
-- Potion cells
(@default_board_id, 7, 'POTION'),
(@default_board_id, 13, 'POTION'),
(@default_board_id, 28, 'POTION'),
(@default_board_id, 31, 'POTION'),
(@default_board_id, 33, 'POTION'),
(@default_board_id, 39, 'POTION'),
(@default_board_id, 41, 'POTION'),
(@default_board_id, 43, 'POTION'),
-- Mystery boxes
(@default_board_id, 1, 'MYSTERY_BOX'),
(@default_board_id, 4, 'MYSTERY_BOX'),
(@default_board_id, 5, 'MYSTERY_BOX'),
(@default_board_id, 8, 'MYSTERY_BOX'),
(@default_board_id, 17, 'MYSTERY_BOX'),
(@default_board_id, 22, 'MYSTERY_BOX'),
(@default_board_id, 38, 'MYSTERY_BOX'),
(@default_board_id, 48, 'MYSTERY_BOX'),
(@default_board_id, 49, 'MYSTERY_BOX');

-- Insert default enemies
INSERT INTO characters (character_type, name, health_points, attack_power, is_enemy, cell_id) VALUES
('DRAGON', 'Smaug', 50, 20, TRUE, (SELECT id FROM cells WHERE board_id = @default_board_id AND position = 64)),
('SORCERER', 'Dark Sorcerer', 15, 12, TRUE, (SELECT id FROM cells WHERE board_id = @default_board_id AND position = 10)),
('GOBLIN', 'Goblin', 8, 5, TRUE, (SELECT id FROM cells WHERE board_id = @default_board_id AND position = 20)),
('ORC', 'Orc Warrior', 12, 8, TRUE, (SELECT id FROM cells WHERE board_id = @default_board_id AND position = 25));

-- Insert default equipment
INSERT INTO equipment (equipment_type, name, power, cell_id) VALUES
-- Weapons
('WEAPON', 'Mace', 6, (SELECT id FROM cells WHERE board_id = @default_board_id AND position = 2)),
('WEAPON', 'Sword', 7, (SELECT id FROM cells WHERE board_id = @default_board_id AND position = 11)),
('WEAPON', 'Axe', 8, (SELECT id FROM cells WHERE board_id = @default_board_id AND position = 19)),
('WEAPON', 'Excalibur', 15, (SELECT id FROM cells WHERE board_id = @default_board_id AND position = 26)),
('WEAPON', 'Dagger', 4, (SELECT id FROM cells WHERE board_id = @default_board_id AND position = 42)),
('WEAPON', 'Bow', 6, (SELECT id FROM cells WHERE board_id = @default_board_id AND position = 53)),
-- Potions
('POTION', 'Health Potion', 5, (SELECT id FROM cells WHERE board_id = @default_board_id AND position = 7)),
('POTION', 'Mana Potion', 4, (SELECT id FROM cells WHERE board_id = @default_board_id AND position = 13)),
('POTION', 'Strength Potion', 3, (SELECT id FROM cells WHERE board_id = @default_board_id AND position = 28)),
('POTION', 'Large Health Potion', 10, (SELECT id FROM cells WHERE board_id = @default_board_id AND position = 41)),
-- Mystery box contents (will be assigned randomly in game)
('WEAPON', 'Lightning Bolt', 12, (SELECT id FROM cells WHERE board_id = @default_board_id AND position = 1)),
('WEAPON', 'Fireball', 15, (SELECT id FROM cells WHERE board_id = @default_board_id AND position = 4)),
('POTION', 'Standard Potion', 5, (SELECT id FROM cells WHERE board_id = @default_board_id AND position = 8)),
('POTION', 'Large Potion', 10, (SELECT id FROM cells WHERE board_id = @default_board_id AND position = 17));

-- Update cells with content IDs
UPDATE cells c
JOIN (
    SELECT id, character_type FROM characters WHERE is_enemy = TRUE
) e ON c.content_type = 'ENEMY' AND c.position IN (10, 20, 25, 32, 35, 36, 37, 40, 44, 47)
SET c.content_id = e.id
WHERE c.board_id = @default_board_id;

UPDATE cells c
JOIN equipment e ON c.position = e.cell_id
SET c.content_id = e.id
WHERE c.board_id = @default_board_id AND c.content_type IN ('WEAPON', 'POTION', 'MYSTERY_BOX');

UPDATE cells SET content_id = (
    SELECT id FROM characters WHERE character_type = 'DRAGON' AND is_enemy = TRUE
) WHERE board_id = @default_board_id AND position = 64;
