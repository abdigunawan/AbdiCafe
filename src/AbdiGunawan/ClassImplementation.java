/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AbdiGunawan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author abdig
 */
public class ClassImplementation {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

                //mengambil variabel dari class1
                Class1 data = new Class1();
               
                try{
                //membuat input dan memasukkan ke variabel dari class 1
                System.out.print("Input Nama anda : ");
                String nasabah = br.readLine();
                data.Nasabah=nasabah;
                System.out.print("Input Saldo anda : ");
                String ksaldo = br.readLine();
                int saldo = Integer.parseInt(ksaldo);
                data.Saldo=saldo;
                System.out.print("Input Alamat anda : ");
                String alamat = br.readLine();
                data.Alamat=alamat;
                // menjalankan method yang ada pada class1
                data.garis();
                data.info(); 
                }catch(Exception e){
                System.out.print(e.toString());
                System.exit(0);
                }
                          
    }   
}
