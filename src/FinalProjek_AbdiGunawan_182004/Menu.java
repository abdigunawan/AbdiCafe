/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package FinalProjek_AbdiGunawan_182004;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author abdig
 */
public class Menu extends javax.swing.JFrame {

    Koneksi Konek = new Koneksi();
    private Connection Con;
    Statement Stm;
    ResultSet Rs,rsGet;
    String Sql;
    DefaultTableModel Dtm,Dtm1,Dtm2,Dtm3;
 
    JasperReport JR;
    JasperPrint JP;

    //Menu Pemesanan
    String idPemesanan,idMejaPemesanan;
    
    //Menu Pembayaran
    String idPembayaran;
    
    //Menu Cafe
    String idMenuCafe;
    
    //
    
    /** Creates new form Menu */
    public Menu() {
        initComponents();
        
        //Saat Pertama Diload
        panelHome.setVisible(true);
        panelPemesanan.setVisible(false);
        panelPembayaran.setVisible(false);
        panelMenu.setVisible(false);
        panelPegawai.setVisible(false);
        panelLaporan.setVisible(false);
        tab1.setBackground(new Color(160,116,70));
        
        //Menu DashBoard
        LoadDataTransaksiHariIni();
        LoadDataNgopiSekarang();
        
        //Menu Pemesanan
        TampilPemesanan();
        LoadDataMakanan();
        LoadDataMinuman();
        LoadMeja();
        totalPesanan.setEditable(false);
        txtPesananPemesanan.setEditable(false);
        totalPembayaran.setEnabled(false);
        txtIdMejaPemesanan.setEditable(false);
        
        
        //Menu Pembayaran
        TampilPembayaran();
        txtNamaPemesan1.setEditable(false);
        totalPesanan1.setEditable(false);
        txtMejaPemesanan.setEditable(false);
        txtKembalian.setEnabled(false);
        totalPembayaran1.setEnabled(false);
        
        
        //Menu Cafe
        TampilMenuCafe();
        AddKategoriMenu();
        
        
        //Menu Pegawai
        AddJabatanPegawai();
        TampilPegawai();
        
        
    }
    
    //Menu Dashboard
    private void LoadDataTransaksiHariIni(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String datenow = dateFormat.format(date);
        
        try{
            Con=Konek.getKoneksiDatabase();
            Stm = Con.createStatement();
            Sql = "select COUNT(id) from tbl_transaksi where tanggalpesanan='"+datenow+"' AND status ='Sudah Bayar' ";
            Rs=Stm.executeQuery(Sql);
            while(Rs.next()) {
                jLabel26.setText("Transaksi Hari ini : " + Rs.getString("COUNT(id)"));
            }
        }catch(SQLException e){
            System.out.println("Koneksi Gagal"+e.toString());
        }
    }
    private void LoadDataNgopiSekarang(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String datenow = dateFormat.format(date);
        
        try{
            Con=Konek.getKoneksiDatabase();
            Stm = Con.createStatement();
            Sql = "select COUNT(id) from tbl_transaksi where tanggalpesanan='"+datenow+"' AND status ='Belum Bayar' ";
            Rs=Stm.executeQuery(Sql);
            while(Rs.next()) {
                jLabel19.setText("Sedang Ngopi : " + Rs.getString("COUNT(id)"));
            }
        }catch(SQLException e){
            System.out.println("Koneksi Gagal"+e.toString());
        }
    }
    
    
    //Menu Pemesanan
    private void LoadDataMakanan(){
        cbMakanan.removeAllItems();
        cbMakanan.addItem("Pilih Makanan");
        try{
            Con=Konek.getKoneksiDatabase();
            Stm = Con.createStatement();
            Sql = "select * from tbl_menu where kategori='Makanan' ";
            Rs=Stm.executeQuery(Sql);
            while(Rs.next()) {
                cbMakanan.addItem(Rs.getString("nama"));
            }
        }catch(SQLException e){
            System.out.println("Koneksi Gagal"+e.toString());
        }
    }
    private void LoadDataMinuman(){
        cbMinuman.removeAllItems();
        cbMinuman.addItem("Pilih Minuman");
        try{
            Con=Konek.getKoneksiDatabase();
            Stm = Con.createStatement();
            Sql = "select * from tbl_menu where kategori='Minuman' ";
            Rs=Stm.executeQuery(Sql);
            while(Rs.next()) {
                cbMinuman.addItem(Rs.getString("nama"));
            }
        }catch(SQLException e){
            System.out.println("Koneksi Gagal"+e.toString());
        }
    }
    private void LoadMeja(){
        ClearCbMejaPemesanan();
        try{
            Con=Konek.getKoneksiDatabase();
            Stm = Con.createStatement();
            Sql = "select * from tbl_meja where status='Tersedia' ";
            Rs=Stm.executeQuery(Sql);
            while(Rs.next()) {
                cbMejaPemesanan.addItem(Rs.getString("meja"));
            }
        }catch(SQLException e){
            System.out.println("Koneksi Gagal"+e.toString());
        }
    }
    void ClearCbMejaPemesanan() {
        cbMejaPemesanan.removeAllItems();
        cbMejaPemesanan.addItem("Pilih Id Meja");
    }
    
