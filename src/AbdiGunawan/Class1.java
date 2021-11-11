/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AbdiGunawan;

/**
 *
 * @author abdig
 */
public class Class1 {
	//inisialisasi variabel untuk class
	public String Nasabah = "";
        public int Saldo = 0;
        public String Alamat = "";
    
        //Konstruktor
        public void Kelas1(String Nasabah, int Saldo, String Alamat){
            Nasabah = Nasabah;
            Saldo = Saldo;
            Alamat = Alamat; 
        }
	//method untuk mencetak info nama,saldo,dan alamat
	public void info(){
		System.out.println("Nama Nasabah : "+this.Nasabah);
                System.out.println("Saldo : "+this.Saldo);
                System.out.println("Alamat : "+this.Alamat);
        }
        
        public void garis(){
            System.out.println("============================================");
        }
}