/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Quiz1;

/**
 *
 * @author abdig
 */

// membuat class tanpa constructor (Rekening)
public class AbdiGunawan_Kasus1_TanpaConstructor {
       	String Nasabah = "";
        int Saldo = 0;
        String Alamat = "";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //membuat objek abdi 
        AbdiGunawan_Kasus1_TanpaConstructor abdi = new AbdiGunawan_Kasus1_TanpaConstructor();
        abdi.Nasabah = "Abdi Gunawan";
        abdi.Saldo = 7500000;
        abdi.Alamat = "Watampone";
        
        //membuat objek gifa 
        AbdiGunawan_Kasus1_TanpaConstructor gifa = new AbdiGunawan_Kasus1_TanpaConstructor();
        gifa.Nasabah = "Adam Gifari";
        gifa.Saldo = 123000;
        gifa.Alamat = "Amerika";
        
        //membuat objek azizah 
        AbdiGunawan_Kasus1_TanpaConstructor azizah = new AbdiGunawan_Kasus1_TanpaConstructor();
        azizah.Nasabah = "Nurul Azizah";
        azizah.Saldo = 50000;
        azizah.Alamat = "Malaysia";

        //Method menampilkan Objek Abdi
        String tampilAbdi = " Nasabah Dengan Nama " + abdi.Nasabah + " Memiliki Saldo " + abdi.Saldo + " Dengan Alamat " + abdi.Alamat;
        System.out.println(tampilAbdi);

        //Method menampilkan Objek Gifa        
        String tampilGifa = " Nasabah Dengan Nama " + gifa.Nasabah + " Memiliki Saldo " + gifa.Saldo + " Dengan Alamat " + gifa.Alamat;
        System.out.println(tampilGifa);
        
        //Method menampilkan Objek Azizah
        String tampilAzizah = " Nasabah Dengan Nama " + azizah.Nasabah + " Memiliki Saldo " + azizah.Saldo + " Dengan Alamat " + azizah.Alamat;
        System.out.println(tampilAzizah);              
    }
    
}

