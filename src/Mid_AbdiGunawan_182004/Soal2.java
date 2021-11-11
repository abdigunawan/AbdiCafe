/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mid_AbdiGunawan_182004;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author abdig
 */
public class Soal2 extends Applet  implements Runnable {
    
    Thread kejar;
    int jarakPencuri = 50, jarakPolisi = 1;
    final int speedPolisi = 3, speedPencuri = 2;
   
    Font f;
    String teks = "POLISI";
    String teks2 = "PENCURI";
    
    
    public void start(){
        if(kejar == null){
            kejar = new Thread(this);
            kejar.start();
        }
    }
    public void stop(){
        if(kejar!= null){
            kejar = null;
            kejar.stop();
        }
    }
    public void run(){
     while(jarakPolisi < jarakPencuri){
         try{
            Thread.sleep(100);
            jarakPolisi += speedPolisi;
            jarakPencuri += speedPencuri;
            repaint();
         }catch(InterruptedException e){
         }
     }
    }
    public void paint(Graphics g){
        f = new Font("Helvetica", Font.BOLD, 24);
        g.setColor(Color.GREEN);
        g.setFont(f);
        g.drawString(teks,5,jarakPolisi);
        g.setColor(Color.RED);       
        g.drawString(teks2,100,jarakPencuri);
     
        if(jarakPolisi == jarakPencuri) {
            g.setColor(Color.GREEN);
            g.drawString("Pencuri berhasil ditangkap",5,jarakPencuri+25);
        }
    }
}
