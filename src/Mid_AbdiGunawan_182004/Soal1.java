/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mid_AbdiGunawan_182004;

import java.util.Scanner;

/**
 *
 * @author abdig
 */
class Peternakan {
        Scanner input = new Scanner (System.in);
        int grosir,lusin,butir;
        
        void hitungTelur() {
            System.out.println("Masukkan Jumlah Telur :"); 
            butir=input.nextInt();
            grosir= butir /(12*12);
            butir = butir -((12*12)*grosir);
            lusin = butir / 12;
            butir = butir - (12*lusin);
        }
   
        void tampilTelur() { 
            System.out.println("Grosir = " + grosir + " Lusin =" + lusin + " Butir = " + butir);
        }
}


public class Soal1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Peternakan ternak = new Peternakan();
        ternak.hitungTelur();
        ternak.tampilTelur();
    }
}
