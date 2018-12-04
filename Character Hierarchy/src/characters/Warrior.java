package characters;

public class Warrior extends MeleeUsers {
	private String weapon;
	private String offhand;
	
	public Warrior(String name, int maxHP, String weapon, int att, int def) {
                super(name, maxHP, att, def);
		this.weapon = weapon;
		this.offhand = null;
	}
	
	public void setWeapon(String weapon) {
		if(weapon != null) {
			this.weapon = weapon;
		}
	}
	
	public String getWeapon() {
		return weapon;
	}
	
	public void setOffhand(String offhand) {
		this.offhand = offhand;
	}
	
	public String getOffhand() {
		return offhand;
	}
	
        @Override
	public void attack() {
		String attack = weapon;
		if(offhand != null) {
			attack += " and " + offhand;
		}
		
		System.out.printf("I swing my %s%n", attack);
	}
	
        @Override
	public String toString() {
		return String.format("Warrior %s with %s and %s offhanded", 
			getName(),
			weapon, offhand == null ? "nothing" : offhand
		);
	}
}
