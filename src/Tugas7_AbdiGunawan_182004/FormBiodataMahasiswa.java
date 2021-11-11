/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tugas7_AbdiGunawan_182004;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class FormBiodataMahasiswa extends javax.swing.JFrame {
    
    Koneksi Konek = new Koneksi();
    private Connection Con;
    Statement Stm;
    ResultSet Rs,rsGet;
    String Sql;
    DefaultTableModel Dtm;
    
    JasperReport JR;
    JasperPrint JP;

    public FormBiodataMahasiswa() {
        initComponents();
        KosongkanObjek();
        TampilDataMhsPadaTabel();
        LoadDataProdi();
        LoadDataAgama();
        LoadDataProvinsi();
        LoadDataKelamin();
        LoadTahunMasuk();
    }

    void KosongkanObjek(){
        stb_182004.setText("");
        nama_182004.setText("");
        jurusan_182004.setText("");
        agama_182004.setText("");
        tempatlahir_182004.setText("");
        jkel_182004.setText("");
        alamat_182004.setText("");
        kota_182004.setText("");
        propinsi_182004.setText("");
        kodepos_182004.setText("");
        telepon_182004.setText("");
        handphone_182004.setText("");
        hobi_182004.setText("");
        wali_182004.setText("");
        alamatwali_182004.setText("");
        teleponwali_182004.setText("");
        cari_182004.setText("");
        tanggallahir_182004.setCalendar(null);
 
        jurusan_182004.enable(false);
        agama_182004.enable(false);
        jkel_182004.enable(false);
        propinsi_182004.enable(false);
        stb_182004.requestFocus();
    }
 private void LoadDataProvinsi(){
        String kd="";
        try{
            Con=Konek.getKoneksiDatabase();
            Stm = Con.createStatement();
            Sql = "select * from provinsi_182004_abdigunawan";
            Rs=Stm.executeQuery(Sql);
            while(Rs.next()) {
                cbpropinsi_182004.addItem(Rs.getString("kode"));
            }
        }catch(SQLException e){
            System.out.println("Koneksi Gagal"+e.toString());
        }
    }
    private void LoadDataAgama(){
        String kd="";
        try{
            Con=Konek.getKoneksiDatabase();
            Stm = Con.createStatement();
            Sql = "select * from agama_182004_abdigunawan";
            Rs=Stm.executeQuery(Sql);
            while(Rs.next()) {
                cbAgama_182004.addItem(Rs.getString("kode"));
            }
        }catch(SQLException e){
            System.out.println("Koneksi Gagal"+e.toString());
        }
    }
    
    private void LoadDataProdi(){
        String kd="";
        try{
            Con=Konek.getKoneksiDatabase();
            Stm = Con.createStatement();
            Sql = "select * from program_studi_182004_abdigunawan";
            Rs=Stm.executeQuery(Sql);
            while(Rs.next()) {
                cbJurusan_182004.addItem(Rs.getString("kode_jurusan"));
            }
        } catch(SQLException e){
            System.out.println("Koneksi Gagal"+e.toString());
        }
    }

    private void LoadDataKelamin(){
        cbjkel_182004.addItem("L");
        cbjkel_182004.addItem("P");
    }

    private void LoadTahunMasuk(){
        tahunmasuk_182004.addItem("2020");
        tahunmasuk_182004.addItem("2021");
        tahunmasuk_182004.addItem("2022");
        tahunmasuk_182004.addItem("2023");
    }

    private void AturJTable(JTable Lihat, int Lebar[]){
        try{
            Lihat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            int banyak = Lihat.getColumnCount();
            for (int i = 0; i < banyak; i++) {
                TableColumn Kolom = Lihat.getColumnModel().getColumn(i);
                Kolom.setPreferredWidth(Lebar[i]);
                Lihat.setRowHeight(20);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "salah"+e);
        }
    }
    private void TampilModelJTabel(){
        try {
            String[]kolom={"Stambuk","Nama Mahasiswa","Program Studi",
                "Agama","Tempat Lahir","Tgl Lahir","Jenis Kelamin","Alamat","Kota",
                "Provinsi","Kode Pos", "Telepon","Hanphone","Hobi","Wali","Alamat Wali",
                "Telepon Wali","Tahun Masuk"};
            Dtm = new DefaultTableModel(null, kolom){
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }
            };
            jTable1.setModel(Dtm);
            AturJTable(jTable1, new int
                    []{100,300,300,90,90,90,90,90,90,90,90,90,90,90,90,90,90,90} );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "salah"+e);
        }
    }
 
    void TampilDataMhsPadaTabel(){
        try {
            Con=Konek.getKoneksiDatabase();
            Stm = Con.createStatement();
            TampilModelJTabel();
            try{
                Sql = "SELECT biodata_mahasiswa_182004_abdigunawan.nim ,biodata_mahasiswa_182004_abdigunawan.nama AS NamaMahasiswa, "+
                        "program_studi_182004_abdigunawan.nama AS ProgramStudi, "+
                        "agama_182004_abdigunawan.nama AS NamaAgama, "+
                        "biodata_mahasiswa_182004_abdigunawan.tempat_lahir AS TempatLahir, "+
                        "biodata_mahasiswa_182004_abdigunawan.tanggal_lahir AS TanggalLahir, "+
                        "biodata_mahasiswa_182004_abdigunawan.jenis_kelamin AS JenisKelamin, "+
                        "biodata_mahasiswa_182004_abdigunawan.alamat AS Alamat, "+
                        "biodata_mahasiswa_182004_abdigunawan.kota AS Kota, "+
                        "provinsi_182004_abdigunawan.nama AS NamaProvinsi, "+
                        "biodata_mahasiswa_182004_abdigunawan.kode_pos AS KodePos, "+
                        "biodata_mahasiswa_182004_abdigunawan.telpon AS Telpon, "+
                        "biodata_mahasiswa_182004_abdigunawan.handphone AS HandPhone, "+
                        "biodata_mahasiswa_182004_abdigunawan.hobi AS Hobi, "+
                        "biodata_mahasiswa_182004_abdigunawan.wali AS Wali, "+
                        "biodata_mahasiswa_182004_abdigunawan.alamat_wali AS AlamatWali, "+
                        "biodata_mahasiswa_182004_abdigunawan.telpon_wali AS TelponWali, "+
                        "biodata_mahasiswa_182004_abdigunawan.tahun_masuk AS TahunMasuk "+
                        " FROM "+
                        " biodata_mahasiswa_182004_abdigunawan "+
                        " LEFT OUTER JOIN program_studi_182004_abdigunawan ON(biodata_mahasiswa_182004_abdigunawan.kode_program_studi=program_studi_182004_abdigunawan.kode_jurusan)"+
                        "LEFT OUTER JOIN agama_182004_abdigunawan ON(biodata_mahasiswa_182004_abdigunawan.kode_agama=agama_182004_abdigunawan.kode)"+
                        " LEFT OUTER JOIN provinsi_182004_abdigunawan ON(biodata_mahasiswa_182004_abdigunawan.kode_provinsi=provinsi_182004_abdigunawan.kode)" ;
                Rs = Stm.executeQuery(Sql);
                while(Rs.next()){
                    Dtm.addRow(new Object[]{
                        Rs.getString("nim"),
                     Rs.getString("NamaMahasiswa"),
                     Rs.getString("ProgramStudi"),
                     Rs.getString("NamaAgama"),
                     Rs.getString("TempatLahir"),
                     Rs.getString("TanggalLahir"),
                     Rs.getString("JenisKelamin"),
                     Rs.getString("Alamat"),
                     Rs.getString("Kota"),
                     Rs.getString("NamaProvinsi"),
                     Rs.getString("KodePos"),
                     Rs.getString("Telpon"),
                     Rs.getString("Handphone"),
                     Rs.getString("Hobi"),
                     Rs.getString("Wali"),
                     Rs.getString("AlamatWali"),
                     Rs.getString("TelponWali"),
                     Rs.getString("TahunMasuk"), 
                    });
                    jTable1.setModel(Dtm);
                }
            }catch(Exception e){
                System.out.println("Ada Kesalahan " + e.toString());
            }
        }catch (SQLException e){
            System.out.println("koneksi gagal " + e.toString());
        }
    }

    void CariDataMhs(){
        try {
         Con=Konek.getKoneksiDatabase();
         Stm = Con.createStatement();
         TampilModelJTabel();
         try{

             Sql = "SELECT biodata_mahasiswa_182004_abdigunawan.nim ,biodata_mahasiswa_182004_abdigunawan.nama AS NamaMahasiswa, "+
                     "program_studi_182004_abdigunawan.nama AS ProgramStudi, "+
                     "biodata_mahasiswa_182004_abdigunawan.tempat_lahir AS TempatLahir, "+
                     "biodata_mahasiswa_182004_abdigunawan.jenis_kelamin AS JenisKelamin, "+
                     "biodata_mahasiswa_182004_abdigunawan.alamat AS Alamat, "+
                     "biodata_mahasiswa_182004_abdigunawan.kota AS Kota, "+
                     "provinsi_182004_abdigunawan.nama AS NamaProvinsi, "+
                     "biodata_mahasiswa_182004_abdigunawan.kode_pos AS KodePos, "+
                     "biodata_mahasiswa_182004_abdigunawan.telpon AS Telpon, "+
                     "biodata_mahasiswa_182004_abdigunawan.hobi AS Hobi, "+
                     "biodata_mahasiswa_182004_abdigunawan.alamat_wali AS AlamatWali, "+
                     "biodata_mahasiswa_182004_abdigunawan.tahun_masuk AS TahunMasuk "+
                     " FROM "+
                     " LEFT OUTER JOIN program_studi_182004_abdigunawan ON(biodata_mahasiswa_182004_abdigunawan.kode_program_studi=program_studi_182004_abdigunawan.kode_jurusan)"+
                     " LEFT OUTER JOIN agama_182004_abdigunawan ON(biodata_mahasiswa_182004_abdigunawan.kode_agama=agama_182004_abdigunawan.kode)"+
                     " where biodata_mahasiswa_182004_abdigunawan."+kategoricari_182004.getSelectedItem()+" LIKE '%"+cari_182004.getText()+"%' ";
             System.out.println(kategoricari_182004.getSelectedItem());
             Rs = Stm.executeQuery(Sql);
             while(Rs.next()){
                 Dtm.addRow(new Object[]{
                     Rs.getString("nim"),
                     Rs.getString("NamaMahasiswa"),
                     Rs.getString("ProgramStudi"),
                     Rs.getString("NamaAgama"),
                     Rs.getString("TempatLahir"),
                     Rs.getString("TanggalLahir"),
                     Rs.getString("JenisKelamin"),
                     Rs.getString("Alamat"),
                     Rs.getString("Kota"),
                     Rs.getString("NamaProvinsi"),
                     Rs.getString("KodePos"),
                     Rs.getString("Telpon"),
                     Rs.getString("Handphone"),
                     Rs.getString("Hobi"),
                     Rs.getString("Wali"),
                     Rs.getString("AlamatWali"),
                     Rs.getString("TelponWali"),
                     Rs.getString("TahunMasuk"),
                 });
                 jTable1.setModel(Dtm);
             }
         }catch(Exception e){
             System.out.println("Ada Kesalahan " + e.toString());
         }
        }catch (SQLException e){
            System.out.println("koneksi gagal " + e.toString());
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSpinner1 = new javax.swing.JSpinner();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        kategoricari_182004 = new javax.swing.JComboBox<>();
        cari_182004 = new javax.swing.JTextField();
        btncari_182004 = new javax.swing.JButton();
        btnrefresh_182004 = new javax.swing.JButton();
        btncetak_182004 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        alamatwali_182004 = new javax.swing.JTextField();
        teleponwali_182004 = new javax.swing.JTextField();
        tahunmasuk_182004 = new javax.swing.JComboBox<>();
        nama_182004 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cbJurusan_182004 = new javax.swing.JComboBox<>();
        jurusan_182004 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cbAgama_182004 = new javax.swing.JComboBox<>();
        agama_182004 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tempatlahir_182004 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnsimpan_182004 = new javax.swing.JButton();
        btnedit_182004 = new javax.swing.JButton();
        btnhapus_182004 = new javax.swing.JButton();
        btnbatal_182004 = new javax.swing.JButton();
        btnexit_182004 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        stb_182004 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbjkel_182004 = new javax.swing.JComboBox<>();
        jkel_182004 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        alamat_182004 = new javax.swing.JTextField();
        kota_182004 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        cbpropinsi_182004 = new javax.swing.JComboBox<>();
        propinsi_182004 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        kodepos_182004 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        telepon_182004 = new javax.swing.JTextField();
        handphone_182004 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        hobi_182004 = new javax.swing.JTextField();
        wali_182004 = new javax.swing.JTextField();
        tanggallahir_182004 = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel19.setText("Cari");

        kategoricari_182004.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Pilih]", "nama", "alamat" }));
        kategoricari_182004.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kategoricari_182004ActionPerformed(evt);
            }
        });

        cari_182004.setText("jTextField17");

        btncari_182004.setText("Cari");
        btncari_182004.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncari_182004ActionPerformed(evt);
            }
        });

        btnrefresh_182004.setText("Refresh");
        btnrefresh_182004.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnrefresh_182004ActionPerformed(evt);
            }
        });

        btncetak_182004.setText("Cetak");
        btncetak_182004.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncetak_182004ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 678, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(45, 45, 45)
                        .addComponent(kategoricari_182004, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cari_182004, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(btncari_182004)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnrefresh_182004)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncetak_182004)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(kategoricari_182004, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cari_182004, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncari_182004)
                    .addComponent(btnrefresh_182004)
                    .addComponent(btncetak_182004))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        alamatwali_182004.setText("jTextField15");

        teleponwali_182004.setText("jTextField16");

        tahunmasuk_182004.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Pilih]" }));
        tahunmasuk_182004.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tahunmasuk_182004ActionPerformed(evt);
            }
        });

        nama_182004.setText("jTextField2");

        jLabel3.setText("Kode Prodi");

        cbJurusan_182004.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Pilih]" }));
        cbJurusan_182004.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbJurusan_182004ActionPerformed(evt);
            }
        });

        jurusan_182004.setText("jTextField3");

        jLabel4.setText("Kode Agama");

        cbAgama_182004.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Pilih]" }));
        cbAgama_182004.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAgama_182004ActionPerformed(evt);
            }
        });

        agama_182004.setText("jTextField4");

        jLabel5.setText("T4 Lahir");

        tempatlahir_182004.setText("jTextField5");

        jLabel6.setText("Tgl Lahir");

        btnsimpan_182004.setText("Simpan");
        btnsimpan_182004.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsimpan_182004ActionPerformed(evt);
            }
        });

        btnedit_182004.setText("Edit");
        btnedit_182004.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnedit_182004ActionPerformed(evt);
            }
        });

        btnhapus_182004.setText("Hapus");
        btnhapus_182004.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhapus_182004ActionPerformed(evt);
            }
        });

        btnbatal_182004.setText("Batal");
        btnbatal_182004.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbatal_182004ActionPerformed(evt);
            }
        });

        btnexit_182004.setText("Exit");
        btnexit_182004.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnexit_182004ActionPerformed(evt);
            }
        });

        jLabel1.setText("Stambuk");

        stb_182004.setText("jTextField1");
        stb_182004.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stb_182004ActionPerformed(evt);
            }
        });

        jLabel2.setText("Nama");

        jLabel7.setText("Jenis Kelamin");

        cbjkel_182004.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Pilih]" }));
        cbjkel_182004.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbjkel_182004ActionPerformed(evt);
            }
        });

        jkel_182004.setText("jTextField6");

        jLabel8.setText("Alamat");

        jLabel9.setText("Kota");

        alamat_182004.setText("jTextField7");

        kota_182004.setText("jTextField8");

        jLabel10.setText("Kode Propinsi");

        cbpropinsi_182004.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Pilih]" }));
        cbpropinsi_182004.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbpropinsi_182004ActionPerformed(evt);
            }
        });

        propinsi_182004.setText("jTextField9");

        jLabel11.setText("Kode Pos");

        kodepos_182004.setText("jTextField10");

        jLabel12.setText("Telepon");

        jLabel13.setText("Handphone");

        telepon_182004.setText("jTextField11");

        handphone_182004.setText("jTextField12");

        jLabel14.setText("Hobi");

        jLabel15.setText("Wali");

        jLabel16.setText("Alamat Wali");

        jLabel17.setText("Telpon Wali");

        jLabel18.setText("Tahun Masuk");

        hobi_182004.setText("jTextField13");

        wali_182004.setText("jTextField14");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(stb_182004, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(193, 193, 193))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(tanggallahir_182004, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(kota_182004)
                                    .addComponent(alamat_182004, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(cbjkel_182004, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jkel_182004))
                                    .addComponent(tempatlahir_182004, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(cbAgama_182004, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(agama_182004))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(cbJurusan_182004, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jurusan_182004))
                                    .addComponent(nama_182004))
                                .addGap(24, 24, 24)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel17)
                                .addComponent(jLabel16))
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(teleponwali_182004, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(alamatwali_182004, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(wali_182004, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hobi_182004, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(handphone_182004, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(telepon_182004, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(kodepos_182004, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(cbpropinsi_182004, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(propinsi_182004, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(tahunmasuk_182004, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnsimpan_182004)
                        .addGap(18, 18, 18)
                        .addComponent(btnedit_182004)
                        .addGap(18, 18, 18)
                        .addComponent(btnhapus_182004)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnbatal_182004)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnexit_182004)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnsimpan_182004)
                    .addComponent(btnedit_182004)
                    .addComponent(btnhapus_182004)
                    .addComponent(btnbatal_182004)
                    .addComponent(btnexit_182004))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(stb_182004, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(cbpropinsi_182004, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(propinsi_182004, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(nama_182004, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11)
                        .addComponent(kodepos_182004, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbJurusan_182004, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jurusan_182004, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(telepon_182004, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbAgama_182004, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(agama_182004, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13)
                        .addComponent(handphone_182004, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tempatlahir_182004, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(hobi_182004, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(wali_182004, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel6)
                        .addComponent(tanggallahir_182004, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(alamatwali_182004, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(cbjkel_182004, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jkel_182004, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(alamat_182004, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel17)
                            .addComponent(teleponwali_182004, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tahunmasuk_182004, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18)
                        .addComponent(kota_182004, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel9))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void kategoricari_182004ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kategoricari_182004ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kategoricari_182004ActionPerformed

    private void btncari_182004ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncari_182004ActionPerformed
        // TODO add your handling code here:
        CariDataMhs();
    }//GEN-LAST:event_btncari_182004ActionPerformed

    private void btnrefresh_182004ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrefresh_182004ActionPerformed
        // TODO add your handling code here:
        TampilDataMhsPadaTabel();
        KosongkanObjek();
    }//GEN-LAST:event_btnrefresh_182004ActionPerformed

    private void tahunmasuk_182004ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tahunmasuk_182004ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tahunmasuk_182004ActionPerformed

    private void cbJurusan_182004ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbJurusan_182004ActionPerformed
        // TODO add your handling code here:
        String Kode="";
        try{
            Con=Konek.getKoneksiDatabase();
            Stm = Con.createStatement();
            Sql = "select * from program_studi_182004_abdigunawan where kode_jurusan='"+cbJurusan_182004.getSelectedItem().toString()+"'";
            Rs=Stm.executeQuery(Sql);
            while(Rs.next()) {
                Kode= Rs.getString("nama");
            }
            jurusan_182004.setText(Kode);
        } catch(SQLException e){
            System.out.println("koneksi gagal"+e.toString());
        }
    }//GEN-LAST:event_cbJurusan_182004ActionPerformed

    private void cbAgama_182004ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAgama_182004ActionPerformed
        // TODO add your handling code here:
        String Kode="";
        try{
            Con=Konek.getKoneksiDatabase();
            Stm = Con.createStatement();
            Sql = "select * from agama_182004_abdigunawan where kode='"
            +cbAgama_182004.getSelectedItem().toString()+"'";
            Rs=Stm.executeQuery(Sql);
            while(Rs.next()) {
                Kode= Rs.getString("nama");
            }
            agama_182004.setText(Kode);
        } catch(SQLException e){
            System.out.println("koneksi gagal"+e.toString());
        }
    }//GEN-LAST:event_cbAgama_182004ActionPerformed

    private void btnsimpan_182004ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsimpan_182004ActionPerformed
        // TODO add your handling code here:
        String Tampilan="yyyy-MM-dd";
        SimpleDateFormat Fm = new SimpleDateFormat(Tampilan);
        String TanggalLahir = String.valueOf(Fm.format(tanggallahir_182004.getDate()));
        try {
            Con = Konek.getKoneksiDatabase();
            Stm = null;
            Sql = "insert into biodata_mahasiswa_182004_abdigunawan (id, nim, nama, kode_program_studi,kode_agama, tempat_lahir," +
            "tanggal_lahir, jenis_kelamin, alamat, kota, kode_provinsi, kode_pos,telpon, handphone, hobi, "+
            "wali, alamat_wali, telpon_wali, tahun_masuk, last_update, userid )VALUES (NULL,'"+stb_182004.getText()+"', " +
            "'"+nama_182004.getText()+"', '"+cbJurusan_182004.getSelectedItem()+"','"+cbAgama_182004.getSelectedItem()+"','"+tempatlahir_182004.getText()+"','"+TanggalLahir+"'," + " '"+cbjkel_182004.getSelectedItem()+"', '"+alamat_182004.getText()+"',"+
            "'"+kota_182004.getText()+"', '"+cbpropinsi_182004.getSelectedItem()+"','"+kodepos_182004.getText()+"'," +

            "'"+telepon_182004.getText()+"','"+handphone_182004.getText()+"','"+hobi_182004.getText()+"','"+wali_182004.getText()+"','"+alamatwali_182004.getText()+"', " +

            "'"+teleponwali_182004.getText()+"','"+tahunmasuk_182004.getSelectedItem()+"',NOW(),'ADMIN')";
            Stm= Con.createStatement();
            int AdaPenambahanRecord= Stm.executeUpdate(Sql);
            TampilDataMhsPadaTabel();
            if (AdaPenambahanRecord>0)
            JOptionPane.showMessageDialog(this,"Data Berhasil Tersimpan",
                "Informasi",JOptionPane.INFORMATION_MESSAGE);
            else
            JOptionPane.showMessageDialog(this,"Data Gagal Tersimpan",
                "Informasi",JOptionPane.INFORMATION_MESSAGE);
            Stm.close();
            KosongkanObjek();
            cbJurusan_182004.setSelectedIndex(0);
            cbAgama_182004.setSelectedIndex(0);
            cbjkel_182004.setSelectedIndex(0);
            cbpropinsi_182004.setSelectedIndex(0);
            tahunmasuk_182004.setSelectedIndex(0);

        } catch (SQLException e){
            System.out.println("Koneksi Gagal " +e.toString());
        }
    }//GEN-LAST:event_btnsimpan_182004ActionPerformed

    private void btnedit_182004ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnedit_182004ActionPerformed
        // TODO add your handling code here:
        String Tampilan="yyyy-MM-dd";
        SimpleDateFormat Fm = new SimpleDateFormat(Tampilan);
        String TanggalLahir = String.valueOf(Fm.format(tanggallahir_182004.getDate()));
        try {
            Con = Konek.getKoneksiDatabase();
            Stm = null;
            Sql = "update biodata_mahasiswa_182004_abdigunawan set nama = '"+nama_182004.getText()+"',kode_program_studi ='"+cbJurusan_182004.getSelectedItem()+"' , "
            + "kode_agama = '"+cbAgama_182004.getSelectedItem()+"', tempat_lahir = '"+tempatlahir_182004.getText()+"',"
            + "tanggal_lahir= '"+TanggalLahir+"', jenis_kelamin ='"+cbjkel_182004.getSelectedItem()+"', alamat = '"+alamat_182004.getText()+"', "
            +"kota='"+kota_182004.getText()+"', kode_provinsi ='"+cbpropinsi_182004.getSelectedItem()+"', kode_pos = '"+kodepos_182004.getText()+"',"
            +"telpon = '"+telepon_182004.getText()+"', handphone = '"+handphone_182004.getText()+"',"
            +"hobi = '"+hobi_182004.getText()+"', wali = '"+wali_182004.getText()+"', alamat_wali ='"+alamatwali_182004.getText()+"', telpon_wali='"+teleponwali_182004.getText()+"' ,"
            +"tahun_masuk='"+tahunmasuk_182004.getSelectedItem()+"', last_update= NOW(),userid='ADMIN' where nim = '"+stb_182004.getText()+"' ";

            Stm= Con.createStatement();
            int AdaPerubahanRecord= Stm.executeUpdate(Sql);
            TampilDataMhsPadaTabel();
            if (AdaPerubahanRecord>0){
                JOptionPane.showMessageDialog(this,"Data Berhasil Di Edit",
                    "Informasi",JOptionPane.INFORMATION_MESSAGE);
                btnsimpan_182004.setEnabled(true);
            }else
            JOptionPane.showMessageDialog(this,"Data Gagal Di Edit",
                "Informasi",JOptionPane.INFORMATION_MESSAGE);

            Stm.close();
            KosongkanObjek();
            cbJurusan_182004.setSelectedIndex(0);
            cbAgama_182004.setSelectedIndex(0);
            cbjkel_182004.setSelectedIndex(0);
            cbpropinsi_182004.setSelectedIndex(0);
            tahunmasuk_182004.setSelectedIndex(0);

        } catch (SQLException e){
            System.out.println("Koneksi Gagal " +e.toString());
        }
    }//GEN-LAST:event_btnedit_182004ActionPerformed

    private void btnhapus_182004ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhapus_182004ActionPerformed
        // TODO add your handling code here:
        try {
            Con = Konek.getKoneksiDatabase();
            Stm = null;
            Sql = "delete from biodata_mahasiswa_182004_abdigunawan where nim = '"+stb_182004.getText()+"'";
            Stm= Con.createStatement();
            int AdaPerubahanRecord= Stm.executeUpdate(Sql);
            TampilDataMhsPadaTabel();
            if (AdaPerubahanRecord>0){
                JOptionPane.showMessageDialog(this,"Data Berhasil Di Hapus",
                    "Informasi",JOptionPane.INFORMATION_MESSAGE);
                btnsimpan_182004.setEnabled(true);
            }else
            JOptionPane.showMessageDialog(this,"Data Gagal Di Hapus",
                "Informasi",JOptionPane.INFORMATION_MESSAGE);
            Stm.close();
            KosongkanObjek();
            cbJurusan_182004.setSelectedIndex(0);
            cbAgama_182004.setSelectedIndex(0);
            cbjkel_182004.setSelectedIndex(0);
            cbpropinsi_182004.setSelectedIndex(0);
            tahunmasuk_182004.setSelectedIndex(0);

        } catch (SQLException e){
            System.out.println("Koneksi Gagal " +e.toString());
        }
    }//GEN-LAST:event_btnhapus_182004ActionPerformed

    private void btnbatal_182004ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbatal_182004ActionPerformed
        // TODO add your handling code here:
        KosongkanObjek();
    }//GEN-LAST:event_btnbatal_182004ActionPerformed

    private void btnexit_182004ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnexit_182004ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnexit_182004ActionPerformed

    private void stb_182004ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stb_182004ActionPerformed
        // TODO add your handling code here:
        
        try {
            Con = Konek.getKoneksiDatabase();

            Sql = "Select * from biodata_mahasiswa_182004_abdigunawan where nim='"+stb_182004.getText()+"'";
            Stm= Con.createStatement();
            rsGet = Stm.executeQuery(Sql);
            if (rsGet.next()){
                JOptionPane.showMessageDialog(this,"Stambuk Tersebut Sudah Ada Silahkan Input STB Lain Atau Data Mau Di Edit", "Informasi" ,JOptionPane.INFORMATION_MESSAGE);

                btnsimpan_182004.setEnabled(false);
                nama_182004.setText(rsGet.getString("nama"));
                tempatlahir_182004.setText(rsGet.getString("tempat_lahir"));
                tanggallahir_182004.setDate(rsGet.getDate("tanggal_lahir"));
                cbjkel_182004.setSelectedItem(rsGet.getString("jenis_kelamin"));
                alamat_182004.setText(rsGet.getString("alamat"));
                kota_182004.setText(rsGet.getString("kota"));
                kodepos_182004.setText(rsGet.getString("kode_pos"));
                telepon_182004.setText(rsGet.getString("telpon"));
                handphone_182004.setText(rsGet.getString("handphone"));
                hobi_182004.setText(rsGet.getString("hobi"));
                wali_182004.setText(rsGet.getString("wali"));
                alamatwali_182004.setText(rsGet.getString("alamat_wali"));
                teleponwali_182004.setText(rsGet.getString("telpon_wali"));
                tahunmasuk_182004.setSelectedItem(rsGet.getString("tahun_masuk"));
                cbJurusan_182004.setSelectedItem(rsGet.getString("kode_program_studi"));
                cbAgama_182004.setSelectedItem(rsGet.getString("kode_agama"));
                cbpropinsi_182004.setSelectedItem(rsGet.getString("kode_provinsi"));

            }else{
                btnsimpan_182004.setEnabled(true);
                nama_182004.setText("");
                jurusan_182004.setText("");
                agama_182004.setText("");
                tempatlahir_182004.setText("");
                jkel_182004.setText("");
                alamat_182004.setText("");
                kota_182004.setText("");
                propinsi_182004.setText("");
                kodepos_182004.setText("");
                telepon_182004.setText("");
                handphone_182004.setText("");
                hobi_182004.setText("");
                wali_182004.setText("");
                alamatwali_182004.setText("");
                teleponwali_182004.setText("");
                cari_182004.setText("");
                cbJurusan_182004.setSelectedIndex(0);
                cbAgama_182004.setSelectedIndex(0);
                cbjkel_182004.setSelectedIndex(0);
                cbpropinsi_182004.setSelectedIndex(0);
                tahunmasuk_182004.setSelectedIndex(0);
                nama_182004.requestFocus();
            }

        }catch (SQLException e){
            System.out.println("koneksi gagal " + e.toString());
        }
    }//GEN-LAST:event_stb_182004ActionPerformed

    private void cbjkel_182004ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbjkel_182004ActionPerformed
        // TODO add your handling code here:
        if (cbjkel_182004.getSelectedItem().equals("L")){
            jkel_182004.setText("Laki - Laki");
        }else{
            jkel_182004.setText("Perempuan");
        }
    }//GEN-LAST:event_cbjkel_182004ActionPerformed

    private void cbpropinsi_182004ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbpropinsi_182004ActionPerformed
        // TODO add your handling code here:
        String Kode="";
        try{
            Con=Konek.getKoneksiDatabase();
            Stm = Con.createStatement();
            Sql = "select * from provinsi_182004_abdigunawan where kode='"+cbpropinsi_182004.getSelectedItem().toString()+"'";
            Rs=Stm.executeQuery(Sql);
            while(Rs.next()) {
                Kode= Rs.getString("nama");
            }
            propinsi_182004.setText(Kode);
        } catch(SQLException e){
            System.out.println("koneksi gagal"+e.toString());
        }
    }//GEN-LAST:event_cbpropinsi_182004ActionPerformed

    private void btncetak_182004ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncetak_182004ActionPerformed
        // TODO add your handling code here:
        CetakMhs view = new CetakMhs();
        view.show();
    }//GEN-LAST:event_btncetak_182004ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormBiodataMahasiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormBiodataMahasiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormBiodataMahasiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormBiodataMahasiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormBiodataMahasiswa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField agama_182004;
    private javax.swing.JTextField alamat_182004;
    private javax.swing.JTextField alamatwali_182004;
    private javax.swing.JButton btnbatal_182004;
    private javax.swing.JButton btncari_182004;
    private javax.swing.JButton btncetak_182004;
    private javax.swing.JButton btnedit_182004;
    private javax.swing.JButton btnexit_182004;
    private javax.swing.JButton btnhapus_182004;
    private javax.swing.JButton btnrefresh_182004;
    private javax.swing.JButton btnsimpan_182004;
    private javax.swing.JTextField cari_182004;
    private javax.swing.JComboBox<String> cbAgama_182004;
    private javax.swing.JComboBox<String> cbJurusan_182004;
    private javax.swing.JComboBox<String> cbjkel_182004;
    private javax.swing.JComboBox<String> cbpropinsi_182004;
    private javax.swing.JTextField handphone_182004;
    private javax.swing.JTextField hobi_182004;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jkel_182004;
    private javax.swing.JTextField jurusan_182004;
    private javax.swing.JComboBox<String> kategoricari_182004;
    private javax.swing.JTextField kodepos_182004;
    private javax.swing.JTextField kota_182004;
    private javax.swing.JTextField nama_182004;
    private javax.swing.JTextField propinsi_182004;
    private javax.swing.JTextField stb_182004;
    private javax.swing.JComboBox<String> tahunmasuk_182004;
    private com.toedter.calendar.JDateChooser tanggallahir_182004;
    private javax.swing.JTextField telepon_182004;
    private javax.swing.JTextField teleponwali_182004;
    private javax.swing.JTextField tempatlahir_182004;
    private javax.swing.JTextField wali_182004;
    // End of variables declaration//GEN-END:variables
}
