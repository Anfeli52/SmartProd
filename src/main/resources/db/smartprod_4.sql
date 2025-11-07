-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 07-11-2025 a las 21:01:34
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `smartprod`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `items`
--

CREATE TABLE `items` (
  `numero_item` bigint(20) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `cantidad_pintura` float NOT NULL,
  `lavado` float NOT NULL,
  `pintura` float NOT NULL,
  `horneo` float NOT NULL,
  `estado` enum('ACTIVO','INACTIVO') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `items`
--

INSERT INTO `items` (`numero_item`, `nombre`, `cantidad_pintura`, `lavado`, `pintura`, `horneo`, `estado`) VALUES
(25408, 'CILINDRO EN ALUMINIO P/BALA SATURNO 33W', 0.09, 0.636, 1, 0.636, 'ACTIVO'),
(27421, 'CILINDRO EN ALUMINIO P/BALA AURA 12W', 0.09, 0.66, 1.6, 0.66, 'INACTIVO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reportes`
--

CREATE TABLE `reportes` (
  `id` bigint(20) NOT NULL,
  `nombre_operario` varchar(255) DEFAULT NULL,
  `actividad` enum('LAVADO','PINTURA','HORNEO') DEFAULT NULL,
  `cantidad` bigint(20) DEFAULT NULL,
  `fecha` date NOT NULL,
  `numero_item` bigint(20) NOT NULL,
  `hora_inicio` time(6) DEFAULT NULL,
  `hora_final` time(6) DEFAULT NULL,
  `motivo_paro` varchar(255) DEFAULT NULL,
  `tiempo_paro` double DEFAULT NULL,
  `conforme` bigint(20) NOT NULL,
  `no_conforme` bigint(20) DEFAULT NULL,
  `motivo` varchar(255) DEFAULT NULL,
  `disposicion_pnc` enum('REINCORPORAR','DESECHAR','NO_APLICA') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `reportes`
--

INSERT INTO `reportes` (`id`, `nombre_operario`, `actividad`, `cantidad`, `fecha`, `numero_item`, `hora_inicio`, `hora_final`, `motivo_paro`, `tiempo_paro`, `conforme`, `no_conforme`, `motivo`, `disposicion_pnc`) VALUES
(2, 'Andrés Medina', 'LAVADO', 10, '2025-10-19', 27421, '17:44:00.000000', '19:44:00.000000', '', NULL, 8, 2, 'Material Dañado', 'DESECHAR'),
(5, 'Andrés Medina', 'HORNEO', 12, '2025-10-23', 27421, '15:09:00.000000', '16:09:00.000000', '', NULL, 10, 2, 'Láminas Dobladas', 'REINCORPORAR');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `nombre` varchar(50) NOT NULL,
  `correo` varchar(100) NOT NULL,
  `contrasena` varchar(100) NOT NULL,
  `rol` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`nombre`, `correo`, `contrasena`, `rol`) VALUES
('Andrés Medina', 'andres_medina@iltec.com', '$2a$12$Q/k3uJSJNA3rl4RaYTr9S.So1iGJ68RwgFYUOCpnYZisFTvYs8yve', 'ANALISTA'),
('Andrés Medina', 'felipe_diaz@iltec.com', '$2a$12$Q/k3uJSJNA3rl4RaYTr9S.So1iGJ68RwgFYUOCpnYZisFTvYs8yve', 'SUPERVISOR');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `items`
--
ALTER TABLE `items`
  ADD PRIMARY KEY (`numero_item`);

--
-- Indices de la tabla `reportes`
--
ALTER TABLE `reportes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `reporte-item` (`numero_item`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`correo`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `reportes`
--
ALTER TABLE `reportes`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `reportes`
--
ALTER TABLE `reportes`
  ADD CONSTRAINT `reporte-item` FOREIGN KEY (`numero_item`) REFERENCES `items` (`numero_item`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
