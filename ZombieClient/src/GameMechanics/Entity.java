package GameMechanics;

/**
 *
 * @author Xazaviar
 */
public class Entity {

    private String type = "";
    private int x = 0, y = 0, size = 0;
    
    public Entity(String t, int x, int y, int size){
        this.type = t;
        this.x = x;
        this.y = y;
        this.size = size;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
