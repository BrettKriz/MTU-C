package Drawing;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Xazaviar
 */
public abstract class DrawEntity {
    
    private static final int scale = 10;
    
    public static void draw(Graphics g, String type, int x, int y, int size, int pX, int pY, int wid, int hei){
        int startX = x*10+(wid/2-25)-pX*10, startY = (hei/2-25)-(pY*10-y*10);
        
        if(type.equals("player"))
            g.setColor(Color.BLUE);
        else if(type.equals("bullet"))
            g.setColor(Color.orange);
        else if(type.equals("zombie"))
            g.setColor(Color.red);
        g.fillRect(startX, startY, size*scale, size*scale);
    }
}
