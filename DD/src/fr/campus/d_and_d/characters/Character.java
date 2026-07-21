/**
 * Représente un personnage générique dans le jeu Donjons et Dragons.
 * Cette classe définit les attributs de base d'un personnage, tels que son type, son nom,
 * ses points de vie, et son équipement.
 */
package fr.campus.d_and_d.characters;

import fr.campus.d_and_d.items.DefensiveEquipment;
import fr.campus.d_and_d.items.OffensiveEquipment;

public abstract class Character {
	private String type = "Settler";
	private String name = "Juan Carlos";
	private int lifePoints = 1;
	private int damagePoint = 1;
	private OffensiveEquipment offensiveEquipment =
			new OffensiveEquipment("Hand", "Fist", 1);
	private DefensiveEquipment defensiveEquipment =
			new DefensiveEquipment("Armor", "LeatherArmor", 1);

	/**
	 * Crée un nouveau personnage avec un type et un nom spécifiés.
	 * @param type Le type du personnage (ex: "Guerrier" ou "Magicien").
	 * @param name Le nom du personnage.
	 * @param lifePoints Le nombre de points de vie du personnage.
	 * @param damagePoint Le nombre de points de dégâts que le personnage inflige.
	 * @param offensiveEquipment L'équipement offensif du personnage.
	 * @param defensiveEquipment  L'équipement défensif du personnage.
	 */
	public Character(String type,
	                 String name,
	                 int lifePoints,
	                 int damagePoint,
	                 OffensiveEquipment offensiveEquipment,
	                 DefensiveEquipment defensiveEquipment) {
		this.type = type;
		this.name = name;
		this.lifePoints = lifePoints;
		this.damagePoint = damagePoint;
		this.offensiveEquipment = offensiveEquipment;
		this.defensiveEquipment = defensiveEquipment;
	}
	//* Méthodes
	// Actions
	// Getters setters
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLifePoints() {
		return lifePoints;
	}
	public void setLifePoints(int lifePoints) {
		this.lifePoints = lifePoints;
	}
	public int getDamagePoint() {
		return damagePoint;
	}
	public void setDamagePoint(int damagePoint) {
		this.damagePoint = damagePoint;
	}
	public OffensiveEquipment getOffensiveEquipment() {
		return offensiveEquipment;
	}
	public void setOffensiveEquipment(OffensiveEquipment offensiveEquipment) {
		this.offensiveEquipment = offensiveEquipment;
	}
	public DefensiveEquipment getDefensiveEquipment() {
		return defensiveEquipment;
	}
	public void setDefensiveEquipment(DefensiveEquipment defensiveEquipment) {
		this.defensiveEquipment = defensiveEquipment;
	}
	public String toString() {
		return "Le personnage est un "+ type +
				"\nIl s'appelle " + name + " et possède " + lifePoints +
				" points de vie. \nIl fait " + damagePoint + " dégats grâce à son " + offensiveEquipment +
				".\nIl peut se protéger grâce à : " + defensiveEquipment;
	}
}
