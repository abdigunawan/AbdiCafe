/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tugas4_AbdiGunawan_182004;

import java.awt.Graphics;
import java.awt.Color;
import java.applet.*;
/**
 *
 * @author abdig
 */
public class Animasi extends Applet implements Runnable {

    Thread runner;
    int xpos,ypos;
    int ux1,ux2;

    public void start(){
        if (runner == null);{
        runner = new Thread(this);
        runner.start();
        }
    }

    public void stop(){
        if(runner != null){
            runner.stop();
            runner = null;
        }
    }

    public void run(){
        setBackground(Color.WHITE);
        while(true){
           //from green to black
            for(ypos = 105;ypos>5;ypos-=4){
                xpos = 104;
                repaint();
                try{Thread.sleep(100);}
                catch(InterruptedException e){}  
            }

            //from black to yellow
            for(xpos = 104;xpos>5;xpos-=4){
                ux1 = xpos;
                repaint();
                try{Thread.sleep(100);}
                catch(InterruptedException e){}  
            }

            //from yellow to blue
            for(ypos = 5;ypos<=105;ypos+=4){
                ux1 = ypos;
                repaint();
                try{Thread.sleep(100);}
                catch(InterruptedException e){}  
            }

            //from blue to green
             for(ypos = 105;xpos<=105;xpos+=4){
                ux1 = xpos;
                repaint();
                try{Thread.sleep(100);}
                catch(InterruptedException e){}  
            }
        }
    }

    public void update(Graphics g){
        paint(g);
    }

    public void paint (Graphics g){
        //yellowbackground
        g.setColor(Color.YELLOW);
        g.fillRect(0,0,100,100);

        //bluebackground
        g.setColor(Color.BLUE);
        g.fillRect(0,100,100,100);

        //blackbackground
        g.setColor(Color.BLACK);
        g.fillRect(100,0,100,100);

        //greenbackground
        g.setColor(Color.GREEN);
        g.fillRect(100,100,100,100);

        //red circle
        g.setColor(Color.RED);
        g.fillOval(xpos,ypos,90,90);
        
        //reset
        ux1 = ux2 = 0;
    }
    
}
