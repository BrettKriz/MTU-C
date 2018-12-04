package Drawing;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Xazaviar
 */
public abstract class DrawMap {
    
    private static final int tileSize = 6*10;

    public static void draw(Graphics g, String[][] map, int pX, int pY, int wid, int hei){
        int startX=(wid/2-25)-pX*10, startY=(hei/2-25)-pY*10;
        
        for(int r = 0; r < map.length; r++){
            for(int c = 0; c < map[0].length; c++){
                if(map[r][c].equals("w")){
                    g.setColor(Color.black);
                    g.fillRect(c*tileSize+startX, r*tileSize+startY, tileSize, tileSize);
                }
            }
        }
    }
}
