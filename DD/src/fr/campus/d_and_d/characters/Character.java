/**
 * Représente un personnage générique dans le jeu Donjons et Dragons.
 * Cette classe définit les attributs de base d'un personnage, tels que son type, son nom,
 * ses points de vie, et son équipement.
 */
package fr.campus.d_and_d.characters;

public class Character {
	private String type;
	private String name;
	private int lifePoints;
	private int damagePoint;
	private String offensiveEquipment;
	private String defensiveEquipment;

	/**
	 * Crée un nouveau personnage avec un type et un nom spécifiés.
	 * @param type Le type du personnage (ex: "Guerrier" ou "Magicien").
	 * @param name Le nom du personnage.
	 */
	public Character(String type, String name) {
		this.type = type;
		this.name = name;
		this.lifePoints = 1;
		this.damagePoint = 1;
		this.offensiveEquipment = "None";
		this.defensiveEquipment = "None";
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
	public String getOffensiveEquipment() {
		return offensiveEquipment;
	}
	public void setOffensiveEquipment(String offensiveEquipment) {
		this.offensiveEquipment = offensiveEquipment;
	}
	public String getDefensiveEquipment() {
		return defensiveEquipment;
	}
	public void setDefensiveEquipment(String defensiveEquipment) {
		this.defensiveEquipment = defensiveEquipment;
	}
	public String toString() {
		return "Le personnage est un "+ type +
				"\nIl s'appelle " + name + " et possède " + lifePoints +
				" points de vie. \nIl fait " + damagePoint + " dégats grâce à son " + offensiveEquipment +
				".\nIl peut se protéger grâce à : " + defensiveEquipment;
	}
}
