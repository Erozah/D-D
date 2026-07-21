/**
 * Représente un guerrier dans le jeu Donjons et Dragons.
 * Cette classe hérite de Character et définit les attributs spécifiques à un guerrier.
 */
package fr.campus.d_and_d.characters;

import fr.campus.d_and_d.items.DefensiveEquipment;
import fr.campus.d_and_d.items.OffensiveEquipment;

public class Warrior extends Character {
	/**
	 * Crée un nouveau guerrier avec un type et un nom spécifiés.
	 * @param type Le type du personnage (doit être "Guerrier").
	 * @param name Le nom du guerrier.
	 */
	public Warrior(String type, String name) {
		super(type,
				name,
				10,
				7,
				new OffensiveEquipment("Arme", "Epée", 5),
				new DefensiveEquipment("Bouclier", "Bouclier en bois", 5));
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
