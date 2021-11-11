/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tugas4_AbdiGunawan_182004;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

/**
 *
 * @author abdig
 */
public class AbdiRun extends Applet implements Runnable {
    
        Image abdipics[] = new Image[11];
        String abdisrc[] = { "run1.gif", "run2.gif", "run3.gif", 
                             "run4.gif", "run5.gif", "run6.gif",
                             "run7.gif", "run8.gif", "run9.gif",
                             "run10.gif", "run11.gif" };
        
        Image currentimg;
        Thread runner;
        int xpos,ypos;
        
    public void start() {
        if (runner == null) {
            runner = new Thread(this);
            runner.start();
        }
    }
    
    
    public void run() {
        //initialize, file file gambar berada pada direktori "images/"
        for ( int i = 0 ; i < abdipics.length ; i++) {
            abdipics[i] = getImage(getCodeBase(), "images/" + abdisrc[i]);
        }
        
        //warna latar belakang adalah putih
        setBackground(Color.white);
        
        //lakukan animasi hingga 100 kali
        for ( int idx = 100 ; idx > 0 ; idx--) {
            //panggil fungsi untuk menggerakkan kucing kekanan hingga tengah
            abdirun(1, this.size().width);            
        }
    }
    
    //fungsi untuk menggerakkan kucing kekiri
    void abdirun(int start, int end) {
        for (xpos = 1700;xpos>5;xpos-=10) {
            //swap images
            if (currentimg == abdipics[0]) {
                currentimg = abdipics[1];
            }   
            else if (currentimg == abdipics[1]) {
                currentimg = abdipics[2];   
            }
            else if (currentimg == abdipics[2]) {
                currentimg = abdipics[3];   
            }
            else if (currentimg == abdipics[3]) {
                currentimg = abdipics[4];   
            }
            else if (currentimg == abdipics[4]) {
                currentimg = abdipics[5];   
            }
            else if (currentimg == abdipics[5]) {
                currentimg = abdipics[6];   
            }
            else if (currentimg == abdipics[6]) {
                currentimg = abdipics[7];   
            }
            else if (currentimg == abdipics[7]) {
                currentimg = abdipics[8];   
            }
            else if (currentimg == abdipics[8]) {
                currentimg = abdipics[9];   
            }
            else if (currentimg == abdipics[9]) {
                currentimg = abdipics[10];   
            }
            else if (currentimg == abdipics[10]) {
                currentimg = abdipics[0];   
            }
            else {
                currentimg = abdipics[0];
            } 
            
            repaint();
            pause(100);
        }
    }
    
    void pause (int time) {
        try {
            Thread.sleep(time);
        }
        catch(InterruptedException e) {}
    }
    
    public void paint(Graphics g) {
        g.drawImage(currentimg,xpos,ypos,this);
    }
 
}
