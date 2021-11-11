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
import java.io.*;

public class AbdiGunawan_no1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        DataInputStream in = new DataInputStream(System.in);
        char c;
        int angka=0;
        char huruf=' ';
        String kalimat=" ";
        System.out.print("Mencoba Fungsi DataInputStream \n");

        /* menerima input String lalu dirubah ke Integer */

        try {
            System.out.print("Enter a number : ");
            kalimat = in.readLine();
            angka = Integer.parseInt(kalimat);
        }
        catch(IOException e){
            System.out.println(e); 
        }
        

        /* menerima karakter dari input-stream */
        try {
            System.out.print("Enter a character : ");
            kalimat = in.readLine();
            huruf = kalimat.charAt(0);
        }catch(IOException e) {
            System.out.println(e); 
        }
        
        
        /* menerima String dari input-stream */ 
        try {
            System.out.print("Enter some words : ");
            kalimat = in.readLine();
        }catch(IOException e) {
            System.out.println(e); 
        }

        
        /* hasil input di tampilkan */ 
        System.out.println("Here are what you type in : ");
        System.out.println(angka);
        System.out.println(huruf);
        System.out.println(kalimat);
    }
}
