/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tugas1_AbdiGunawan_182004;

/**
 *
 * @author abdig
 */
import javax.swing.*;

public class AbdiGunawan_no4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic herechar c;
        
        int angka=0;
        char huruf=' ';
        String kalimat=" ";       
        String welcome =" ";
        JFrame f=new JFrame();
        JOptionPane.showMessageDialog(f,"Mencoba Fitur JOptionPane");

        /* menerima input String lalu dirubah ke Integer */
        try {
            kalimat = JOptionPane.showInputDialog(null, "Enter a number :");
            angka = Integer.parseInt(kalimat);
        }
        catch(Exception e){
            System.out.println(e); 
        }
        

        /* menerima karakter dari BufferedReader */
        try {
            kalimat = JOptionPane.showInputDialog(null, "Enter a character :");
            huruf = kalimat.charAt(0);
        }catch(Exception e) {
            System.out.println(e); 
        }
        
        
        /* menerima String dari BufferedReader */ 
        try {
            kalimat = JOptionPane.showInputDialog(null, "Enter a number :");
        }catch(Exception e) {
            System.out.println(e); 
        }

        
        /* hasil input di tampilkan */ 
        System.out.println("Here are what you type in : ");
        System.out.println(angka);
        System.out.println(huruf);
        System.out.println(kalimat);
    }
    
}
