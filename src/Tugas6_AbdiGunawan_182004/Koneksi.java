/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tugas6_AbdiGunawan_182004;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {

    private Object koneksiDatabase;
    
    public Connection getKoneksiDatabase(){
        if(koneksiDatabase == null){
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    System.out.println("Driver Ditemukan");
                    try{
                        koneksiDatabase = DriverManager.getConnection("jdbc:mysql://localhost:3306/abdigunawan_182004","root","");
                        System.out.println("Koneksi Database Ditemukan");
                    }catch(SQLException ex){
                        System.out.println("Koneksi Database Tidak Ditemukan :\n Dengan Pesan :"+ ex.toString());
                    }
                }catch(ClassNotFoundException ex){
                    System.out.println("Class Driver Tidak Ditemukan :\n Dengan Pesan Error"+ ex.toString());
            }     
        }
            return (Connection) koneksiDatabase;
    }   
}