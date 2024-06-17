-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 15, 2024 at 06:49 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ptwcarogame`
--
CREATE DATABASE IF NOT EXISTS `ptwcarogame` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `ptwcarogame`;

-- --------------------------------------------------------

--
-- Table structure for table `achievements`
--

CREATE TABLE `achievements` (
  `id` bigint(20) NOT NULL,
  `achievement_name` varchar(255) DEFAULT NULL,
  `achievement_img_file_path` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `hasachievements`
--

CREATE TABLE `hasachievements` (
  `id` bigint(20) NOT NULL,
  `player_id` bigint(20) NOT NULL,
  `achievement_id` bigint(20) NOT NULL,
  `achieved_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `matches`
--

CREATE TABLE `matches` (
  `id` bigint(20) NOT NULL,
  `match_date` date DEFAULT NULL,
  `total_moves` int(11) DEFAULT NULL,
  `player_1_id` bigint(20) NOT NULL,
  `player_2_id` bigint(20) NOT NULL,
  `win_player_id` bigint(20) DEFAULT NULL,
  `elo_earn` int(11) DEFAULT NULL,
  `elo_lost` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `players`
--

CREATE TABLE `players` (
  `id` bigint(20) NOT NULL,
  `user_uid` varchar(20) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `gender` varchar(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `total_matches` int(11) DEFAULT NULL,
  `win_streak_counts` int(11) DEFAULT NULL,
  `win_matches` int(11) DEFAULT NULL,
  `lost_matches` int(11) DEFAULT NULL,
  `elo_rating_points` int(11) DEFAULT NULL,
  `player_img_file_path` varchar(255) DEFAULT NULL,
  `biography` varchar(255) DEFAULT NULL,
  `joined_date` date DEFAULT NULL,
  `rank_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `players`
--

INSERT INTO `players` (`id`, `user_uid`, `full_name`, `gender`, `email`, `password`, `dob`, `total_matches`, `win_streak_counts`, `win_matches`, `lost_matches`, `elo_rating_points`, `player_img_file_path`, `biography`, `joined_date`, `rank_id`) VALUES
(18, 'P002', 'ggwqq', 'Nam', '1', '123', '2024-06-12', 0, 0, 0, 0, 534, 'HinhAnh.png', ' ', '2024-06-12', 1),
(19, 'P29c3aba7f', 'kphehehaha', 'Nam', '2', '123', '2022-12-05', 0, 0, 0, 0, 1207, 'beoj.jpg', '', '2024-06-12', 1),
(20, 'P3d3754442', 'fqfqw', 'Nam', 'fqw@gmail.com', '123', '2024-06-12', 0, 0, 0, 0, 478, 'HinhAnh.png', ' ', '2024-06-12', 1),
(21, 'P17e22e11b', 'barovinhd', 'Nam', 'test@gmail.com', '123', '2003-12-18', 0, 0, 0, 0, 386, 'girl_img.png', 'img', '2024-06-12', 1),
(22, 'Pfee7134f7', 'dieu ngu', 'Nam', 'dieu@gmail.com', 'beoj.123', '2022-06-14', 0, 0, 0, 0, 500, '/ww', 'img', '2024-06-14', 1),
(23, 'P10e8009f7', 'vinh phan', 'Nam', 'vp123@gmail.com', '123', '2024-06-15', 0, 0, 0, 0, 503, 'beoj.jpg', 'vcf', '2024-06-15', 1),
(24, 'P10407c832', 'dieu co bap', 'Nam', 'dieuquangbo@gmail.com', '123', '2024-06-15', 0, 0, 0, 0, 524, '', 'HinhAnh.png', '2024-06-15', 1),
(25, 'Pf9a007e7a', 'vuthanhduong123', 'Nam', 'mail@mail.com', '123', '2024-06-15', 0, 0, 0, 0, 479, 'HinhAnh.png', ' ', '2024-06-15', 1),
(26, 'P3876aac5f', 'Diệu Dí Dỏm', 'Nam', 'boquangdieu2003@gmail.com', '123', '2024-06-15', 0, 0, 0, 0, 528, '', 'HinhAnh.png', '2024-06-15', 1),
(27, 'P9e91ba696', 'kkkkk', 'Nam', '123@gmail.com', '123', '2024-06-15', 0, 0, 0, 0, 500, 'girl_img.png', 'HinhAnh.png', '2024-06-15', 1),
(28, 'Pf6792864d', 'dieu', 'Nam', 'dieu123@gmail.com', '123', '2024-06-15', 0, 0, 0, 0, 16049, 'HinhAnh.png', ' ', '2024-06-15', 1),
(29, 'P480759990', 'rqnrlqrwlkj', 'Nam', 'ko@gmail.com', '123', '2024-06-15', 0, 0, 0, 0, -222, 'girl_img.png', 'HinhAnh.png', '2024-06-15', 1),
(30, 'P65ca4d09a', 'ko', 'Nam', 'lol@gmail.com', '123', '2024-06-15', 0, 0, 0, 0, 500, '/HinhAnh.png', ' ', '2024-06-15', 1),
(31, 'Pe36669687', 'ewfw', 'Nam', '1232@gmail.com', '123', '2024-06-15', 0, 0, 0, 0, 500, 'girl_img.png', 'HinhAnh.png', '2024-06-15', 1),
(32, 'P5b9cdfa7b', 'vu thanh duong', 'Nam', 'mailmail@gmail.com', '09122002', '2024-06-15', 0, 0, 0, 0, 500, 'man_img.png', ' ', '2024-06-15', 1);

-- --------------------------------------------------------

--
-- Table structure for table `ranks`
--

CREATE TABLE `ranks` (
  `id` bigint(20) NOT NULL,
  `rank_name` varchar(255) NOT NULL,
  `rank_img_file_path` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `ranks`
--

INSERT INTO `ranks` (`id`, `rank_name`, `rank_img_file_path`) VALUES
(1, 'Đồng', 'hinhDong.png'),
(2, 'Bạc', 'hinhDong.png'),
(3, 'Vàng', 'hinhDong.png'),
(4, 'Bạch kim', 'hinhDong.png'),
(5, 'Kim Cương', 'hinhDong.png'),
(6, 'Cao thủ', 'hinhDong.png'),
(7, 'Thách đấu', 'hinhDong.png');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `achievements`
--
ALTER TABLE `achievements`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `hasachievements`
--
ALTER TABLE `hasachievements`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_HasAchievements_Achievements` (`achievement_id`),
  ADD KEY `FK_HasAchievements_Players` (`player_id`);

--
-- Indexes for table `matches`
--
ALTER TABLE `matches`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_Maches_Players_1` (`player_1_id`),
  ADD KEY `FK_Matches_Players_2` (`player_2_id`);

--
-- Indexes for table `players`
--
ALTER TABLE `players`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_Players_Ranks` (`rank_id`);

--
-- Indexes for table `ranks`
--
ALTER TABLE `ranks`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `achievements`
--
ALTER TABLE `achievements`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `hasachievements`
--
ALTER TABLE `hasachievements`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `matches`
--
ALTER TABLE `matches`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `players`
--
ALTER TABLE `players`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT for table `ranks`
--
ALTER TABLE `ranks`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `hasachievements`
--
ALTER TABLE `hasachievements`
  ADD CONSTRAINT `FK_HasAchievements_Achievements` FOREIGN KEY (`achievement_id`) REFERENCES `achievements` (`id`),
  ADD CONSTRAINT `FK_HasAchievements_Players` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`);

--
-- Constraints for table `matches`
--
ALTER TABLE `matches`
  ADD CONSTRAINT `FK_Maches_Players_1` FOREIGN KEY (`player_1_id`) REFERENCES `players` (`id`),
  ADD CONSTRAINT `FK_Matches_Players_2` FOREIGN KEY (`player_2_id`) REFERENCES `players` (`id`);

--
-- Constraints for table `players`
--
ALTER TABLE `players`
  ADD CONSTRAINT `FK_Players_Ranks` FOREIGN KEY (`rank_id`) REFERENCES `ranks` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
