package fr.campus.d_and_d.items;

public class Potion extends DefensiveEquipment {
	public Potion(String defensiveType, String name, int defensivePoints) {
		super(defensiveType, name, defensivePoints);
	}

	@Override
	public String toString() {
		return getName();
	}
}