    private void TampilPemesananJTabel(){
        try {
            String[]kolom={"Id Pemesanan","Nama Pemesan","Meja","Pesanan","Total","Tanggal Pesanan","Status Pesanan"};
            Dtm = new DefaultTableModel(null, kolom){
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }
            };
            jTable1.setModel(Dtm);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "salah"+e);
        }
    }
    void TampilPemesanan(){
        try {
            Con=Konek.getKoneksiDatabase();
            Stm = Con.createStatement();
            TampilPemesananJTabel();
            try{
                Sql = "SELECT tbl_transaksi.id,tbl_transaksi.namapemesan,tbl_meja.meja,tbl_transaksi.pesanan,tbl_transaksi.total,tbl_transaksi.tanggalpesanan,tbl_transaksi.status from tbl_transaksi join tbl_meja on tbl_transaksi.mejaid = tbl_meja.id order by tbl_transaksi.id DESC";
                Rs = Stm.executeQuery(Sql);
                while(Rs.next()){
                    Dtm.addRow(new Object[]{
                        Rs.getString("id"),
                        Rs.getString("namapemesan"),
                        Rs.getString("meja"),
                        Rs.getString("pesanan"),
                        Rs.getString("total"),
                        Rs.getString("tanggalpesanan"),
                        Rs.getString("status"),
                        
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
    void BersihPemesanan() {
        txtNamaPemesan.setText("");
        cbMakanan.setSelectedIndex(0);
        cbMinuman.setSelectedIndex(0);
        totalPesanan.setText("");
        quantityPemesanan.setValue(0);
        cbMejaPemesanan.setSelectedIndex(0);
        txtIdMejaPemesanan.setText("");
        txtNamaPemesan.setText("");
        tglPemesanan.setCalendar(null);
        
    }
    
    
    
    //Menu Pembayaran
    void BersihPembayaran() {
        txtNamaPemesan1.setText("");
        totalPesanan1.setText("");
        totalPembayaran1.setValue(0);
        totalPembayaran2.setValue(0);
        txtKembalian.setValue(0);
        txtMejaPemesanan.setText("");
    }
    private void TampilPembayaranJTabel(){
        try {
            String[]kolom={"ID Pemesanan","Nama Pemesan","ID Meja","Pesanan","Total","Tanggal Pesanan"};
            Dtm1 = new DefaultTableModel(null, kolom){
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }
            };
            jTable2.setModel(Dtm1);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "salah"+e);
        }
    }
    void TampilPembayaran(){
        try {
            Con=Konek.getKoneksiDatabase();
            Stm = Con.createStatement();
            TampilPembayaranJTabel();
            try{
                Sql = "SELECT tbl_transaksi.id,tbl_transaksi.namapemesan,tbl_transaksi.mejaid,tbl_transaksi.pesanan,tbl_transaksi.total,tbl_transaksi.tanggalpesanan from tbl_transaksi where status='Belum Bayar' ";
                Rs = Stm.executeQuery(Sql);
                while(Rs.next()){
                    Dtm1.addRow(new Object[]{
                        Rs.getString("id"),
                        Rs.getString("namapemesan"),
                        Rs.getString("mejaid"),
                        Rs.getString("pesanan"),
                        Rs.getString("total"),
                        Rs.getString("tanggalpesanan"),
                    });
                    jTable2.setModel(Dtm1);
                    
                }
            }catch(Exception e){
                System.out.println("Ada Kesalahan " + e.toString());
            }
        }catch (SQLException e){
            System.out.println("koneksi gagal " + e.toString());
        }
    }
    
    
    //Menu Cafe
    void AddKategoriMenu() {
        cbKategoriMenuCafe.addItem("Makanan");
        cbKategoriMenuCafe.addItem("Minuman");
    }
    private void TampilMenuCafeJTabel(){
        try {
            String[]kolom={"Id Menu","Nama Menu","Kategori","Harga"};
            Dtm2 = new DefaultTableModel(null, kolom){
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }
            };
            jTable3.setModel(Dtm2);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "salah"+e);
        }
    }
    void TampilMenuCafe(){
        try {
            Con=Konek.getKoneksiDatabase();
            Stm = Con.createStatement();
            TampilMenuCafeJTabel();
            try{
                Sql = "SELECT * FROM tbl_menu";
                Rs = Stm.executeQuery(Sql);
                while(Rs.next()){
                    Dtm2.addRow(new Object[]{
                        Rs.getString("id"),
                        Rs.getString("nama"),
                        Rs.getString("kategori"),
                        Rs.getString("harga"),
                    });
                    jTable3.setModel(Dtm2);
                    
                }
            }catch(Exception e){
                System.out.println("Ada Kesalahan " + e.toString());
            }
        }catch (SQLException e){
            System.out.println("koneksi gagal " + e.toString());
        }
    }
    void BersihkanMenuCafe(){
        txtNamaMenuCafe.setText("");
        txtCariMenu.setText("");
        cbKategoriMenuCafe.setSelectedIndex(0);
        txtHargaMenu.setValue(0);
    }
    
    
    //Menu Pegawai
    void AddJabatanPegawai() {
        cbJabatanPegawai.addItem("Pilih Jabatan");
        cbJabatanPegawai.addItem("Kasir");
        cbJabatanPegawai.addItem("Waiter");
    }
    
    private void TampilMenuPegawaiJTabel(){
        try {
            String[]kolom={"Nip Pegawai","Nama Lengkap","Alamat","Jenis Kelamin","Jabatan","Email","Username"};
            Dtm3 = new DefaultTableModel(null, kolom){
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }
            };
            jTable4.setModel(Dtm3);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "salah"+e);
        }
    }
    void TampilPegawai(){
        try {
            Con=Konek.getKoneksiDatabase();
            Stm = Con.createStatement();
            TampilMenuPegawaiJTabel();
            try{
                Sql = "SELECT tbl_pegawai.nip,tbl_pegawai.namalengkap,tbl_pegawai.alamat,tbl_pegawai.jkel,tbl_role.role,tbl_pegawai.username,tbl_pegawai.email"
                   + " FROM tbl_pegawai JOIN tbl_role on tbl_pegawai.roleid = tbl_role.id where tbl_role.role='Waiter' OR tbl_role.role='Kasir' ";
                Rs = Stm.executeQuery(Sql);
                while(Rs.next()){
                    Dtm3.addRow(new Object[]{
                        Rs.getString("nip"),
                        Rs.getString("namalengkap"),
                        Rs.getString("alamat"),
                        Rs.getString("jkel"),
                        Rs.getString("role"),
                        Rs.getString("email"),
                        Rs.getString("username"),
                    });
                    jTable4.setModel(Dtm3);
                    
                }
            }catch(Exception e){
                System.out.println("Ada Kesalahan " + e.toString());
            }
        }catch (SQLException e){
            System.out.println("koneksi gagal " + e.toString());
        }
    }
    void BersihkanPegawai (){
        txtNipPegawai.setText("");
        txtNamaPegawai.setText("");
        txtAlamatPegawai.setText("");
        buttonGroup1.clearSelection();
        cbJabatanPegawai.setSelectedIndex(0);
        txtEmailPegawai.setText("");
        txtUsernamePegawai.setText("");
        txtPasswordPegawai.setText("");
        txtUsernamePegawai.setEnabled(true);
        txtPasswordPegawai.setEnabled(true);
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
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        panelkiri = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tab1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tab2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        tab3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        tab4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        tab5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        tab6 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        tab7 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        paneltengah = new javax.swing.JPanel();
        panelHome = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        panelPemesanan = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        txtNamaPemesan = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        cbMinuman = new javax.swing.JComboBox<>();
        cbMakanan = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        totalPesanan = new javax.swing.JTextArea();
        jLabel15 = new javax.swing.JLabel();
        addPesanan = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        cbMejaPemesanan = new javax.swing.JComboBox<>();
        txtIdMejaPemesanan = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        tglPemesanan = new com.toedter.calendar.JDateChooser();
        btnPesanPemesanan = new javax.swing.JButton();
        btnHapusPemesanan = new javax.swing.JButton();
        btnClearPemesanan = new javax.swing.JButton();
        quantityPemesanan = new javax.swing.JSpinner();
        txtPesananPemesanan = new javax.swing.JTextField();
        clearPesanan = new javax.swing.JButton();
        totalPembayaran = new javax.swing.JSpinner();
        panelPembayaran = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        txtNamaPemesan1 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        totalPesanan1 = new javax.swing.JTextArea();
        totalPembayaran1 = new javax.swing.JSpinner();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txtMejaPemesanan = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        totalPembayaran2 = new javax.swing.JSpinner();
        jLabel30 = new javax.swing.JLabel();
        txtKembalian = new javax.swing.JSpinner();
        btnClearPemesanan1 = new javax.swing.JButton();
        btnBayarPesanan = new javax.swing.JButton();
        btnBayarPesanan1 = new javax.swing.JButton();
        panelMenu = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        cbKategoriMenuCafe = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel31 = new javax.swing.JLabel();
        txtNamaMenuCafe = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        txtHargaMenu = new javax.swing.JSpinner();
        btnSimpanMenu = new javax.swing.JButton();
        btnEditMenu = new javax.swing.JButton();
        btnHapusMenu = new javax.swing.JButton();
        btnClearMenu = new javax.swing.JButton();
        txtCariMenu = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        panelPegawai = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jLabel35 = new javax.swing.JLabel();
        txtNipPegawai = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        txtNamaPegawai = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        txtAlamatPegawai = new javax.swing.JTextArea();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        txtEmailPegawai = new javax.swing.JTextField();
        rbLaki = new javax.swing.JRadioButton();
        RbPerempuan = new javax.swing.JRadioButton();
        jLabel40 = new javax.swing.JLabel();
        cbJabatanPegawai = new javax.swing.JComboBox<>();
        jLabel41 = new javax.swing.JLabel();
        txtUsernamePegawai = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        txtPasswordPegawai = new javax.swing.JTextField();
        btnSimpanPegawai = new javax.swing.JButton();
        btnEditPegawai = new javax.swing.JButton();
        btnHapusPegawai = new javax.swing.JButton();
        btnClearPegawai = new javax.swing.JButton();
        panelLaporan = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        btnSimpanPegawai1 = new javax.swing.JButton();
        btnSimpanPegawai2 = new javax.swing.JButton();
        jLabel44 = new javax.swing.JLabel();
        tglAwalCetak = new com.toedter.calendar.JDateChooser();
        tglAkhirCetak = new com.toedter.calendar.JDateChooser();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        btnSimpanPegawai3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelkiri.setBackground(new java.awt.Color(254, 232, 208));

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\abdig\\Documents\\NetBeansProjects\\AbdiGunawan_182004_PBOKelasA\\asset\\logo.png")); // NOI18N

        tab1.setBackground(new java.awt.Color(96, 56, 19));
        tab1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab1MouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Home");

        javax.swing.GroupLayout tab1Layout = new javax.swing.GroupLayout(tab1);
        tab1.setLayout(tab1Layout);
        tab1Layout.setHorizontalGroup(
            tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab1Layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tab1Layout.setVerticalGroup(
            tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tab2.setBackground(new java.awt.Color(96, 56, 19));
        tab2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab2MouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Pemesanan");
        jLabel5.setToolTipText("");

        javax.swing.GroupLayout tab2Layout = new javax.swing.GroupLayout(tab2);
        tab2.setLayout(tab2Layout);
        tab2Layout.setHorizontalGroup(
            tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab2Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tab2Layout.setVerticalGroup(
            tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tab3.setBackground(new java.awt.Color(96, 56, 19));
        tab3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab3MouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Pembayaran");
        jLabel6.setToolTipText("");

        javax.swing.GroupLayout tab3Layout = new javax.swing.GroupLayout(tab3);
        tab3.setLayout(tab3Layout);
        tab3Layout.setHorizontalGroup(
            tab3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab3Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tab3Layout.setVerticalGroup(
            tab3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tab4.setBackground(new java.awt.Color(96, 56, 19));
        tab4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab4MouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Menu Cafe");
        jLabel7.setToolTipText("");

        javax.swing.GroupLayout tab4Layout = new javax.swing.GroupLayout(tab4);
        tab4.setLayout(tab4Layout);
        tab4Layout.setHorizontalGroup(
            tab4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab4Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tab4Layout.setVerticalGroup(
            tab4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tab5.setBackground(new java.awt.Color(96, 56, 19));
        tab5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab5MouseClicked(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Pegawai");
        jLabel9.setToolTipText("");

        javax.swing.GroupLayout tab5Layout = new javax.swing.GroupLayout(tab5);
        tab5.setLayout(tab5Layout);
        tab5Layout.setHorizontalGroup(
            tab5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(86, 86, 86))
        );
        tab5Layout.setVerticalGroup(
            tab5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tab6.setBackground(new java.awt.Color(96, 56, 19));
        tab6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab6MouseClicked(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Laporan");
        jLabel10.setToolTipText("");

        javax.swing.GroupLayout tab6Layout = new javax.swing.GroupLayout(tab6);
        tab6.setLayout(tab6Layout);
        tab6Layout.setHorizontalGroup(
            tab6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab6Layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tab6Layout.setVerticalGroup(
            tab6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tab7.setBackground(new java.awt.Color(96, 56, 19));
        tab7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab7MouseClicked(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Log Out");
        jLabel11.setToolTipText("");

        javax.swing.GroupLayout tab7Layout = new javax.swing.GroupLayout(tab7);
        tab7.setLayout(tab7Layout);
        tab7Layout.setHorizontalGroup(
            tab7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab7Layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tab7Layout.setVerticalGroup(
            tab7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelkiriLayout = new javax.swing.GroupLayout(panelkiri);
        panelkiri.setLayout(panelkiriLayout);
        panelkiriLayout.setHorizontalGroup(
            panelkiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelkiriLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel1)
                .addContainerGap(37, Short.MAX_VALUE))
            .addComponent(tab1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tab2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tab3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tab4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tab5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tab6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tab7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelkiriLayout.setVerticalGroup(
            panelkiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelkiriLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(54, 54, 54)
                .addComponent(tab1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tab2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tab3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tab4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tab5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tab6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tab7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(91, Short.MAX_VALUE))
        );

        paneltengah.setBackground(new java.awt.Color(96, 56, 19));

        panelHome.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(96, 56, 19));

        jLabel19.setFont(new java.awt.Font("Tw Cen MT", 1, 36)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Sedang Ngopi : ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(78, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabel19)
                .addContainerGap(61, Short.MAX_VALUE))
        );

        jLabel20.setFont(new java.awt.Font("Tw Cen MT", 1, 36)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(96, 56, 19));
        jLabel20.setText("Home");

        jPanel6.setBackground(new java.awt.Color(96, 56, 19));

        jLabel26.setFont(new java.awt.Font("Tw Cen MT", 1, 36)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Transaksi Hari ini :");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 589, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(69, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabel26)
                .addContainerGap(61, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelHomeLayout = new javax.swing.GroupLayout(panelHome);
        panelHome.setLayout(panelHomeLayout);
        panelHomeLayout.setHorizontalGroup(
            panelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHomeLayout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addGroup(panelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelHomeLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(94, 94, 94))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelHomeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addGap(403, 403, 403))
        );
        panelHomeLayout.setVerticalGroup(
            panelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHomeLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel20)
                .addGap(64, 64, 64)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelPemesanan.setBackground(new java.awt.Color(255, 255, 255));
        panelPemesanan.setToolTipText("");
        panelPemesanan.setName(""); // NOI18N
        panelPemesanan.setPreferredSize(new java.awt.Dimension(920, 0));

        jLabel12.setFont(new java.awt.Font("Tw Cen MT", 1, 36)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(96, 56, 19));
        jLabel12.setText("Pemesanan");

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
        jTable1.setSelectionBackground(new java.awt.Color(160, 116, 70));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel13.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel13.setText("Nama Pemesan");

        txtNamaPemesan.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel14.setText("Pesanan ");

        cbMinuman.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        cbMinuman.setMaximumRowCount(30);
        cbMinuman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMinumanActionPerformed(evt);
            }
        });

        cbMakanan.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        cbMakanan.setMaximumRowCount(30);
        cbMakanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMakananActionPerformed(evt);
            }
        });

        totalPesanan.setColumns(20);
        totalPesanan.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        totalPesanan.setRows(5);
        jScrollPane2.setViewportView(totalPesanan);

        jLabel15.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel15.setText("Total Pembayaran");

        addPesanan.setText("Tambah Pesanan");
        addPesanan.setActionCommand("Clear Pesanan");
        addPesanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPesananActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel16.setText("Pilih Meja");
        jLabel16.setToolTipText("");

        cbMejaPemesanan.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        cbMejaPemesanan.setMaximumRowCount(30);
        cbMejaPemesanan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbMejaPemesananItemStateChanged(evt);
            }
        });
        cbMejaPemesanan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbMejaPemesananMouseClicked(evt);
            }
        });
        cbMejaPemesanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMejaPemesananActionPerformed(evt);
            }
        });

        txtIdMejaPemesanan.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        txtIdMejaPemesanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdMejaPemesananActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        jLabel17.setText("ID Meja :");
        jLabel17.setToolTipText("");

        jLabel18.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel18.setText("Tanggal Pesanan");
        jLabel18.setToolTipText("");

        tglPemesanan.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N

        btnPesanPemesanan.setBackground(new java.awt.Color(96, 56, 19));
        btnPesanPemesanan.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnPesanPemesanan.setForeground(new java.awt.Color(255, 255, 255));
        btnPesanPemesanan.setText("Pesan");
        btnPesanPemesanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesanPemesananActionPerformed(evt);
            }
        });

        btnHapusPemesanan.setBackground(new java.awt.Color(96, 56, 19));
        btnHapusPemesanan.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnHapusPemesanan.setForeground(new java.awt.Color(255, 255, 255));
        btnHapusPemesanan.setText("Hapus");
        btnHapusPemesanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusPemesananActionPerformed(evt);
            }
        });

        btnClearPemesanan.setBackground(new java.awt.Color(96, 56, 19));
        btnClearPemesanan.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnClearPemesanan.setForeground(new java.awt.Color(255, 255, 255));
        btnClearPemesanan.setText("Clear");
        btnClearPemesanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearPemesananActionPerformed(evt);
            }
        });

        quantityPemesanan.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N

        txtPesananPemesanan.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        txtPesananPemesanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesananPemesananActionPerformed(evt);
            }
        });

        clearPesanan.setText("ClearPesanan");
        clearPesanan.setActionCommand("Clear Pesanan");
        clearPesanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearPesananActionPerformed(evt);
            }
        });

        totalPembayaran.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N

        javax.swing.GroupLayout panelPemesananLayout = new javax.swing.GroupLayout(panelPemesanan);
        panelPemesanan.setLayout(panelPemesananLayout);
        panelPemesananLayout.setHorizontalGroup(
            panelPemesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPemesananLayout.createSequentialGroup()
                .addGroup(panelPemesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPemesananLayout.createSequentialGroup()
                        .addGap(364, 364, 364)
                        .addComponent(jLabel12))
                    .addGroup(panelPemesananLayout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 780, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelPemesananLayout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jLabel13)
                        .addGap(47, 47, 47)
                        .addComponent(txtNamaPemesan, javax.swing.GroupLayout.PREFERRED_SIZE, 621, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelPemesananLayout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jLabel14)
                        .addGap(94, 94, 94)
                        .addComponent(cbMinuman, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cbMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelPemesananLayout.createSequentialGroup()
                        .addGap(271, 271, 271)
                        .addComponent(btnPesanPemesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(btnHapusPemesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(btnClearPemesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelPemesananLayout.createSequentialGroup()
                        .addGap(227, 227, 227)
                        .addGroup(panelPemesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPesananPemesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelPemesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelPemesananLayout.createSequentialGroup()
                                .addComponent(addPesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(clearPesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(quantityPemesanan)
                            .addComponent(jLabel15)
                            .addComponent(totalPembayaran)))
                    .addGroup(panelPemesananLayout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addGroup(panelPemesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelPemesananLayout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(34, 34, 34)
                                .addComponent(tglPemesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelPemesananLayout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(88, 88, 88)
                                .addComponent(cbMejaPemesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17)
                        .addGap(33, 33, 33)
                        .addComponent(txtIdMejaPemesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(72, 72, 72))
        );
        panelPemesananLayout.setVerticalGroup(
            panelPemesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPemesananLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel12)
                .addGap(31, 31, 31)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(panelPemesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addGroup(panelPemesananLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txtNamaPemesan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28)
                .addGroup(panelPemesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addGroup(panelPemesananLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(panelPemesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbMinuman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(panelPemesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPesananPemesanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(quantityPemesanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(panelPemesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelPemesananLayout.createSequentialGroup()
                        .addGroup(panelPemesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(clearPesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addPesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(totalPembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(panelPemesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPemesananLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(panelPemesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addGroup(panelPemesananLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jLabel17))
                            .addGroup(panelPemesananLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(txtIdMejaPemesanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelPemesananLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(cbMejaPemesanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28)
                .addGroup(panelPemesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addGroup(panelPemesananLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(tglPemesanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addGroup(panelPemesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPesanPemesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapusPemesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClearPemesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        panelPembayaran.setBackground(new java.awt.Color(255, 255, 255));
        panelPembayaran.setPreferredSize(new java.awt.Dimension(920, 0));

        jLabel21.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel21.setText("Nama Pemesan");

        txtNamaPemesan1.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable2.setSelectionBackground(new java.awt.Color(160, 116, 70));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable2);

        jLabel24.setFont(new java.awt.Font("Tw Cen MT", 1, 36)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(96, 56, 19));
        jLabel24.setText("Pembayaran");

        jLabel25.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel25.setText("Pesanan ");

        totalPesanan1.setColumns(20);
        totalPesanan1.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        totalPesanan1.setRows(5);
        jScrollPane4.setViewportView(totalPesanan1);

        totalPembayaran1.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N

        jLabel27.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel27.setText("Total Pembayaran");

        jLabel28.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel28.setText("Id Meja :");

        txtMejaPemesanan.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N

        jLabel29.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel29.setText("Uang Pelanggan :");

        totalPembayaran2.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N

        jLabel30.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel30.setText("Kembalian : ");

        txtKembalian.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N

        btnClearPemesanan1.setBackground(new java.awt.Color(96, 56, 19));
        btnClearPemesanan1.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnClearPemesanan1.setForeground(new java.awt.Color(255, 255, 255));
        btnClearPemesanan1.setText("Clear");
        btnClearPemesanan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearPemesanan1ActionPerformed(evt);
            }
        });

        btnBayarPesanan.setBackground(new java.awt.Color(96, 56, 19));
        btnBayarPesanan.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnBayarPesanan.setForeground(new java.awt.Color(255, 255, 255));
        btnBayarPesanan.setText("Bayar");
        btnBayarPesanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBayarPesananActionPerformed(evt);
            }
        });

        btnBayarPesanan1.setBackground(new java.awt.Color(96, 56, 19));
        btnBayarPesanan1.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnBayarPesanan1.setForeground(new java.awt.Color(255, 255, 255));
        btnBayarPesanan1.setText("Hitung");
        btnBayarPesanan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBayarPesanan1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPembayaranLayout = new javax.swing.GroupLayout(panelPembayaran);
        panelPembayaran.setLayout(panelPembayaranLayout);
        panelPembayaranLayout.setHorizontalGroup(
            panelPembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPembayaranLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBayarPesanan1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(btnBayarPesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(btnClearPemesanan1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(254, 254, 254))
            .addGroup(panelPembayaranLayout.createSequentialGroup()
                .addContainerGap(75, Short.MAX_VALUE)
                .addGroup(panelPembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPembayaranLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 780, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelPembayaranLayout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(47, 47, 47)
                        .addComponent(txtNamaPemesan1, javax.swing.GroupLayout.PREFERRED_SIZE, 621, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelPembayaranLayout.createSequentialGroup()
                        .addGroup(panelPembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelPembayaranLayout.createSequentialGroup()
                                .addGap(296, 296, 296)
                                .addComponent(jLabel24))
                            .addGroup(panelPembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(panelPembayaranLayout.createSequentialGroup()
                                    .addComponent(jLabel25)
                                    .addGap(94, 94, 94)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addGroup(panelPembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(totalPembayaran1)
                                        .addGroup(panelPembayaranLayout.createSequentialGroup()
                                            .addComponent(jLabel27)
                                            .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(panelPembayaranLayout.createSequentialGroup()
                                            .addComponent(jLabel28)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                                            .addComponent(txtMejaPemesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelPembayaranLayout.createSequentialGroup()
                                    .addGroup(panelPembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(totalPembayaran2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(panelPembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtKembalian, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(67, 67, 67))))
        );
        panelPembayaranLayout.setVerticalGroup(
            panelPembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPembayaranLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel24)
                .addGap(31, 31, 31)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelPembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addGroup(panelPembayaranLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txtNamaPemesan1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(32, 32, 32)
                .addGroup(panelPembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelPembayaranLayout.createSequentialGroup()
                        .addGroup(panelPembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addComponent(jLabel27))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalPembayaran1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelPembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28)
                            .addComponent(txtMejaPemesanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(panelPembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelPembayaranLayout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalPembayaran2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelPembayaranLayout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(64, 64, 64)
                .addGroup(panelPembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBayarPesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClearPemesanan1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBayarPesanan1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(68, 68, 68))
        );

        panelMenu.setBackground(new java.awt.Color(255, 255, 255));
        panelMenu.setPreferredSize(new java.awt.Dimension(920, 0));

        jLabel22.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel22.setText("Kategori ");

        cbKategoriMenuCafe.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        cbKategoriMenuCafe.setMaximumRowCount(30);
        cbKategoriMenuCafe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Menu" }));
        cbKategoriMenuCafe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbKategoriMenuCafeActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Tw Cen MT", 1, 36)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(96, 56, 19));
        jLabel23.setText("Menu Cafe");

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable3.setSelectionBackground(new java.awt.Color(160, 116, 70));
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTable3);

        jLabel31.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel31.setText("Nama Menu");

        txtNamaMenuCafe.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N

        jLabel32.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel32.setText("Harga");

        txtHargaMenu.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N

        btnSimpanMenu.setBackground(new java.awt.Color(96, 56, 19));
        btnSimpanMenu.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnSimpanMenu.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpanMenu.setText("Simpan");
        btnSimpanMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanMenuActionPerformed(evt);
            }
        });

        btnEditMenu.setBackground(new java.awt.Color(96, 56, 19));
        btnEditMenu.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnEditMenu.setForeground(new java.awt.Color(255, 255, 255));
        btnEditMenu.setText("Edit");
        btnEditMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditMenuActionPerformed(evt);
            }
        });

        btnHapusMenu.setBackground(new java.awt.Color(96, 56, 19));
        btnHapusMenu.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnHapusMenu.setForeground(new java.awt.Color(255, 255, 255));
        btnHapusMenu.setText("Hapus");
        btnHapusMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusMenuActionPerformed(evt);
            }
        });

        btnClearMenu.setBackground(new java.awt.Color(96, 56, 19));
        btnClearMenu.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnClearMenu.setForeground(new java.awt.Color(255, 255, 255));
        btnClearMenu.setText("Clear");
        btnClearMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearMenuActionPerformed(evt);
            }
        });

        txtCariMenu.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        txtCariMenu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCariMenuKeyPressed(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel33.setText("Cari Menu");

        javax.swing.GroupLayout panelMenuLayout = new javax.swing.GroupLayout(panelMenu);
        panelMenu.setLayout(panelMenuLayout);
        panelMenuLayout.setHorizontalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenuLayout.createSequentialGroup()
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelMenuLayout.createSequentialGroup()
                        .addGap(376, 376, 376)
                        .addComponent(jLabel23))
                    .addGroup(panelMenuLayout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 780, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(panelMenuLayout.createSequentialGroup()
                                    .addComponent(jLabel31)
                                    .addGap(74, 74, 74)
                                    .addComponent(txtNamaMenuCafe, javax.swing.GroupLayout.PREFERRED_SIZE, 621, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(panelMenuLayout.createSequentialGroup()
                                    .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel22)
                                        .addComponent(jLabel32))
                                    .addGap(94, 94, 94)
                                    .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(cbKategoriMenuCafe, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtHargaMenu)))
                                .addGroup(panelMenuLayout.createSequentialGroup()
                                    .addGap(446, 446, 446)
                                    .addComponent(jLabel33)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtCariMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(71, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMenuLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnSimpanMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(btnEditMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(btnHapusMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(btnClearMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(177, 177, 177))
        );
        panelMenuLayout.setVerticalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenuLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel23)
                .addGap(31, 31, 31)
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCariMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(txtNamaMenuCafe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addGroup(panelMenuLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(cbKategoriMenuCafe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(txtHargaMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapusMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpanMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClearMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66))
        );

        panelPegawai.setBackground(new java.awt.Color(255, 255, 255));
        panelPegawai.setPreferredSize(new java.awt.Dimension(920, 0));

        jLabel34.setFont(new java.awt.Font("Tw Cen MT", 1, 36)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(96, 56, 19));
        jLabel34.setText("Pegawai");

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable4.setSelectionBackground(new java.awt.Color(160, 116, 70));
        jTable4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable4MouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(jTable4);

        jLabel35.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel35.setText("Nip Pegawai");

        txtNipPegawai.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N

        jLabel36.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel36.setText("Nama Pegawai");

        txtNamaPegawai.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N

        jLabel37.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel37.setText("Alamat");

        txtAlamatPegawai.setColumns(20);
        txtAlamatPegawai.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        txtAlamatPegawai.setRows(5);
        txtAlamatPegawai.setToolTipText("");
        txtAlamatPegawai.setWrapStyleWord(true);
        txtAlamatPegawai.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jScrollPane7.setViewportView(txtAlamatPegawai);

        jLabel38.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel38.setText("Email");

        jLabel39.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel39.setText("Jenis Kelamin");

        txtEmailPegawai.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N

        rbLaki.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rbLaki);
        rbLaki.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        rbLaki.setText("Laki Laki");

        RbPerempuan.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(RbPerempuan);
        RbPerempuan.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        RbPerempuan.setText("Perempuan");

        jLabel40.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel40.setText("Jabatan");

        cbJabatanPegawai.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        cbJabatanPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbJabatanPegawaiActionPerformed(evt);
            }
        });

        jLabel41.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel41.setText("Username");

        txtUsernamePegawai.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N

        jLabel42.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel42.setText("Password");

        txtPasswordPegawai.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N

        btnSimpanPegawai.setBackground(new java.awt.Color(96, 56, 19));
        btnSimpanPegawai.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnSimpanPegawai.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpanPegawai.setText("Simpan");
        btnSimpanPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanPegawaiActionPerformed(evt);
            }
        });

        btnEditPegawai.setBackground(new java.awt.Color(96, 56, 19));
        btnEditPegawai.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnEditPegawai.setForeground(new java.awt.Color(255, 255, 255));
        btnEditPegawai.setText("Edit");
        btnEditPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditPegawaiActionPerformed(evt);
            }
        });

        btnHapusPegawai.setBackground(new java.awt.Color(96, 56, 19));
        btnHapusPegawai.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnHapusPegawai.setForeground(new java.awt.Color(255, 255, 255));
        btnHapusPegawai.setText("Hapus");
        btnHapusPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusPegawaiActionPerformed(evt);
            }
        });

        btnClearPegawai.setBackground(new java.awt.Color(96, 56, 19));
        btnClearPegawai.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnClearPegawai.setForeground(new java.awt.Color(255, 255, 255));
        btnClearPegawai.setText("Clear");
        btnClearPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearPegawaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPegawaiLayout = new javax.swing.GroupLayout(panelPegawai);
        panelPegawai.setLayout(panelPegawaiLayout);
        panelPegawaiLayout.setHorizontalGroup(
            panelPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPegawaiLayout.createSequentialGroup()
                .addGroup(panelPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPegawaiLayout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addGroup(panelPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(panelPegawaiLayout.createSequentialGroup()
                                    .addComponent(jLabel35)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtNipPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 621, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(panelPegawaiLayout.createSequentialGroup()
                                    .addGroup(panelPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPegawaiLayout.createSequentialGroup()
                                            .addGroup(panelPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel36, javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel37)
                                                .addComponent(jLabel38)
                                                .addComponent(jLabel39))
                                            .addGap(47, 47, 47))
                                        .addGroup(panelPegawaiLayout.createSequentialGroup()
                                            .addComponent(jLabel41)
                                            .addGap(89, 89, 89)))
                                    .addGroup(panelPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtNamaPegawai, javax.swing.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
                                        .addComponent(jScrollPane7)
                                        .addComponent(txtEmailPegawai, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(panelPegawaiLayout.createSequentialGroup()
                                            .addComponent(rbLaki)
                                            .addGap(29, 29, 29)
                                            .addComponent(RbPerempuan)
                                            .addGap(49, 49, 49)
                                            .addComponent(jLabel40)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cbJabatanPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(panelPegawaiLayout.createSequentialGroup()
                                            .addComponent(txtUsernamePegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel42)
                                            .addGap(89, 89, 89)
                                            .addComponent(txtPasswordPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(panelPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(panelPegawaiLayout.createSequentialGroup()
                                    .addGap(321, 321, 321)
                                    .addComponent(jLabel34)
                                    .addGap(0, 329, Short.MAX_VALUE))
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 780, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelPegawaiLayout.createSequentialGroup()
                        .addGap(174, 174, 174)
                        .addComponent(btnSimpanPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(btnEditPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(btnHapusPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(btnClearPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(72, Short.MAX_VALUE))
        );
        panelPegawaiLayout.setVerticalGroup(
            panelPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPegawaiLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel34)
                .addGap(31, 31, 31)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(panelPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNipPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35))
                .addGap(30, 30, 30)
                .addGroup(panelPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNamaPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36))
                .addGap(33, 33, 33)
                .addGroup(panelPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel37)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(panelPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(rbLaki)
                    .addComponent(RbPerempuan)
                    .addComponent(jLabel40)
                    .addComponent(cbJabatanPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(panelPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(txtEmailPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(panelPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUsernamePegawai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41)
                    .addComponent(txtPasswordPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapusPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpanPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClearPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33))
        );

        panelLaporan.setBackground(new java.awt.Color(255, 255, 255));
        panelLaporan.setPreferredSize(new java.awt.Dimension(920, 0));

        jLabel43.setFont(new java.awt.Font("Tw Cen MT", 1, 36)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(96, 56, 19));
        jLabel43.setText("Laporan Transaksi");

        btnSimpanPegawai1.setBackground(new java.awt.Color(96, 56, 19));
        btnSimpanPegawai1.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnSimpanPegawai1.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpanPegawai1.setText("Transaksi Hari Ini");
        btnSimpanPegawai1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanPegawai1ActionPerformed(evt);
            }
        });

        btnSimpanPegawai2.setBackground(new java.awt.Color(96, 56, 19));
        btnSimpanPegawai2.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnSimpanPegawai2.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpanPegawai2.setText("Transaksi Bulan Ini");
        btnSimpanPegawai2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanPegawai2ActionPerformed(evt);
            }
        });

        jLabel44.setFont(new java.awt.Font("Tw Cen MT", 1, 36)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(96, 56, 19));
        jLabel44.setText("Pilih Tanggal Yang Akan Dicetak");

        tglAwalCetak.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N

        tglAkhirCetak.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N

        jLabel45.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(96, 56, 19));
        jLabel45.setText("Tanggal awal : ");

        jLabel46.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(96, 56, 19));
        jLabel46.setText("Tanggal Akhir : ");

        btnSimpanPegawai3.setBackground(new java.awt.Color(96, 56, 19));
        btnSimpanPegawai3.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnSimpanPegawai3.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpanPegawai3.setText("Cetak");
        btnSimpanPegawai3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanPegawai3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelLaporanLayout = new javax.swing.GroupLayout(panelLaporan);
        panelLaporan.setLayout(panelLaporanLayout);
        panelLaporanLayout.setHorizontalGroup(
            panelLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLaporanLayout.createSequentialGroup()
                .addGroup(panelLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(panelLaporanLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(tglAwalCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(74, 74, 74)
                            .addComponent(tglAkhirCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelLaporanLayout.createSequentialGroup()
                            .addGap(131, 131, 131)
                            .addComponent(btnSimpanPegawai1, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(73, 73, 73)
                            .addComponent(btnSimpanPegawai2, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel44)
                        .addGroup(panelLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(panelLaporanLayout.createSequentialGroup()
                                .addGap(92, 92, 92)
                                .addComponent(jLabel45)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel46))
                            .addGroup(panelLaporanLayout.createSequentialGroup()
                                .addGap(62, 62, 62)
                                .addComponent(jLabel43)
                                .addGap(221, 221, 221))))
                    .addGroup(panelLaporanLayout.createSequentialGroup()
                        .addGap(314, 314, 314)
                        .addComponent(btnSimpanPegawai3, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(145, Short.MAX_VALUE))
        );
        panelLaporanLayout.setVerticalGroup(
            panelLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLaporanLayout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabel43)
                .addGap(54, 54, 54)
                .addGroup(panelLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpanPegawai1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpanPegawai2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(69, 69, 69)
                .addComponent(jLabel44)
                .addGap(53, 53, 53)
                .addGroup(panelLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel45)
                    .addComponent(jLabel46))
                .addGap(18, 18, 18)
                .addGroup(panelLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tglAkhirCetak, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tglAwalCetak, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(85, 85, 85)
                .addComponent(btnSimpanPegawai3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(106, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout paneltengahLayout = new javax.swing.GroupLayout(paneltengah);
        paneltengah.setLayout(paneltengahLayout);
        paneltengahLayout.setHorizontalGroup(
            paneltengahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelHome, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(paneltengahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelPemesanan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 923, Short.MAX_VALUE))
            .addGroup(paneltengahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelPembayaran, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 923, Short.MAX_VALUE))
            .addGroup(paneltengahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelMenu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 923, Short.MAX_VALUE))
            .addGroup(paneltengahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelPegawai, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 923, Short.MAX_VALUE))
            .addGroup(paneltengahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelLaporan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 923, Short.MAX_VALUE))
        );
        paneltengahLayout.setVerticalGroup(
            paneltengahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(paneltengahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelPemesanan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 722, Short.MAX_VALUE))
            .addGroup(paneltengahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelPembayaran, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 722, Short.MAX_VALUE))
            .addGroup(paneltengahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelMenu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 722, Short.MAX_VALUE))
            .addGroup(paneltengahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelPegawai, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 722, Short.MAX_VALUE))
            .addGroup(paneltengahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelLaporan, javax.swing.GroupLayout.DEFAULT_SIZE, 722, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(257, 257, 257)
                .addComponent(paneltengah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(panelkiri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(paneltengah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelkiri, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tab1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab1MouseClicked
        // TODO add your handling code here:
        panelHome.setVisible(true);
        panelPemesanan.setVisible(false);
        panelPembayaran.setVisible(false);
        panelMenu.setVisible(false);
        panelPegawai.setVisible(false);
        panelLaporan.setVisible(false);
        tab1.setBackground(new Color(160,116,70));
        tab2.setBackground(new Color(96,56,19));
        tab3.setBackground(new Color(96,56,19));
        tab4.setBackground(new Color(96,56,19));
        tab5.setBackground(new Color(96,56,19));
        tab6.setBackground(new Color(96,56,19));
        tab7.setBackground(new Color(96,56,19));
        
        LoadDataTransaksiHariIni();
        LoadDataNgopiSekarang();
    }//GEN-LAST:event_tab1MouseClicked

    private void tab2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab2MouseClicked
        // TODO add your handling code here:
        panelHome.setVisible(false);
        panelPemesanan.setVisible(true);
        panelPembayaran.setVisible(false);
        panelMenu.setVisible(false);
        panelPegawai.setVisible(false);
        panelLaporan.setVisible(false);  
        tab1.setBackground(new Color(96,56,19));
        tab2.setBackground(new Color(160,116,70));
        tab3.setBackground(new Color(96,56,19));
        tab4.setBackground(new Color(96,56,19));
        tab5.setBackground(new Color(96,56,19));
        tab6.setBackground(new Color(96,56,19));
        tab7.setBackground(new Color(96,56,19));
        
    }//GEN-LAST:event_tab2MouseClicked

    private void tab3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab3MouseClicked
        // TODO add your handling code here:
        panelHome.setVisible(false);
        panelPemesanan.setVisible(false);
        panelPembayaran.setVisible(true);
        panelMenu.setVisible(false);
        panelPegawai.setVisible(false);
        panelLaporan.setVisible(false);  
        tab1.setBackground(new Color(96,56,19));
        tab2.setBackground(new Color(96,56,19));
        tab3.setBackground(new Color(160,116,70));
        tab4.setBackground(new Color(96,56,19));
        tab5.setBackground(new Color(96,56,19));
        tab6.setBackground(new Color(96,56,19));
        tab7.setBackground(new Color(96,56,19));
    }//GEN-LAST:event_tab3MouseClicked

    private void tab4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab4MouseClicked
        // TODO add your handling code here:
        panelHome.setVisible(false);
        panelPemesanan.setVisible(false);
        panelPembayaran.setVisible(false);
        panelMenu.setVisible(true);
        panelPegawai.setVisible(false);
        panelLaporan.setVisible(false);  
        tab1.setBackground(new Color(96,56,19));
        tab2.setBackground(new Color(96,56,19));
        tab3.setBackground(new Color(96,56,19));
        tab4.setBackground(new Color(160,116,70));
        tab5.setBackground(new Color(96,56,19));
        tab6.setBackground(new Color(96,56,19));
        tab7.setBackground(new Color(96,56,19));
    }//GEN-LAST:event_tab4MouseClicked

    private void tab5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab5MouseClicked
        // TODO add your handling code here:
        panelHome.setVisible(false);
        panelPemesanan.setVisible(false);
        panelPembayaran.setVisible(false);
        panelMenu.setVisible(false);
        panelPegawai.setVisible(true);
        panelLaporan.setVisible(false); 
        tab1.setBackground(new Color(96,56,19));
        tab2.setBackground(new Color(96,56,19));
        tab3.setBackground(new Color(96,56,19));
        tab4.setBackground(new Color(96,56,19));
        tab5.setBackground(new Color(160,116,70));
        tab6.setBackground(new Color(96,56,19));
        tab7.setBackground(new Color(96,56,19));
    }//GEN-LAST:event_tab5MouseClicked

    private void tab6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab6MouseClicked
        // TODO add your handling code here:
        panelHome.setVisible(false);
        panelPemesanan.setVisible(false);
        panelPembayaran.setVisible(false);
        panelMenu.setVisible(false);
        panelPegawai.setVisible(false);
        panelLaporan.setVisible(true); 
        tab1.setBackground(new Color(96,56,19));
        tab2.setBackground(new Color(96,56,19));
        tab3.setBackground(new Color(96,56,19));
        tab4.setBackground(new Color(96,56,19));
        tab5.setBackground(new Color(96,56,19));
        tab6.setBackground(new Color(160,116,70));
        tab7.setBackground(new Color(96,56,19));
    }//GEN-LAST:event_tab6MouseClicked

    private void tab7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab7MouseClicked
        // TODO add your handling code here:
        dispose();
        new Login().setVisible(true);
    }//GEN-LAST:event_tab7MouseClicked

    private void cbMinumanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMinumanActionPerformed
        // TODO add your handling code here:
        try{
            Con=Konek.getKoneksiDatabase();
            Stm = Con.createStatement();
            Sql = "select * from tbl_menu where kategori='Minuman' AND nama='"+cbMinuman.getSelectedItem()+"' ";
            Rs=Stm.executeQuery(Sql);
            while(Rs.next()) {
                txtPesananPemesanan.setText(Rs.getString("nama"));
            }
        } catch(SQLException e){
            System.out.println("koneksi gagal"+e.toString());
        }
    }//GEN-LAST:event_cbMinumanActionPerformed

    private void addPesananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPesananActionPerformed
        // TODO add your handling code here:
        int harga = 0,quantity=0,total=0,ambiltotalpembayaran=0;
        try{
            Con=Konek.getKoneksiDatabase();
            Stm = Con.createStatement();
            Sql = "select * from tbl_menu where nama='"+txtPesananPemesanan.getText()+"' ";
            Rs=Stm.executeQuery(Sql);
            while(Rs.next()) {
                harga = Rs.getInt("harga");
            }
            quantity = (int) quantityPemesanan.getValue();
            total = quantity * harga;
            ambiltotalpembayaran = (int) totalPembayaran.getValue();
            totalPembayaran.setValue(ambiltotalpembayaran + total);
            totalPesanan.setText(totalPesanan.getText() + quantity + "         " + txtPesananPemesanan.getText() + "\n");
        } catch(SQLException e){
            System.out.println("koneksi gagal"+e.toString());
        }

    }//GEN-LAST:event_addPesananActionPerformed

    private void cbMejaPemesananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMejaPemesananActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbMejaPemesananActionPerformed

    private void txtIdMejaPemesananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdMejaPemesananActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdMejaPemesananActionPerformed

    private void btnPesanPemesananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesanPemesananActionPerformed
        // TODO add your handling code here:
        String Tampilan="yyyy-MM-dd";
        SimpleDateFormat Fm = new SimpleDateFormat(Tampilan);
        String tanggalPesanan = String.valueOf(Fm.format(tglPemesanan.getDate()));
        
        try {
            Con = Konek.getKoneksiDatabase();
            Stm = null;
            Sql = "insert into tbl_transaksi(namapemesan,mejaid,pesanan,total,uangpelanggan,uangkembalian,status,tanggalpesanan) values('"+txtNamaPemesan.getText()+"','"+txtIdMejaPemesanan.getText()+"','"+totalPesanan.getText()+"','"+totalPembayaran.getValue()+"','0','0','Belum Bayar','"+tanggalPesanan+"')";
            Stm= Con.createStatement();
            int AdaPenambahanRecord= Stm.executeUpdate(Sql);
            if (AdaPenambahanRecord>0){
                Stm.executeUpdate("update tbl_meja set status='Terpakai' where id='"+txtIdMejaPemesanan.getText()+"' ");
                JOptionPane.showMessageDialog(this,"Pesanan Berhasil Tersimpan",
                    "Informasi",JOptionPane.INFORMATION_MESSAGE);
                TampilPemesanan();
            }
            else
            JOptionPane.showMessageDialog(this,"Pesanan Gagal Tersimpan",
                "Informasi",JOptionPane.INFORMATION_MESSAGE);
            Stm.close();
        } catch (SQLException e){
            System.out.println("Koneksi Gagal " +e.toString());
        }
        BersihPemesanan();
        TampilPembayaran();
        LoadMeja();
    }//GEN-LAST:event_btnPesanPemesananActionPerformed

    private void btnHapusPemesananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusPemesananActionPerformed
        // TODO add your handling code here:
        try {
            Con = Konek.getKoneksiDatabase();
            Stm = null;
            Sql = "delete from tbl_transaksi where id='"+idPemesanan+"' ";
            Stm= Con.createStatement();
            int AdaPenambahanRecord= Stm.executeUpdate(Sql);
            TampilPemesanan();
            if (AdaPenambahanRecord>0) {
                Stm.executeUpdate("update tbl_meja set status='Tersedia' where meja='"+idMejaPemesanan+"' ");
                JOptionPane.showMessageDialog(this,"Pesanan Berhasil Dihapus",
                "Informasi",JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(this,"Pesanan Gagal Dihapus",
                "Informasi",JOptionPane.INFORMATION_MESSAGE);
            }
            Stm.close();
        } catch (SQLException e){
            System.out.println("Koneksi Gagal " +e.toString());
        }
        BersihPemesanan(); 
        TampilPembayaran();
        LoadMeja();
        
    }//GEN-LAST:event_btnHapusPemesananActionPerformed

    private void btnClearPemesananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearPemesananActionPerformed
        // TODO add your handling code here:
        BersihPemesanan();
        TampilPemesanan();
    }//GEN-LAST:event_btnClearPemesananActionPerformed

    private void cbMakananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMakananActionPerformed
        // TODO add your handling code here:
        try{
            Con=Konek.getKoneksiDatabase();
            Stm = Con.createStatement();
            Sql = "select * from tbl_menu where kategori='Makanan' AND nama='"+cbMakanan.getSelectedItem()+"'";
            Rs=Stm.executeQuery(Sql);
            while(Rs.next()) {
                txtPesananPemesanan.setText(Rs.getString("nama"));
            }
        } catch(SQLException e){
            System.out.println("koneksi gagal"+e.toString());
        }
    }//GEN-LAST:event_cbMakananActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        int i = jTable1.getSelectedRow();
        if (i == -1) {
            return;
        }
        
        idPemesanan = jTable1.getValueAt(i, 0).toString();
        idMejaPemesanan = jTable1.getValueAt(i, 2).toString();

    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        int i = jTable2.getSelectedRow();
        if (i == -1) {
            return;
        }
        idPembayaran = jTable2.getValueAt(i, 0).toString();
        txtNamaPemesan1.setText(jTable2.getValueAt(i, 1).toString());
        txtMejaPemesanan.setText(jTable2.getValueAt(i, 2).toString());
        totalPesanan1.setText(jTable2.getValueAt(i, 3).toString());
        totalPembayaran1.setValue(Integer.parseInt((String)jTable2.getValueAt(i,4)));
        
    }//GEN-LAST:event_jTable2MouseClicked

    private void btnClearPemesanan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearPemesanan1ActionPerformed
        // TODO add your handling code here:
        BersihPembayaran();
        TampilPemesanan();
    }//GEN-LAST:event_btnClearPemesanan1ActionPerformed

    private void btnBayarPesananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBayarPesananActionPerformed
        // TODO add your handling code here:
        try {
            Con = Konek.getKoneksiDatabase();
            Stm = null;
            Sql = "update tbl_transaksi set uangpelanggan='"+totalPembayaran2.getValue()+"', uangkembalian='"+txtKembalian.getValue()+"', status='Sudah Bayar' where id ='"+idPembayaran+"' ";
            Stm= Con.createStatement();
            int AdaPenambahanRecord= Stm.executeUpdate(Sql);
            TampilPemesanan();
            if (AdaPenambahanRecord>0) {
                Stm.executeUpdate("update tbl_meja set status='Tersedia' where id='"+txtMejaPemesanan.getText()+"' ");
                JOptionPane.showMessageDialog(this,"Berhasil Membayar Pesanan, Struk Akan Segera Dicetak",
                "Informasi",JOptionPane.INFORMATION_MESSAGE);
                
                try{
                    String path = "C:\\Users\\abdig\\Documents\\NetBeansProjects\\AbdiGunawan_182004_PBOKelasA\\src\\FinalProjek_Report\\struk.jasper";
                    Map parameter = new HashMap();

                    parameter.put("idtransaksi", idPembayaran);
                    JR = (JasperReport)JRLoader.loadObject(path);
                    JP = JasperFillManager.fillReport(path,parameter, Con);
                    JasperViewer.viewReport(JP,false);
                 }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(rootPane,"Dokumen tidak ada"+ex);
                }  
            }
            
            else
                JOptionPane.showMessageDialog(this,"Gagal Membayar Pesanan",
                "Informasi",JOptionPane.INFORMATION_MESSAGE);
            Stm.close();
        } catch (SQLException e){
            System.out.println("Koneksi Gagal " +e.toString());
        }
        BersihPembayaran();
        TampilPemesanan();
        TampilPembayaran();
        LoadMeja();
    }//GEN-LAST:event_btnBayarPesananActionPerformed

    private void btnBayarPesanan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBayarPesanan1ActionPerformed
        // TODO add your handling code here:
        int bayar,uang,kembalian;
        bayar = (int) totalPembayaran1.getValue();
        uang = (int) totalPembayaran2.getValue();
        kembalian = uang - bayar;
        txtKembalian.setValue(kembalian);
    }//GEN-LAST:event_btnBayarPesanan1ActionPerformed

    private void cbMejaPemesananMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbMejaPemesananMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cbMejaPemesananMouseClicked

    private void cbMejaPemesananItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbMejaPemesananItemStateChanged
        // TODO add your handling code here:
        try{
            Con=Konek.getKoneksiDatabase();
            Stm = Con.createStatement();
            Sql = "select * from tbl_meja where meja='"+cbMejaPemesanan.getSelectedItem()+"' ";
            Rs=Stm.executeQuery(Sql);
            while(Rs.next()) {
                txtIdMejaPemesanan.setText(Rs.getString("id"));
            }
        } catch(SQLException e){
            System.out.println("koneksi gagal"+e.toString());
        }
    }//GEN-LAST:event_cbMejaPemesananItemStateChanged

    private void txtPesananPemesananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesananPemesananActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPesananPemesananActionPerformed

    private void clearPesananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearPesananActionPerformed
        // TODO add your handling code here:
        totalPesanan.setText("");
        quantityPemesanan.setValue(0);
        totalPembayaran.setValue(0);
        txtPesananPemesanan.setText("");
        
    }//GEN-LAST:event_clearPesananActionPerformed

    private void cbKategoriMenuCafeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbKategoriMenuCafeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbKategoriMenuCafeActionPerformed

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        // TODO add your handling code here:
        int i = jTable3.getSelectedRow();
        if (i == -1) {
            return;
        }
        idMenuCafe = jTable3.getValueAt(i, 0).toString();
        txtNamaMenuCafe.setText(jTable3.getValueAt(i, 1).toString());
        cbKategoriMenuCafe.setSelectedItem(jTable3.getValueAt(i, 2).toString());
        txtHargaMenu.setValue(Integer.parseInt((String)jTable3.getValueAt(i,3)));
    }//GEN-LAST:event_jTable3MouseClicked

    private void btnSimpanMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanMenuActionPerformed
        // TODO add your handling code here:
        try {
            Con = Konek.getKoneksiDatabase();
            Stm = null;
            Sql = "insert into tbl_menu(nama,kategori,harga) values('"+txtNamaMenuCafe.getText()+"','"+cbKategoriMenuCafe.getSelectedItem()+"','"+txtHargaMenu.getValue()+"')";
            Stm= Con.createStatement();
            int AdaPenambahanRecord= Stm.executeUpdate(Sql);
            if (AdaPenambahanRecord>0){
                JOptionPane.showMessageDialog(this,"Menu Berhasil Ditambah",
                    "Informasi",JOptionPane.INFORMATION_MESSAGE);
                TampilMenuCafe();
                LoadDataMakanan();
                LoadDataMinuman();
                BersihkanMenuCafe();
            }
            else
            JOptionPane.showMessageDialog(this,"Menu Gagal Tersimpan",
                "Informasi",JOptionPane.INFORMATION_MESSAGE);
            Stm.close();
        } catch (SQLException e){
            System.out.println("Koneksi Gagal " +e.toString());
        }
    }//GEN-LAST:event_btnSimpanMenuActionPerformed

    private void btnEditMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditMenuActionPerformed
        // TODO add your handling code here:
        try {
            Con = Konek.getKoneksiDatabase();
            Stm = null;
            Sql = "update tbl_menu set nama='"+txtNamaMenuCafe.getText()+"', kategori ='"+cbKategoriMenuCafe.getSelectedItem()+"', harga='"+txtHargaMenu.getValue()+"'where id ='"+idMenuCafe+"' ";
            Stm= Con.createStatement();
            int AdaPenambahanRecord= Stm.executeUpdate(Sql);
            if (AdaPenambahanRecord>0) {
                JOptionPane.showMessageDialog(this,"Menu Berhasil Di update",
                "Informasi",JOptionPane.INFORMATION_MESSAGE);
                TampilMenuCafe();
                LoadDataMakanan();
                LoadDataMinuman();
                BersihkanMenuCafe();
            }
            else
                JOptionPane.showMessageDialog(this,"Menu Gagal Diupdate",
                "Informasi",JOptionPane.INFORMATION_MESSAGE);
            Stm.close();
        } catch (SQLException e){
            System.out.println("Koneksi Gagal " +e.toString());
        }
    }//GEN-LAST:event_btnEditMenuActionPerformed

    private void btnHapusMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusMenuActionPerformed
        // TODO add your handling code here:
        try {
            Con = Konek.getKoneksiDatabase();
            Stm = null;
            Sql = "delete from tbl_menu where id ='"+idMenuCafe+"' ";
            Stm= Con.createStatement();
            int AdaPenambahanRecord= Stm.executeUpdate(Sql);
            if (AdaPenambahanRecord>0) {
            JOptionPane.showMessageDialog(this,"Data Berhasil Dihapus",
                "Informasi",JOptionPane.INFORMATION_MESSAGE);
                TampilMenuCafe();
                LoadDataMakanan();
                LoadDataMinuman();
                BersihkanMenuCafe();
            }
            else
            JOptionPane.showMessageDialog(this,"Data Gagal Dihapus",
                "Informasi",JOptionPane.INFORMATION_MESSAGE);
            Stm.close();
        } catch (SQLException e){
            System.out.println("Koneksi Gagal " +e.toString());
        }
    }//GEN-LAST:event_btnHapusMenuActionPerformed

    private void btnClearMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearMenuActionPerformed
        // TODO add your handling code here:
        BersihkanMenuCafe();
    }//GEN-LAST:event_btnClearMenuActionPerformed

    private void txtCariMenuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariMenuKeyPressed
        // TODO add your handling code here:
        try {
            Con=Konek.getKoneksiDatabase();
            Stm = Con.createStatement();
            TampilMenuCafeJTabel();
            Sql = "select * from tbl_menu where id LIKE '%"+txtCariMenu.getText()+"%' OR nama LIKE '%"+txtCariMenu.getText()+"%' OR kategori LIKE '%"+txtCariMenu.getText()+"%' OR harga LIKE '%"+txtCariMenu.getText()+"%' ";
            Rs = Stm.executeQuery(Sql);
            while(Rs.next()){
                Dtm2.addRow(new Object[]{
                        Rs.getString("id"),
                        Rs.getString("nama"),
                        Rs.getString("kategori"),
                        Rs.getString("harga"),
                });
            jTable3.setModel(Dtm2);
            }
        }catch(Exception e){
            System.out.println("Ada Kesalahan " + e.toString());
        } 
    }//GEN-LAST:event_txtCariMenuKeyPressed

    private void jTable4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable4MouseClicked
        // TODO add your handling code here:
        int i = jTable4.getSelectedRow();
        if (i == -1) {
            return;
        }
        txtNipPegawai.setText(jTable4.getValueAt(i, 0).toString());
        txtNamaPegawai.setText(jTable4.getValueAt(i, 1).toString());
        txtAlamatPegawai.setText(jTable4.getValueAt(i, 2).toString());
        String jenisKelamin = jTable4.getValueAt(i, 3).toString();
        if (jenisKelamin.equals("Laki Laki")) {
            rbLaki.setSelected(true);
        } else if (jenisKelamin.equals("Perempuan")) {
            RbPerempuan.setSelected(true);
        }
        cbJabatanPegawai.setSelectedItem(jTable4.getValueAt(i, 4).toString());        
        txtEmailPegawai.setText(jTable4.getValueAt(i, 5).toString());
        txtUsernamePegawai.setText(jTable4.getValueAt(i, 6).toString());      
    }//GEN-LAST:event_jTable4MouseClicked

    private void btnSimpanPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanPegawaiActionPerformed
        // TODO add your handling code here:
        String roleid="",Jkel="";
        if (cbJabatanPegawai.getSelectedItem() == "Kasir")
            roleid = "3";
        else if (cbJabatanPegawai.getSelectedItem() == "Waiter") 
            roleid = "2";

        if (rbLaki.isSelected()) {
            Jkel = "Laki Laki";
        }else if (RbPerempuan.isSelected()) {
            Jkel = "Perempuan";
        }
        
        try {
            Con = Konek.getKoneksiDatabase();
            Stm = null;
            Sql = "insert into tbl_pegawai values('"+txtNipPegawai.getText()+"','"+txtNamaPegawai.getText()+"','"+txtAlamatPegawai.getText()+"',"
                    + "'"+Jkel+"','"+roleid+"','"+txtUsernamePegawai.getText()+"','"+txtPasswordPegawai.getText()+"','"+txtEmailPegawai.getText()+"')";
            Stm= Con.createStatement();
            int AdaPenambahanRecord= Stm.executeUpdate(Sql);
            if (AdaPenambahanRecord>0){
                JOptionPane.showMessageDialog(this,"Pegawai Berhasil Ditambah",
                    "Informasi",JOptionPane.INFORMATION_MESSAGE);
                TampilPegawai();
                BersihkanPegawai();
            }
            else
            JOptionPane.showMessageDialog(this,"Pegawai Gagal Tersimpan",
                "Informasi",JOptionPane.INFORMATION_MESSAGE);
            Stm.close();
        } catch (SQLException e){
            System.out.println("Koneksi Gagal " +e.toString());
        }
    }//GEN-LAST:event_btnSimpanPegawaiActionPerformed

    private void btnEditPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditPegawaiActionPerformed
        // TODO add your handling code here:
        String roleid="",Jkel="";
        if (cbJabatanPegawai.getSelectedItem() == "Kasir")
            roleid = "3";
        else if (cbJabatanPegawai.getSelectedItem() == "Waiter") 
            roleid = "2";

        if (rbLaki.isSelected()) {
            Jkel = "Laki Laki";
        }else if (RbPerempuan.isSelected()) {
            Jkel = "Perempuan";
        }
        
        try {
            Con = Konek.getKoneksiDatabase();
            Stm = null;
            Sql = "update tbl_pegawai set namalengkap='"+txtNamaPegawai.getText()+"',alamat='"+txtAlamatPegawai.getText()+"', "
                + "jkel ='"+Jkel+"', roleid='"+roleid+"', username='"+txtUsernamePegawai.getText()+"',password='"+txtPasswordPegawai.getText()+"',"
                + " email='"+txtEmailPegawai.getText()+"' where nip ='"+txtNipPegawai.getText()+"' ";
            Stm= Con.createStatement();
            int AdaPenambahanRecord= Stm.executeUpdate(Sql);
            if (AdaPenambahanRecord>0) {
                JOptionPane.showMessageDialog(this,"Pegawai Berhasil Di update",
                "Informasi",JOptionPane.INFORMATION_MESSAGE);
                TampilPegawai();
                BersihkanPegawai();
            }
            else
                JOptionPane.showMessageDialog(this,"Pegawai Gagal Diupdate",
                "Informasi",JOptionPane.INFORMATION_MESSAGE);
            Stm.close();
        } catch (SQLException e){
            System.out.println("Koneksi Gagal " +e.toString());
        }
    }//GEN-LAST:event_btnEditPegawaiActionPerformed

    private void btnHapusPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusPegawaiActionPerformed
        // TODO add your handling code here:
        try {
            Con = Konek.getKoneksiDatabase();
            Stm = null;
            Sql = "delete from tbl_pegawai where nip='"+txtNipPegawai.getText()+"' ";
            Stm= Con.createStatement();
            int AdaPenambahanRecord= Stm.executeUpdate(Sql);
            if (AdaPenambahanRecord>0) {
                JOptionPane.showMessageDialog(this,"Pegawai Berhasil Dihapus",
                "Informasi",JOptionPane.INFORMATION_MESSAGE);
                TampilPegawai();
                BersihkanPegawai();
            }
            else
            JOptionPane.showMessageDialog(this,"Pegawai Gagal Dihapus",
                "Informasi",JOptionPane.INFORMATION_MESSAGE);
            Stm.close();
        } catch (SQLException e){
            System.out.println("Koneksi Gagal " +e.toString());
        }
    }//GEN-LAST:event_btnHapusPegawaiActionPerformed

    private void btnClearPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearPegawaiActionPerformed
        // TODO add your handling code here:
        BersihkanPegawai();
    }//GEN-LAST:event_btnClearPegawaiActionPerformed

    private void cbJabatanPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbJabatanPegawaiActionPerformed
        // TODO add your handling code here:
        if (cbJabatanPegawai.getSelectedItem() == "Kasir") {
            txtUsernamePegawai.setEnabled(true);
            txtPasswordPegawai.setEnabled(true);
        }
        else if (cbJabatanPegawai.getSelectedItem() == "Waiter") {
            txtUsernamePegawai.setEnabled(false);
            txtPasswordPegawai.setEnabled(false);
        }
    }//GEN-LAST:event_cbJabatanPegawaiActionPerformed

    private void btnSimpanPegawai1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanPegawai1ActionPerformed
        // TODO add your handling code here:
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String hariini = dateFormat.format(date);
        
        try{
            Con = Konek.getKoneksiDatabase();
            try{

                 String path = "C:\\Users\\abdig\\Documents\\NetBeansProjects\\AbdiGunawan_182004_PBOKelasA\\src\\FinalProjek_Report\\laporanTransaksi.jasper   ";
                 Map parameter = new HashMap();
                
                 parameter.put("tglhariini", hariini);
                 JR = (JasperReport)JRLoader.loadObject(path);
                 JP = JasperFillManager.fillReport(path,parameter, Con);
                 JasperViewer.viewReport(JP,false);
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(rootPane,"Dokumen tidak ada"+ex);
            }
        Con.close();
        }catch (Exception e){
            JOptionPane.showMessageDialog(rootPane, e);
        }
        
    }//GEN-LAST:event_btnSimpanPegawai1ActionPerformed

    private void btnSimpanPegawai2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanPegawai2ActionPerformed
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();

        try{
            Con = Konek.getKoneksiDatabase();
            try{

                 String path = "C:\\Users\\abdig\\Documents\\NetBeansProjects\\AbdiGunawan_182004_PBOKelasA\\src\\FinalProjek_Report\\laporanTransaksiBulanan.jasper";
                 Map parameter = new HashMap();
                
                 parameter.put("bulan", month);
                 JR = (JasperReport)JRLoader.loadObject(path);
                 JP = JasperFillManager.fillReport(path,parameter, Con);
                 JasperViewer.viewReport(JP,false);
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(rootPane,"Dokumen tidak ada"+ex);
            }
        Con.close();
        }catch (Exception e){
            JOptionPane.showMessageDialog(rootPane, e);
        }
        
    }//GEN-LAST:event_btnSimpanPegawai2ActionPerformed

    private void btnSimpanPegawai3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanPegawai3ActionPerformed
        // TODO add your handling code here:
        String Tampilan="yyyy-MM-dd";
        SimpleDateFormat Fm = new SimpleDateFormat(Tampilan);
        String tglawal = String.valueOf(Fm.format(tglAwalCetak.getDate()));
        String tglakhir = String.valueOf(Fm.format(tglAkhirCetak.getDate()));
        
        try{
            Con = Konek.getKoneksiDatabase();
            try{

                 String path = "C:\\Users\\abdig\\Documents\\NetBeansProjects\\AbdiGunawan_182004_PBOKelasA\\src\\FinalProjek_Report\\laporanTransaksiCustom.jasper";
                 Map parameter = new HashMap();
                 parameter.put("awal", tglawal);
                 parameter.put("akhir", tglakhir);
                 JR = (JasperReport)JRLoader.loadObject(path);
                 JP = JasperFillManager.fillReport(path,parameter, Con);
                 JasperViewer.viewReport(JP,false);
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(rootPane,"Dokumen tidak ada"+ex);
            }
        Con.close();
        }catch (Exception e){
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }//GEN-LAST:event_btnSimpanPegawai3ActionPerformed

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
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton RbPerempuan;
    private javax.swing.JButton addPesanan;
    private javax.swing.JButton btnBayarPesanan;
    private javax.swing.JButton btnBayarPesanan1;
    private javax.swing.JButton btnClearMenu;
    private javax.swing.JButton btnClearPegawai;
    private javax.swing.JButton btnClearPemesanan;
    private javax.swing.JButton btnClearPemesanan1;
    private javax.swing.JButton btnEditMenu;
    private javax.swing.JButton btnEditPegawai;
    private javax.swing.JButton btnHapusMenu;
    private javax.swing.JButton btnHapusPegawai;
    private javax.swing.JButton btnHapusPemesanan;
    private javax.swing.JButton btnPesanPemesanan;
    private javax.swing.JButton btnSimpanMenu;
    private javax.swing.JButton btnSimpanPegawai;
    private javax.swing.JButton btnSimpanPegawai1;
    private javax.swing.JButton btnSimpanPegawai2;
    private javax.swing.JButton btnSimpanPegawai3;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbJabatanPegawai;
    private javax.swing.JComboBox<String> cbKategoriMenuCafe;
    private javax.swing.JComboBox<String> cbMakanan;
    private javax.swing.JComboBox<String> cbMejaPemesanan;
    private javax.swing.JComboBox<String> cbMinuman;
    private javax.swing.JButton clearPesanan;
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
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JPanel panelHome;
    private javax.swing.JPanel panelLaporan;
    private javax.swing.JPanel panelMenu;
    private javax.swing.JPanel panelPegawai;
    private javax.swing.JPanel panelPembayaran;
    private javax.swing.JPanel panelPemesanan;
    private javax.swing.JPanel panelkiri;
    private javax.swing.JPanel paneltengah;
    private javax.swing.JSpinner quantityPemesanan;
    private javax.swing.JRadioButton rbLaki;
    private javax.swing.JPanel tab1;
    private javax.swing.JPanel tab2;
    private javax.swing.JPanel tab3;
    private javax.swing.JPanel tab4;
    private javax.swing.JPanel tab5;
    private javax.swing.JPanel tab6;
    private javax.swing.JPanel tab7;
    private com.toedter.calendar.JDateChooser tglAkhirCetak;
    private com.toedter.calendar.JDateChooser tglAwalCetak;
    private com.toedter.calendar.JDateChooser tglPemesanan;
    private javax.swing.JSpinner totalPembayaran;
    private javax.swing.JSpinner totalPembayaran1;
    private javax.swing.JSpinner totalPembayaran2;
    private javax.swing.JTextArea totalPesanan;
    private javax.swing.JTextArea totalPesanan1;
    private javax.swing.JTextArea txtAlamatPegawai;
    private javax.swing.JTextField txtCariMenu;
    private javax.swing.JTextField txtEmailPegawai;
    private javax.swing.JSpinner txtHargaMenu;
    private javax.swing.JTextField txtIdMejaPemesanan;
    private javax.swing.JSpinner txtKembalian;
    private javax.swing.JTextField txtMejaPemesanan;
    private javax.swing.JTextField txtNamaMenuCafe;
    private javax.swing.JTextField txtNamaPegawai;
    private javax.swing.JTextField txtNamaPemesan;
    private javax.swing.JTextField txtNamaPemesan1;
    private javax.swing.JTextField txtNipPegawai;
    private javax.swing.JTextField txtPasswordPegawai;
    private javax.swing.JTextField txtPesananPemesanan;
    private javax.swing.JTextField txtUsernamePegawai;
    // End of variables declaration//GEN-END:variables

}
