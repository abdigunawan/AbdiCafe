/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tugas3_AbdiGunawan_182004;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author abdig
 */
public class Nomor2 extends Applet {

        public void paint(Graphics g){
           
            setBackground (Color.orange);
            
            //head
            g.drawOval(250, 50, 100, 120);
            //eye
            g.drawOval(270, 90, 25, 10);
            g.fillOval(319, 91, 9, 7);
            g.drawOval(315, 90, 25, 10);
            g.fillOval(274, 91, 9, 7);
            //nose
            g.drawLine(300, 105, 290, 125);
            g.drawArc(290,124,15,5,-180,180);
            //eyebrow
            g.fillArc(270,80,20,5,180,170);
            g.fillArc(315,80,20,5,180,170);
            //mouth
            g.setColor(Color.black);
            g.fillArc(275,125,50,30,0,-180);
            //  hair
            int hair = 250;
            g.setColor(new Color(60, 60, 0));
            g.fillRoundRect(hair, 72, 4, 22, 2, 2);
            g.fillRoundRect(hair += 6, 65, 4, 20, 2, 2);
            g.fillRoundRect(hair += 6, 60, 4, 20, 2, 2);
            g.fillRoundRect(hair += 6, 56, 4, 20, 2, 2);
            g.fillRoundRect(hair += 6, 52, 4, 20, 2, 2);
            g.fillRoundRect(hair += 6, 49, 4, 20, 2, 2);
            g.fillRoundRect(hair += 6, 47, 4, 20, 2, 2);
            g.fillRoundRect(hair += 6, 46, 4, 20, 2, 2);
            g.fillRoundRect(hair += 6, 45, 4, 20, 2, 2);
            g.fillRoundRect(hair += 6, 46, 4, 20, 2, 2);
            g.fillRoundRect(hair += 6, 47, 4, 20, 2, 2);
            g.fillRoundRect(hair += 6, 49, 4, 20, 2, 2);
            g.fillRoundRect(hair += 6, 52, 4, 20, 2, 2);
            g.fillRoundRect(hair += 6, 56, 4, 20, 2, 2);
            g.fillRoundRect(hair += 6, 60, 4, 20, 2, 2);
            g.fillRoundRect(hair += 6, 65, 4, 20, 2, 2);
            g.fillRoundRect(hair += 6, 72, 4, 22, 2, 2);
            
            Font f = new Font ("Abdi Gunawan",Font.BOLD, 32);
            g.setFont(f);
            g.setColor(Color.white);
            g.drawString("Abdi Gunawan",190,210);
        }
    
}
