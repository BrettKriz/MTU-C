/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Drawing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author Xazaviar
 */
public abstract class DrawHUD {
 
    public static void draw(Graphics g, int hp, int gold){
        //Health
        g.setColor(Color.gray);
        for(int h = 0; h < 6; h++){
            g.fillRect(25+h*10+(((int)h/2)*10), 25, 10, 20);
        }
        g.setColor(Color.red);
        for(int h = 0; h < hp; h++){
            g.fillRect(25+h*10+(((int)h/2)*10), 25, 10, 20);
        }
        
        //Gold
        g.setColor(Color.ORANGE);
        g.setFont(new Font("ARIAL",Font.BOLD,25));
        g.drawString("GOLD: "+gold, 125, 45);
        
    }
}
