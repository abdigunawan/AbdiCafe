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
public class Nomor1 extends Applet {

    public void paint(Graphics g){
        setBackground (Color.orange);
        g.drawOval(170, 90, 25, 10);
        g.fillOval(219, 91, 9, 7);
        g.drawLine(120, 105, 290, 125);
        g.drawArc(250,124,15,5,-180,180);
        g.fillArc(210,80,20,5,180,170);
        int hair = 50;
        g.setColor(new Color(60, 60, 0));
        g.fillRoundRect(hair, 72, 4, 22, 2, 2);
    }
    
}
