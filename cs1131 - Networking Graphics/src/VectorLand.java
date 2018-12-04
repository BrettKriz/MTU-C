import Original.Renderer;
import Original.View;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.*;
import javax.swing.*;

import java.io.*;
import java.net.*; // NEtworking for sockets

//http://hastebin.com/raw/opujowagon.java
// java VectorLand wopr.csl.mtu.edu
/**
 * http://hastebin.com/opujowagon.java
 * download it there
 * 
 * @author Brett Kriz
 */
public class VectorLand {
    static final Image buff = new BufferedImage(640, 480, BufferedImage.TYPE_3BYTE_BGR);
        // for double buffering;
            //2 threads, by using sychronize, we never have half frames
    
    public static void main(String[] args){
        View app = new View();
        app.setPreferredSize(new Dimension(640,480));
        app.addMouseListener(app);
        app.addMouseMotionListener(app);
        // Mouse events to the JPanel, because we ant it relative to the panels coords
        
        JFrame window = new JFrame("VectorLand");
        window.add(app);
        window.addKeyListener(app); // You dont get events if you listen to a panel
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setVisible(true);
        
        Renderer ren = new Renderer();
        ren.start();
        
        // NOW THAT ITS VISABLE
        while(true){
            // EVENT loop, Think
            app.repaint();
            // ESTABLISH CONNECT TO REMOTE SERVER
            if (app.out == null || ren.in == null){
                try{
                    Socket sock = new Socket(args[0], 10101);
                    app.out = new DataOutputStream(sock.getOutputStream());
                    ren.in = new DataInputStream(sock.getInputStream());
                }catch (IOException e){
                    // If we fail, wait
                    try{
                        Thread.sleep(5000);
                        
                    }catch (InterruptedException ex){
                        
                    }
                }
            }
            
            // Just to have it loop at about 30 FPS
            // Without overstacking
            try { Thread.sleep(1000/30);}
            catch (InterruptedException e){}
        }
        
    }
}

class View extends JPanel implements KeyListener, MouseListener, MouseMotionListener{
    DataOutputStream out;
    
    public void paint(Graphics g){
        /*
        int r = (int)Math.random()*256;
        int gr = (int)Math.random()*256;
        int b = (int)Math.random()*256;
        g.setColor(new Color(150,150,180));
        g.fillRect(10, 10, 50, 70);
        */
        synchronized(VectorLand.buff){
            g.drawImage(VectorLand.buff, 0, 0, null);
            
        }
        
    }
    // Go thru output sream
    void writeCommand(int command, int... args){
        if (out == null){
            return;
        }
        try{
            out.writeByte((byte)command);
            for(int a : args){
                out.writeInt(a);
                out.flush(); // EXPLICITLY FLUSH
            }
        }catch (IOException ex){
            out = null;
        }
    }
    
    public void keyPressed      (KeyEvent e){ writeCommand(0, e.getKeyCode()); }
    public void keyReleased     (KeyEvent e){ writeCommand(1, e.getKeyCode()); }
    public void keyTyped        (KeyEvent e){ writeCommand(2, e.getKeyCode()); }
    
    public void mousePressed    (MouseEvent e){ writeCommand(3); }
    public void mouseReleased   (MouseEvent e){ writeCommand(4); }
    public void mouseClicked    (MouseEvent e){ System.out.println(e);}
    public void mouseEntered    (MouseEvent e){}
    public void mouseExited     (MouseEvent e){}
    
    public void mouseDragged    (MouseEvent e){ writeCommand(5, e.getX(), e.getY()); }
    public void mouseMoved      (MouseEvent e){ writeCommand(5, e.getX(), e.getY()); }
}

class Renderer extends Thread{
    // Convenience 
    DataInputStream in;
    Color fill = Color.BLACK;
    Color stroke = Color.BLACK;
    
    final Graphics2D g = (Graphics2D)VectorLand.buff.getGraphics();
    {
        // Turn o
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.KEY_COLOR_RENDERING); // 2nd is wrong
    }
    
    
    public void run(){
        while(true){
            if (in == null){
               // interupted, monitor object
                synchronized(VectorLand.buff){
                    g.setColor(Color.WHITE);
                    g.fillRect(0,0,640,480);
                    g.setColor(Color.BLACK);
                    g.drawString("NO CONNECTION TO SERVER",10,20);
                }
            }else{
                try{
                    in.readByte();
                    synchronized(VectorLand.buff){
                        g.setColor(Color.WHITE);
                        g.fillRect(0,0,640,480);
                        frame();
                    }
                }catch (IOException ex){
                    // Tell the think to retry connection
                    in = null;
                }
            }
        }
    }
    void frame(){
        while(true){
            switch(in.readByte()){
                case 0: 
                    return;
                case 1:
                    fill = readColor();
                    break;
                case 2:
                    stroke = readColor();
                    break;
                case 3:
                    int size = in.readInt()-1; // # of verticies
                    Path2D.Float path = new Path2D.Float();
                    path.moveTo(in.readShort(), in.readShort());
                    while(size --> 0){ // Downto operator? -- >
                        path.lineTo(in.readShort(), in.readShort());
                    }
                    path.closePath();
                    g.setColor(fill);
                    g.fill(path);
                    g.setColor(stroke);
                    g.draw(path);
            }
        }
    }
    Color readColor() throws IOException{
        return new Color( // Color expects positive integers, so force sign bit to be 0
            in.readByte() & 0xFF,
            in.readByte() & 0xFF,
            in.readByte() & 0xFF,
            in.readByte() & 0xFF
        );
    }
}