package characters;

public abstract class SpellUsers extends characters.Character {
    protected String spell;
    protected int mag;
    
    public SpellUsers(String name, int maxHP, String spell, int mag, int def){
        super(name, maxHP, def);
        this.mag = mag;
        this.spell = spell;
    }
    
    public void setSpell(String spell) {
        this.spell = spell;
    }
	
    public String getSpell() {
        return spell;
    }

    public void setMagic(int mag) {
        this.mag = mag;
    }

    public int getMagic() {
        return mag;
    }
    
    public abstract void chargePower();
    public abstract void castSpell();
}
