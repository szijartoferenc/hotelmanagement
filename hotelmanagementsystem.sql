-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Gép: 127.0.0.1
-- Létrehozás ideje: 2025. Máj 24. 10:57
-- Kiszolgáló verziója: 10.4.32-MariaDB
-- PHP verzió: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Adatbázis: `hotelmanagementsystem`
--

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `customer`
--

CREATE TABLE `customer` (
  `id` varchar(20) NOT NULL,
  `number` varchar(30) NOT NULL,
  `name` varchar(30) NOT NULL,
  `gender` varchar(15) NOT NULL,
  `country` varchar(20) NOT NULL,
  `room` varchar(10) NOT NULL,
  `checkintime` varchar(80) NOT NULL,
  `deposit` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- A tábla adatainak kiíratása `customer`
--

INSERT INTO `customer` (`id`, `number`, `name`, `gender`, `country`, `room`, `checkintime`, `deposit`) VALUES
('ID card', '119589LK', 'Peter Kol', 'Male', 'Germany', '100', 'Wed May 21 16:48:34 CEST 2025', '1475');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `department`
--

CREATE TABLE `department` (
  `department` varchar(30) NOT NULL,
  `budget` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- A tábla adatainak kiíratása `department`
--

INSERT INTO `department` (`department`, `budget`) VALUES
('Front Office', '500000'),
('Housekeeping', '40000'),
('Food and Beverage', '23000'),
('Kitchen or Food Production', '540000'),
('Security', '320000');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `driver`
--

CREATE TABLE `driver` (
  `name` varchar(20) NOT NULL,
  `age` varchar(10) NOT NULL,
  `gender` varchar(15) NOT NULL,
  `company` varchar(20) NOT NULL,
  `brand` varchar(20) NOT NULL,
  `available` varchar(20) NOT NULL,
  `location` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- A tábla adatainak kiíratása `driver`
--

INSERT INTO `driver` (`name`, `age`, `gender`, `company`, `brand`, `available`, `location`) VALUES
('Peter', '30', 'Male', 'Edus', 'Fiat', 'Available', 'Downtown');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `employee`
--

CREATE TABLE `employee` (
  `name` varchar(25) NOT NULL,
  `age` varchar(10) NOT NULL,
  `gender` varchar(15) NOT NULL,
  `job` varchar(30) NOT NULL,
  `salary` varchar(15) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `email` varchar(40) NOT NULL,
  `ssn` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- A tábla adatainak kiíratása `employee`
--

INSERT INTO `employee` (`name`, `age`, `gender`, `job`, `salary`, `phone`, `email`, `ssn`) VALUES
('Próba Géza', '43', 'Male', 'Front Desk Clerks', '15450.0', '+36701112233', 'probageza@mail.com', '145444589');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `login`
--

CREATE TABLE `login` (
  `username` varchar(25) NOT NULL,
  `password` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- A tábla adatainak kiíratása `login`
--

INSERT INTO `login` (`username`, `password`) VALUES
('admin', '12345');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `room`
--

CREATE TABLE `room` (
  `roomnumber` varchar(10) NOT NULL,
  `availability` varchar(20) NOT NULL,
  `cleaning_status` varchar(20) NOT NULL,
  `price` varchar(20) NOT NULL,
  `bed_type` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- A tábla adatainak kiíratása `room`
--

INSERT INTO `room` (`roomnumber`, `availability`, `cleaning_status`, `price`, `bed_type`) VALUES
('100', 'Occupied', 'Cleaned', '1478', 'Single Bed');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
