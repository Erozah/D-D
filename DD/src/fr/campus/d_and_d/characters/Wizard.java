/**
 * Représente un magicien dans le jeu Donjons et Dragons.
 * Cette classe hérite de Character et définit les attributs spécifiques à un magicien.
 */
package fr.campus.d_and_d.characters;

public class Wizard extends Character {
	/**
	 * Crée un nouveau magicien avec un type et un nom spécifiés.
	 * @param type Le type du personnage (doit être "Magicien").
	 * @param name Le nom du magicien.
	 */
	public Wizard(String type, String name) {
		super(type, name);
		setLifePoints(7);
		setDamagePoint(7);
		setOffensiveEquipment("Sort");
		setDefensiveEquipment("Potion");
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
