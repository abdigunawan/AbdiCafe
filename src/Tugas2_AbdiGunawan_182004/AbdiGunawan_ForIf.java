/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tugas2_AbdiGunawan_182004;

import java.util.Scanner;

/**
 *
 * @author abdig
 */

public class AbdiGunawan_ForIf {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc = new Scanner(System.in);
        
        String kdBrg,jnsBrg,nmBrg,ulang;
        int hrgBrg,jmlhBeli,data=1;
        double diskon,jmlhByr,ttlByr;
        
        for ( ulang="Y"; !"N".equals(ulang) ; data++ ) {
            //input data
            System.out.println("Data Ke - "+data);
            System.out.print("Input Kode Barang : " );
            kdBrg = sc.next();
            System.out.print("Input Jenis Barang : " );
            jnsBrg = sc.next();
            System.out.print("Input Jumlah Beli : " );
            jmlhBeli = sc.nextInt();

            //proses penentuan nama barang berdasarkan jenis barang
            if ("A".equals(jnsBrg)){
                nmBrg = "Baju Kemeja";
                hrgBrg = 50000;
            }
            else if ("B".equals(jnsBrg)){
                nmBrg = "Celana Panjang";
                hrgBrg = 70000;
            }
            else if ("C".equals(jnsBrg)){
                nmBrg = "Topi";
                hrgBrg = 80000;
            }
            else if ("D".equals(jnsBrg)){
                nmBrg = "Sepatu";
                hrgBrg = 100000;
            }
            else {
                nmBrg = "Tidak Ditemukan";
                hrgBrg = 0;
            }

            jmlhByr = hrgBrg * jmlhBeli;

            //proses penentuan diskon berdasarkan jumlah beli
            if ( jmlhBeli <= 5 ) {
                diskon = 0;
            }
            else {
                diskon = 0.10 * jmlhByr;
            }


            ttlByr = jmlhByr - diskon;

            //output
            System.out.println();
            System.out.println("DATA PEMBELIAN BARANG");
            System.out.println("Kode Barang "+ kdBrg + " Nama Barang " + nmBrg);
            System.out.println("Harga Barang : Rp."+ hrgBrg );
            System.out.println("Jumlah Beli : "+ jmlhBeli );
            System.out.println("Jumlah Bayar : Rp."+ jmlhByr );
            System.out.println("Diskon : "+ diskon );
            System.out.println("Total Bayar : Rp."+ ttlByr );
            System.out.println();
            System.out.print("Masih Ada Lagi? [Y/N]");
            ulang = sc.next();
            System.out.println();
        } 
    }
}
