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
public class Neko extends Applet implements Runnable {

    Image nekopics[] = new Image[9];
    String nekosrc[] = { "sleep1.gif", "right1.gif", "stop.gif", 
                         "yawn.gif", "scratch1.gif", "scratch2.gif", 
                         "right2.gif", "sleep2.gif", "awake.gif" };
    
    Image currentimg;
    Thread runner;
    int xpos;
    int ypos = 50;
    
    public void start() {
        if (runner == null) {
            runner = new Thread(this);
            runner.start();
        }
    }
    
    public void stop() {
        if (runner != null) {
            runner.stop();
            runner = null;
        }
    }
    
    public void run() {
        //initialize, file file gambar berada pada direktori "images/"
        for ( int i = 0 ; i < nekopics.length ; i++) {
            nekopics[i] = getImage(getCodeBase(), "images/" + nekosrc[i]);
        }
        
        //warna latar belakang adalah putih
        setBackground(Color.white);
        
        //lakukan animasi hingga 100 kali
        for ( int idx = 0 ; idx < 100 ; idx++) {
            //panggil fungsi untuk menggerakkan kucing kekanan hingga tengah
            nekorun(0, this.size().width / 2);
            
            //stop and pause kucing
            currentimg = nekopics[2];
            repaint();
            pause(100);
            
            //yawn mengantuk
            currentimg = nekopics[3];
            repaint();
            pause(1000);
            
            //scratch four times, menggaruk
            nekoscratch(4);
            
            //scratch four times, menggaruk
            nekosleep(5);
            
            //wakeup and lari lagi
            currentimg = nekopics[8];
            repaint();
            pause(500);
            nekorun(xpos, this.size().width + 10);
            
        }
    }
    
    //fungsi untuk menggerakkan kucing kekanan
    void nekorun(int start, int end) {
        for (int i = start; i < end;i+=10) {
            this.xpos = i;
            
            //swap images
            if (currentimg == nekopics[0]) {
                currentimg = nekopics[1];
            }
                
            else if (currentimg == nekopics[1]) {
                currentimg = nekopics[0];   
            }
            else {
                currentimg = nekopics[0];
            } 
            
            repaint();
            pause(150);
        }
    }
    
    //fungsi untuk menampilkan kucing menggaruk
    void nekoscratch(int numtimes) {
        for (int i = numtimes; i > 0; i--) {
            currentimg = nekopics[4];
            repaint();
            pause(150);
            currentimg = nekopics[5];
            repaint();
            pause(150);
        }
    }
    
    //fungsi untuk menampilkan kucing tidur
    void nekosleep (int numtimes) {
        for (int i = numtimes; i > 0; i--) {
            currentimg = nekopics[6];
            repaint();
            pause(250);
            currentimg = nekopics[7];
            repaint();
            pause(250);
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
