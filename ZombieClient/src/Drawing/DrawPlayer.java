package Drawing;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Xazaviar
 */
public abstract class DrawPlayer {
    
    private static final int playerSize = 5*10;
    
    public static void draw(Graphics g, int wid, int hei){
        g.setColor(Color.GREEN);
        g.fillRect(wid/2-playerSize/2, hei/2-playerSize/2, playerSize, playerSize);
    }
}
