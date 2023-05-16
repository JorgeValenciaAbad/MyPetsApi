-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: db:3306
-- Tiempo de generación: 16-05-2023 a las 10:51:45
-- Versión del servidor: 8.0.23
-- Versión de PHP: 8.1.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `protectora`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `adoption`
--

CREATE TABLE `adoption` (
  `user_id` int NOT NULL,
  `pet_id` int NOT NULL,
  `accept` bit(1) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `adoption`
--

INSERT INTO `adoption` (`user_id`, `pet_id`, `accept`) VALUES
(2, 1, b'0'),
(2, 3, b'0');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lost`
--

CREATE TABLE `lost` (
  `id` int NOT NULL,
  `summary` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `image` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `lost`
--

INSERT INTO `lost` (`id`, `summary`, `image`) VALUES
(6, 'Se ha perdido un animal en peligro de extincin ayer por Valladolid. La ultima vez que se vió fue en las oficinas de Madisson con un ordenador.\nSi se encuentra llame a este telefono: \n\n1234567809.', '1000000033.jpg'),
(7, 'bkdbkjsbbvskdbvkdsvbdksvbskvbskvbdkvbskvbskbvksvbksvbkvbskvbskvbskvbskvbskvbksvbskvbksbksvbkvbksvbksvbksvbksvbskvbsvbksvbv', '1000000033.jpg');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pets`
--

CREATE TABLE `pets` (
  `id` int NOT NULL,
  `adoption` bit(1) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `breed` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `location` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `summary` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cats` bit(1) DEFAULT NULL,
  `dogs` bit(1) DEFAULT NULL,
  `humans` bit(1) DEFAULT NULL,
  `sex` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `size` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `weight` double DEFAULT NULL,
  `image` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `pets`
--

INSERT INTO `pets` (`id`, `adoption`, `age`, `breed`, `location`, `name`, `summary`, `type`, `cats`, `dogs`, `humans`, `sex`, `size`, `weight`, `image`) VALUES
(1, b'0', 2, 'Pastor Aleman', 'Barcelona', 'Pancho', 'Perro cariñoso y jugeton, encontrado en la calle, en busca de un hogar ', 'perro', b'0', b'0', b'1', 'male', 'medium', 17.5, 'jorge.jpg'),
(2, b'0', 5, 'Dalmata', 'Santander', 'Lila', 'Perra cariñosa y tranquila, encontrada atada en el campo, en busca de un hogar ', 'perro', b'0', b'1', b'1', 'female', 'big', 24.7, 'lila.jpg'),
(3, b'0', 5, 'Siamese', 'London', 'Max', 'Hello my name is Max. I am a cute cat who is looking for a home ', 'gato', b'1', b'1', b'1', 'male', 'small', 3.5, 'max.jpg');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE `users` (
  `id` int NOT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `pass` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `image` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ld` bit(1) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`id`, `email`, `name`, `pass`, `phone`, `image`, `ld`) VALUES
(2, 'jorgevalenciaabad18@gmail.com', 'Jorge', '$2a$10$/nUetL4p/auxhLQhJu3Qe.GWsTSX9IWNbdmKFhVzYeyWuWHiiuEPW', '+34670266954', '1000000033.jpg', b'0'),
(9, 'jgamesv18@gmail.com', 'Paco Jones 69', '$2a$10$EbfgF/v4HsvEmV2RvKWOl.LpNFoCDgz2YS83IxqQ7IHaRqrzOZsN2', '+34632154578', 'default.png', b'0');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `adoption`
--
ALTER TABLE `adoption`
  ADD KEY `FKlc1y357snjb9qirpevdra8c9g` (`pet_id`),
  ADD KEY `FK7anvrufcvnqg02u65p2b0lbmy` (`user_id`);

--
-- Indices de la tabla `lost`
--
ALTER TABLE `lost`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `pets`
--
ALTER TABLE `pets`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `phone` (`phone`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `lost`
--
ALTER TABLE `lost`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `pets`
--
ALTER TABLE `pets`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `users`
--
ALTER TABLE `users`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
