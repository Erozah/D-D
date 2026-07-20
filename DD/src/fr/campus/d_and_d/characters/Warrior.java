/**
 * Représente un guerrier dans le jeu Donjons et Dragons.
 * Cette classe hérite de Character et définit les attributs spécifiques à un guerrier.
 */
package fr.campus.d_and_d.characters;

public class Warrior extends Character {
	/**
	 * Crée un nouveau guerrier avec un type et un nom spécifiés.
	 * @param type Le type du personnage (doit être "Guerrier").
	 * @param name Le nom du guerrier.
	 */
	public Warrior(String type, String name) {
		super(type, name);
		setLifePoints(10);
		setDamagePoint(7);
		setOffensiveEquipment("Arme");
		setDefensiveEquipment("Bouclier");
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
