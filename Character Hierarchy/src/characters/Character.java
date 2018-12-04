package characters;

/**
 * <p>The base for other characters.</p>
 * 
 * @author Brett Kriz
 **/
public abstract class Character {
    protected String name;
    protected int maxHP;
    protected int hp;
    protected int def;
    protected int level;
    
    public Character(String name, int maxHP, int def){
        this.name = name;
        this.maxHP = maxHP;
        this.hp = maxHP;
        this.def = def;
        this.level = 0;
    }
    
    public String getName() {
        return name;
    }

    public void takeDamage(int damage) {
        hp  = Math.min(Math.max(0, hp - (damage / getDefense())), maxHP);
    }

    public int hitPoints() {
        return hp;
    }

    public boolean fainted() {
        return hp == 0;
    }

    public void setDefense(int def) {
        this.def = def;
    }

    public int getDefense() {
        return def;
    }

    public int level() {
        return level;
    }

    public void levelUp() {
        level += 1;
    }
    
    public abstract void attack();
    
    @Override
    public String toString(){
        return "Call this an easter egg";
    }

}
