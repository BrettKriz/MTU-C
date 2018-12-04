package characters;

public class Warlock extends SpellUsers {
	private Familiar familiar;
	
	public Warlock(String name, int maxHP, String spell, int mag, int def) {
		super(name, maxHP, spell, mag, def);
		this.familiar = null;
	}

	public void setFamiliar(Familiar familiar) {
		this.familiar = familiar;
	}
	
	public Familiar getFamiliar() {
		return familiar;
	}
	
	public void conjureFamiliar() {
		if(familiar != null) {
			System.out.printf("Go get'em, %s!%n", familiar.getName());
		}
		else {
			System.out.printf("I am without a familiar.%n");
		}
	}
	
        @Override
	public void attack() {
		chargePower();
		castSpell();
		
		if(familiar != null) {
			familiar.attack();
		}
	}
	
        @Override
	public void chargePower() {
		System.out.printf("Dark forces grant me your power!%n");
	}
	
        @Override
	public void castSpell() {
		System.out.printf("From these hands I cast %s.%n", getSpell());
	}
	
        @Override
	public String toString() {
		String follow;
		if(familiar != null) {
			follow = "with familiar " + familiar.getName();
		}
		else {
			follow = "alone";
		}
		
		return String.format("Warlock %s who travels %s", getName(), follow);
	}
}
