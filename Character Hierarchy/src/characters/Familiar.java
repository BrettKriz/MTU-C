package characters;

public class Familiar extends MeleeUsers {
        // I seriously dont understand what a "Familiar" is..
        // I assume a dog or minion, but still what neccisarly is a "Familiar"??
	public Familiar(String name, int maxHP, int att, int def) {
                super(name, maxHP, att, def);
	} // This essentially is the base MeleeUsers, so is it Familar in that way?
	
        @Override
	public void attack() {
		System.out.println("Woof! Woof! *Crunch!*");
	}
	
        @Override
	public String toString() {
		return String.format("Familiar %s", getName());
	}
}
