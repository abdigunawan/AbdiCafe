-- phpMyAdmin SQL Dump
-- version 4.8.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 30, 2021 at 10:09 AM
-- Server version: 10.1.32-MariaDB
-- PHP Version: 5.6.36

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
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
('182004', 'Abdi Gunawan', 'Teknik Informatika', 'Islam', '082293204972', 'Bone', 'Laki Laki', 'Ngoding');

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
-- Indexes for table `biodata_mahasiswa_182004_abdigunawan`
--
ALTER TABLE `biodata_mahasiswa_182004_abdigunawan`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id` (`id`,`nim`);

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
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
