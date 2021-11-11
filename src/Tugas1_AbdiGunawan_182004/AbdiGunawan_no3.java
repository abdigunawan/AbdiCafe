/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tugas1_AbdiGunawan_182004;

import java.util.*;

/**
 *
 * @author abdig
 */
public class AbdiGunawan_no3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc = new Scanner(System.in);
        
        char c;
        int angka=0;
        char huruf=' ';
        String kalimat=" ";
        System.out.print("Mencoba Fungsi Scanner \n");

        /* menerima input String lalu dirubah ke Integer */
        try {
            System.out.print("Enter a number : ");
            kalimat = sc.nextLine();
            angka = Integer.parseInt(kalimat);
        }
        catch(Exception e){
            System.out.println(e); 
        }
        

        /* menerima karakter dari BufferedReader */
        try {
            System.out.print("Enter a character : ");
            kalimat = sc.nextLine();
            huruf = kalimat.charAt(0);
        }catch(Exception e) {
            System.out.println(e); 
        }
        
        
        /* menerima String dari BufferedReader */ 
        try {
            System.out.print("Enter some words : ");
            kalimat = sc.nextLine();
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
