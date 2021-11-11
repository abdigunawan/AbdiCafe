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

//membuat class dengan nama_kasus 1 (rekening)
public class AbdiGunawan_Kasus1 {
	//inisialisasi variabel untuk class
	String Nasabah = "";
        int Saldo = 0;
        String Alamat = "";
	
	//constructor
	public AbdiGunawan_Kasus1(String Nasabah, int Saldo, String Alamat){
            this.Nasabah = Nasabah;
            this.Saldo = Saldo;
            this.Alamat = Alamat;
	}
        
        // deklarasi method
        public String getNasabah() {
            return Nasabah;
        }

        public void setNama(String Nasabah) {
            this.Nasabah = Nasabah;
        }
        
        public int getSaldo() {
            return Saldo;
        }

        public void setSaldo(int Saldo) {
            this.Saldo = Saldo;
        }
        
        public String getAlamat() {
            return Alamat;
        }

        public void setAlamat(String Alamat) {
            this.Alamat = Alamat;
        }
        
        
	//method untuk mengambil info nama,saldo,dan alamat
	public void info(){
		System.out.println("Nama Nasabah : "+this.Nasabah + ", Saldo : "+this.Saldo + ", Alamat : "+this.Alamat);
	}
        
        public AbdiGunawan_Kasus1(){
		//membuat 3 object dari class Nama_Kasus1
		//membuat object menggunakan keyword new
		AbdiGunawan_Kasus1 Abdi = new AbdiGunawan_Kasus1("Abdi Gunawan", 4000000, "Bone");
                AbdiGunawan_Kasus1 Adrian = new AbdiGunawan_Kasus1("Muhammad Adrian", 500000, "Zimbabwe");
                AbdiGunawan_Kasus1 Dilla = new AbdiGunawan_Kasus1("Nurul Fadillah", 10000000, "Nepal");	
                
		//menjalankan method info
		Abdi.info();
                Adrian.info();
                Dilla.info();
	}
        
        public static void main(String[] args) {
		new AbdiGunawan_Kasus1();

    }

}
