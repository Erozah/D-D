-- MySQL database schema for Dungeons and Dragons game
-- This schema only stores characters and their equipment
-- Compatible with SimpleDatabaseManager

-- Drop existing tables if they exist
DROP TABLE IF EXISTS equipment;
DROP TABLE IF EXISTS characters;
USE DnD;

-- Create characters table (MySQL syntax)
CREATE TABLE characters (
    id INT AUTO_INCREMENT PRIMARY KEY,
    character_type VARCHAR(50) NOT NULL,  -- WARRIOR, WIZARD, etc.
    name VARCHAR(100) NOT NULL,          -- Increased size for longer names
    health_points INT NOT NULL DEFAULT 10,
    attack_power INT NOT NULL DEFAULT 5,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create equipment table (MySQL syntax)
CREATE TABLE equipment (
    id INT AUTO_INCREMENT PRIMARY KEY,
    equipment_type VARCHAR(20) NOT NULL,  -- WEAPON, SPELL, SHIELD, POTION
    name VARCHAR(100) NOT NULL,          -- Increased size for equipment names
    power INT NOT NULL DEFAULT 1,        -- attack power for weapons/spells, defense for shields/potions
    owner_id INT,                        -- ID of the character who owns this equipment
    FOREIGN KEY (owner_id) REFERENCES characters(id) ON DELETE CASCADE
);

-- Insert sample data for testing
-- Using types that match our SimpleDatabaseManager (WARRIOR, WIZARD)
INSERT INTO characters (character_type, name, health_points, attack_power) VALUES
    ('WARRIOR', 'Guerrier Test', 20, 8),
    ('WIZARD', 'Magicien Test', 15, 10);

-- Insert equipment for the test characters
INSERT INTO equipment (equipment_type, name, power, owner_id) VALUES
    ('WEAPON', 'Épée de Test', 5, 1),      -- Warrior's weapon
    ('SHIELD', 'Bouclier de Test', 3, 1), -- Warrior's shield  
    ('SPELL', 'Boule de Test', 7, 2), -- Wizard's spell
    ('POTION', 'Potion de Test', 5, 2);   -- Wizard's potion
