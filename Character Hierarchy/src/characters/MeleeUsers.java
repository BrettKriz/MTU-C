package characters;

public abstract class MeleeUsers extends characters.Character {
    protected int att;
    
    public MeleeUsers(String name, int maxHP, int att, int def){
        super(name, maxHP, def);
        this.att = att;
    }
    
    public void setAttack(int att) {
        this.att = att;
    }

    public int getAttack() {
        return att;
    }
    
}
