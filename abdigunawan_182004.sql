-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 19, 2021 at 03:20 AM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `abdigunawan_182004`
--

-- --------------------------------------------------------

--
-- Table structure for table `agama_182004_abdigunawan`
--

CREATE TABLE `agama_182004_abdigunawan` (
  `id` int(11) NOT NULL,
  `kode` varchar(2) NOT NULL,
  `nama` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `agama_182004_abdigunawan`
--

INSERT INTO `agama_182004_abdigunawan` (`id`, `kode`, `nama`) VALUES
(1, '01', 'ISLAM'),
(2, '02', 'KRISTEN'),
(3, '03', 'KATOLIK'),
(4, '04', 'HINDU'),
(5, '05', 'BUDHA');

-- --------------------------------------------------------

--
-- Table structure for table `barang_final_abdigunawan`
--

CREATE TABLE `barang_final_abdigunawan` (
  `kode_brg` varchar(6) NOT NULL,
  `nama` varchar(25) NOT NULL,
  `kode_kategori` varchar(4) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `hargabeli` int(11) NOT NULL,
  `hargajual` int(11) NOT NULL,
  `totalhargabeli` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `barang_final_abdigunawan`
--

INSERT INTO `barang_final_abdigunawan` (`kode_brg`, `nama`, `kode_kategori`, `jumlah`, `hargabeli`, `hargajual`, `totalhargabeli`) VALUES
('K123', 'Barongko', 'K001', 5, 5000, 7000, 25000),
('K1234', 'Nasi Kuning', 'K001', 7, 8000, 10000, 56000),
('M124', 'Kopi Susu', 'K002', 8, 5000, 7000, 40000);

-- --------------------------------------------------------

--
-- Table structure for table `biodata_mahasiswa_182004_abdigunawan`
--

CREATE TABLE `biodata_mahasiswa_182004_abdigunawan` (
  `id` int(15) NOT NULL,
  `nim` varchar(11) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `kode_program_studi` varchar(4) NOT NULL,
  `kode_agama` varchar(2) DEFAULT NULL,
  `tempat_lahir` varchar(100) DEFAULT NULL,
  `tanggal_lahir` date DEFAULT NULL,
  `jenis_kelamin` enum('L','P') DEFAULT NULL,
  `alamat` varchar(100) DEFAULT NULL,
  `kota` varchar(100) DEFAULT NULL,
  `kode_provinsi` varchar(2) DEFAULT NULL,
  `kode_pos` varchar(50) DEFAULT NULL,
  `telpon` varchar(40) DEFAULT NULL,
  `handphone` varchar(40) DEFAULT NULL,
  `hobi` varchar(100) DEFAULT NULL,
  `wali` varchar(100) DEFAULT NULL,
  `alamat_wali` varchar(100) DEFAULT NULL,
  `telpon_wali` varchar(40) DEFAULT NULL,
  `tahun_masuk` varchar(4) NOT NULL,
  `last_update` datetime NOT NULL,
  `userid` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `biodata_mahasiswa_182004_abdigunawan`
--

INSERT INTO `biodata_mahasiswa_182004_abdigunawan` (`id`, `nim`, `nama`, `kode_program_studi`, `kode_agama`, `tempat_lahir`, `tanggal_lahir`, `jenis_kelamin`, `alamat`, `kota`, `kode_provinsi`, `kode_pos`, `telpon`, `handphone`, `hobi`, `wali`, `alamat_wali`, `telpon_wali`, `tahun_masuk`, `last_update`, `userid`) VALUES
(12, '182004', 'Abdi Gunawan', 'P02', '01', 'Bone', '2000-01-23', 'L', 'Bone', 'Bone', '01', '92763', '082293204972', '082293204972', 'Ngoding', 'Marwati', 'Koppe', '0812345678910', '2020', '2021-06-24 23:37:28', 'ADMIN');

-- --------------------------------------------------------

--
-- Table structure for table `kategori_final`
--

CREATE TABLE `kategori_final` (
  `kode_kategori` varchar(4) NOT NULL,
  `nama` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `kategori_final`
--

INSERT INTO `kategori_final` (`kode_kategori`, `nama`) VALUES
('K001', 'Makanan'),
('K002', 'Minuman'),
('K003', 'Pakaian');

-- --------------------------------------------------------

--
-- Table structure for table `mhs_abdigunawan`
--

CREATE TABLE `mhs_abdigunawan` (
  `stambuk` varchar(10) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `jurusan` varchar(50) NOT NULL,
  `agama` varchar(50) NOT NULL,
  `nohp` varchar(50) NOT NULL,
  `alamat` text NOT NULL,
  `jkel` varchar(50) NOT NULL,
  `ahli` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `mhs_abdigunawan`
--

INSERT INTO `mhs_abdigunawan` (`stambuk`, `nama`, `jurusan`, `agama`, `nohp`, `alamat`, `jkel`, `ahli`) VALUES
('182004', 'Abdi Gunawan', 'Teknik Informatika', 'Islam', '082293204972', 'Bone', 'Laki Laki', 'Ngoding'),
('182001', 'Alvisa', 'Teknik Informatika', 'Islam', '367854', 'asdfghj', 'Laki Laki', 'adafadsad');

-- --------------------------------------------------------

--
-- Table structure for table `program_studi_182004_abdigunawan`
--

CREATE TABLE `program_studi_182004_abdigunawan` (
  `id` tinyint(4) NOT NULL,
  `kode_jurusan` varchar(4) NOT NULL,
  `nama` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `program_studi_182004_abdigunawan`
--

INSERT INTO `program_studi_182004_abdigunawan` (`id`, `kode_jurusan`, `nama`) VALUES
(1, 'P01', 'SISTEM INFORMASI'),
(2, 'P02', 'TEKNIK INFORMATIKA'),
(3, 'P03', 'MANAJEMEN INFORMATIKA'),
(4, 'P04', 'REKAYASA PERANGKAT LUNAK'),
(5, 'P05', 'KEWIRAUSAHAAN'),
(6, 'P06', 'BISNIS DIGITAL');

-- --------------------------------------------------------

--
-- Table structure for table `provinsi_182004_abdigunawan`
--

CREATE TABLE `provinsi_182004_abdigunawan` (
  `id` int(4) NOT NULL,
  `kode` varchar(2) NOT NULL,
  `nama` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `provinsi_182004_abdigunawan`
--

INSERT INTO `provinsi_182004_abdigunawan` (`id`, `kode`, `nama`) VALUES
(1, '01', 'SULAWESI SELATAN'),
(2, '02', 'SELAWESI BARAT'),
(3, '03', 'SULAWESI TENGAH'),
(4, '04', 'SULAWESI TENGGARA'),
(5, '05', 'GORONTALO');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_meja`
--

CREATE TABLE `tbl_meja` (
  `id` int(5) NOT NULL,
  `meja` varchar(50) NOT NULL,
  `status` enum('Tersedia','Terpakai') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbl_meja`
--

INSERT INTO `tbl_meja` (`id`, `meja`, `status`) VALUES
(1, 'Meja 1', 'Tersedia'),
(2, 'Meja 2', 'Terpakai'),
(3, 'Meja 3', 'Tersedia');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_menu`
--

CREATE TABLE `tbl_menu` (
  `id` int(5) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `kategori` enum('Makanan','Minuman') NOT NULL,
  `harga` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbl_menu`
--

INSERT INTO `tbl_menu` (`id`, `nama`, `kategori`, `harga`) VALUES
(3, 'Nasi Kuning Spesial Keju', 'Makanan', 18000),
(4, 'Freshmilk Brown Sugar', 'Minuman', 15000),
(5, 'GreenTea', 'Minuman', 15000),
(6, 'Thai Tea', 'Minuman', 15000),
(8, 'Air Mineral', 'Minuman', 5000),
(9, 'Es Kopi Aren', 'Minuman', 18000);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_pegawai`
--

CREATE TABLE `tbl_pegawai` (
  `nip` varchar(20) NOT NULL,
  `namalengkap` varchar(50) NOT NULL,
  `alamat` text NOT NULL,
  `jkel` enum('Laki Laki','Perempuan') NOT NULL,
  `roleid` int(5) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbl_pegawai`
--

INSERT INTO `tbl_pegawai` (`nip`, `namalengkap`, `alamat`, `jkel`, `roleid`, `username`, `password`, `email`) VALUES
('182000', 'bryan', 'makassar', 'Laki Laki', 3, 'bryan', 'bryan', 'bryan123@gmail.com'),
('182001', 'samuel', 'makassar', 'Laki Laki', 2, '', '', 'samuel'),
('182004', 'Abdi Gunawan', 'Perintis Kemerdekaan IV', 'Laki Laki', 1, 'abdi', 'abdi', 'abdygunawan023@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_role`
--

CREATE TABLE `tbl_role` (
  `id` int(5) NOT NULL,
  `role` enum('Owner','Waiter','Kasir') NOT NULL,
  `gaji` int(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbl_role`
--

INSERT INTO `tbl_role` (`id`, `role`, `gaji`) VALUES
(1, 'Owner', 10000000),
(2, 'Waiter', 4000000),
(3, 'Kasir', 6000000);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_transaksi`
--

CREATE TABLE `tbl_transaksi` (
  `id` int(5) NOT NULL,
  `namapemesan` varchar(50) NOT NULL,
  `mejaid` int(5) NOT NULL,
  `pesanan` text NOT NULL,
  `total` int(30) NOT NULL,
  `uangpelanggan` int(20) NOT NULL,
  `uangkembalian` int(20) NOT NULL,
  `status` enum('Belum Bayar','Sudah Bayar') NOT NULL,
  `tanggalpesanan` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbl_transaksi`
--

INSERT INTO `tbl_transaksi` (`id`, `namapemesan`, `mejaid`, `pesanan`, `total`, `uangpelanggan`, `uangkembalian`, `status`, `tanggalpesanan`) VALUES
(53, 'Abdi Gunawan', 1, '2         GreenTea\n4         Nasi Kuning Spesial Keju\n', 102000, 100000, -2000, 'Sudah Bayar', '2021-07-14'),
(54, 'Imam', 1, '3         Freshmilk Brown Sugar\n', 45000, 100000, 55000, 'Sudah Bayar', '2021-07-14'),
(55, 'Albert', 1, '2         Nasi Kuning Spesial Keju\n', 36000, 100000, 64000, 'Sudah Bayar', '2021-07-14'),
(56, 'aswwww', 2, '10         Freshmilk Brown Sugar\n', 150000, 2000000, 1850000, 'Sudah Bayar', '2021-07-14'),
(57, 'Natalya tolla', 2, '2         Thai Tea\n4         Air Mineral\n', 50000, 50000, 0, 'Sudah Bayar', '2021-07-14'),
(58, 'Abdi Gunawan', 1, '100000         Es Kopi Aren\n', 1800000000, 1900000000, 100000000, 'Sudah Bayar', '2021-07-13'),
(59, 'Somalia Al Mongolia', 1, '1         GreenTea\n2         Thai Tea\n4         Es Kopi Aren\n4         Freshmilk Brown Sugar\n20         Air Mineral\n', 277000, 300000, 23000, 'Sudah Bayar', '2021-07-14'),
(60, 'Natalya Tolla', 1, '1         Es Kopi Aren\n2         Nasi Kuning Spesial Keju\n', 54000, 100000, 46000, 'Sudah Bayar', '2021-08-01'),
(61, 'abdi', 2, '2         GreenTea\n', 30000, 100000, 70000, 'Sudah Bayar', '2021-07-15'),
(62, 'asdsadas', 1, '0         Freshmilk Brown Sugar\n2         Freshmilk Brown Sugar\n', 30000, 1, -29999, 'Sudah Bayar', '2021-07-15'),
(63, 'Rodrigue', 2, '3         Freshmilk Brown Sugar\n', 45000, 0, 0, 'Belum Bayar', '2021-07-15'),
(64, 'Abdi Gunawan', 3, '2         GreenTea\n3         Air Mineral\n1         Nasi Kuning Spesial Keju\n', 63000, 100000, 37000, 'Sudah Bayar', '2021-07-15'),
(65, 'Yudha', 3, '1         Freshmilk Brown Sugar\n1         Nasi Kuning Spesial Keju\n', 33000, 100000, 67000, 'Sudah Bayar', '2021-07-16');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `agama_182004_abdigunawan`
--
ALTER TABLE `agama_182004_abdigunawan`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`,`kode`);

--
-- Indexes for table `barang_final_abdigunawan`
--
ALTER TABLE `barang_final_abdigunawan`
  ADD PRIMARY KEY (`kode_brg`);

--
-- Indexes for table `biodata_mahasiswa_182004_abdigunawan`
--
ALTER TABLE `biodata_mahasiswa_182004_abdigunawan`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id` (`id`,`nim`);

--
-- Indexes for table `kategori_final`
--
ALTER TABLE `kategori_final`
  ADD PRIMARY KEY (`kode_kategori`);

--
-- Indexes for table `program_studi_182004_abdigunawan`
--
ALTER TABLE `program_studi_182004_abdigunawan`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`);

--
-- Indexes for table `provinsi_182004_abdigunawan`
--
ALTER TABLE `provinsi_182004_abdigunawan`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_meja`
--
ALTER TABLE `tbl_meja`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_menu`
--
ALTER TABLE `tbl_menu`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_pegawai`
--
ALTER TABLE `tbl_pegawai`
  ADD PRIMARY KEY (`nip`),
  ADD KEY `roleid` (`roleid`);

--
-- Indexes for table `tbl_role`
--
ALTER TABLE `tbl_role`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_transaksi`
--
ALTER TABLE `tbl_transaksi`
  ADD PRIMARY KEY (`id`),
  ADD KEY `mejaid` (`mejaid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `agama_182004_abdigunawan`
--
ALTER TABLE `agama_182004_abdigunawan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `biodata_mahasiswa_182004_abdigunawan`
--
ALTER TABLE `biodata_mahasiswa_182004_abdigunawan`
  MODIFY `id` int(15) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `program_studi_182004_abdigunawan`
--
ALTER TABLE `program_studi_182004_abdigunawan`
  MODIFY `id` tinyint(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `provinsi_182004_abdigunawan`
--
ALTER TABLE `provinsi_182004_abdigunawan`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `tbl_meja`
--
ALTER TABLE `tbl_meja`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `tbl_menu`
--
ALTER TABLE `tbl_menu`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `tbl_role`
--
ALTER TABLE `tbl_role`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `tbl_transaksi`
--
ALTER TABLE `tbl_transaksi`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=66;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbl_pegawai`
--
ALTER TABLE `tbl_pegawai`
  ADD CONSTRAINT `tbl_pegawai_ibfk_1` FOREIGN KEY (`roleid`) REFERENCES `tbl_role` (`id`);

--
-- Constraints for table `tbl_transaksi`
--
ALTER TABLE `tbl_transaksi`
  ADD CONSTRAINT `tbl_transaksi_ibfk_1` FOREIGN KEY (`mejaid`) REFERENCES `tbl_meja` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
