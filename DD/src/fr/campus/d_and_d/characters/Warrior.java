/**
 * Représente un guerrier dans le jeu Donjons et Dragons.
 * Cette classe hérite de Character et définit les attributs spécifiques à un guerrier.
 */
package fr.campus.d_and_d.characters;
import fr.campus.d_and_d.items.Shield;
import fr.campus.d_and_d.items.Weapon;

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
				new Weapon(),
				new Shield());
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
