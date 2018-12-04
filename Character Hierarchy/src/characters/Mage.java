package characters;

public class Mage extends SpellUsers {
	private String title;
	private String color;
	private boolean isElder;
	
	public Mage(String name, int maxHP, String spell, int mag, int def) {
                super(name, maxHP, spell, mag, def);
		this.title = "Unknown";
		this.color = "Grey";
		this.isElder = false;
	}
	
	public void setTitle(String title) {
		if(title != null) {
			this.title = title;
		}
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setColor(String color) {
		if(title != null) {
			this.color = color;
		}
	}
	
	public String getColor() {
		return color;
	}
	
	public void makeElder(boolean isElder) {
		this.isElder = isElder;
	}
	
	public boolean isElder() {
		return isElder;
	}
	
	public void honored() {
		title = "Magnificent";
		isElder = true;
	}
	
	public void dishonor() {
		title = "Shunned";
		isElder = false;
	}
	
        @Override
	public void attack() {
		chargePower();
		castSpell();
	}
	
        @Override
	public void chargePower() {
		System.out.printf("Let my power focus!%n");
	}
	
        @Override
	public void castSpell() {
		System.out.printf("From this wand I cast %s.%n", getSpell());
	}
	
        @Override
	public String toString() {
		String honorary = isElder ? "Elder Mage" : "Mage";
		String descriptor = String.format("the %s of color %s", title, color);
		
		return String.format("%s %s %s", honorary, getName(), descriptor);
	}
}
