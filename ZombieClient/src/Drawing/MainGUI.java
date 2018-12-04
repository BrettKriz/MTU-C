
package Drawing;

import GameMechanics.Entity;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Xazaviar
 */
public class MainGUI {

    private int Width = 1050;
    private int Height = 650;
    private JFrame frame = new JFrame();
    private myJPanel mainpanel = new myJPanel();
    
    //Other Vars
    private boolean[] keyPresses = {false,false,false,false,false,false,false,false};
    private ArrayList<Entity> coords = new ArrayList<Entity>();
    private String[][] map;
    private final String mapName = "test 2";
    
    //HUD vars
    private int health = 6, gold = 0;
    
    /**
     * The default constructor
     */
    public MainGUI(){
        
        //Set up map
        try{
            Scanner scan = new Scanner(new File("Maps/"+mapName+".txt"));
            int row = scan.nextInt();
            int col = scan.nextInt();
            map = new String[row][col];
            for(int r = 0; r < map.length; r++){
                for(int c = 0; c < map[0].length; c++){
                    map[r][c] = scan.next();
                }
            }
        } catch(IOException e){
            System.out.println("Error loading in map");
            System.exit(0);
        }
        
        frame = new JFrame("SURVIVE!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(Width, Height);
        
        mainpanel.setPreferredSize(new Dimension(Width, Height));
        mainpanel.setLayout(null);
        mainpanel.addKeyListener(new Push());
        mainpanel.setFocusable(true);
        
        //Start up the magic
        frame.getContentPane().add(mainpanel);
        frame.setVisible(true);
        frame.pack();
    }   
    
    public void display(){
        mainpanel.repaint();
    }
    
    public boolean[] keyPresses(){
        return this.keyPresses;
    }
    
    public void coords(ArrayList<Entity> c){
        this.coords = c;
    }
    
    public void HUDVars(int health, int gold){
        this.health = health;
        this.gold = gold;
    }
    
     /**
     * This class represents the panel that the player sees
     */
    private class myJPanel extends JPanel{
       
        /**
         * Paints the panel
         * @param g 
         *          The graphics being used to draw
         */
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            
//            System.out.println(coords.get(1).getType()+" "+coords.get(1).getSize()+": "+coords.get(1).getX()+", "+coords.get(1).getY());
            
            if(coords.size()>0 && health>0){
                DrawMap.draw(g, map, coords.get(0).getX(),coords.get(0).getY(), mainpanel.getWidth(),mainpanel.getHeight());
                DrawPlayer.draw(g, mainpanel.getWidth(),mainpanel.getHeight());
                DrawHUD.draw(g, health, gold);
            }else if(coords.size()>0){
                DrawMap.draw(g, map, coords.get(0).getX(),coords.get(0).getY(), mainpanel.getWidth(),mainpanel.getHeight());
                DrawHUD.draw(g, health, gold);
            }
            
            for(int d = 1; d < coords.size(); d++){
                DrawEntity.draw(g,coords.get(d).getType(),coords.get(d).getX(),coords.get(d).getY(), coords.get(d).getSize(), 
                                  coords.get(0).getX(),coords.get(0).getY(), mainpanel.getWidth(),mainpanel.getHeight());
            }
            
        }
    }
    
    /**
     * This class represents the button pushes by the client
     */
    private class Push extends myJPanel implements KeyListener{
        
        /**
         * The default constructor (not in use)
         */
        public Push(){}
        
        /**
         * The event that occurs when a key is pressed
         * @param e 
         *          The event
         */
        public void keyPressed(KeyEvent e) {
          /* int id = e.getKeyCode();
           int xx = p.getx();
           int yy = p.gety();*/

            switch (e.getKeyCode()){
                //Movement Keys
                case KeyEvent.VK_A:
                keyPresses[0] = true;
                break;
                case KeyEvent.VK_D:
                keyPresses[2] = true;
                break;
                case KeyEvent.VK_W:
                keyPresses[1] = true;
                break;
                case KeyEvent.VK_S:
                keyPresses[3] = true;
                break;
                case KeyEvent.VK_UP:
                keyPresses[5] = true;
                break;
                case KeyEvent.VK_DOWN:
                keyPresses[7] = true;
                break;
                case KeyEvent.VK_LEFT:
                keyPresses[4] = true;
                break;
                case KeyEvent.VK_RIGHT:
                keyPresses[6] = true;
                break;
                
            }
        }
        
        /**
         * The event that occurs when a key is typed (not used)
         * @param e 
         *          The event
         */
        public void keyTyped(KeyEvent e) {}
        
        /**
         * The event that occurs when a key is released
         * @param e 
         *          The event
         */
        public void keyReleased(KeyEvent e) {
         switch (e.getKeyCode()){
                case KeyEvent.VK_A:
                keyPresses[0] = false;
                break;
                case KeyEvent.VK_D:
                keyPresses[2] = false;
                break;
                case KeyEvent.VK_W:
                keyPresses[1] = false;
                break;
                case KeyEvent.VK_S:
                keyPresses[3] = false;
                break;
                case KeyEvent.VK_UP:
                keyPresses[5] = false;
                break;
                case KeyEvent.VK_DOWN:
                keyPresses[7] = false;
                break;
                case KeyEvent.VK_LEFT:
                keyPresses[4] = false;
                break;
                case KeyEvent.VK_RIGHT:
                keyPresses[6] = false;
                break;
            }

        }
     }
}
