/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PackageImplementasi;

import AbdiGunawan.Class1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author abdig
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            Class1 kelas = new Class1();
            
            try{
                //membuat input dan memasukkan ke variabel dari class 1
                System.out.print("Input Nama anda : ");
                String nasabah = br.readLine();
                kelas.Nasabah=nasabah;
                System.out.print("Input Saldo anda : ");
                String ksaldo = br.readLine();
                int saldo = Integer.parseInt(ksaldo);
                kelas.Saldo=saldo;
                System.out.print("Input Alamat anda : ");
                String alamat = br.readLine();
                kelas.Alamat=alamat;
                // menjalankan method yang ada pada class1
                kelas.garis();
                kelas.info(); 
                }catch(Exception e){
                System.out.print(e.toString());
                System.exit(0);
                }
    }
}
