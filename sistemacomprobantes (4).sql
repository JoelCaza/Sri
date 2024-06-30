-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 30-06-2024 a las 21:40:35
-- Versión del servidor: 10.4.27-MariaDB
-- Versión de PHP: 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `sistemacomprobantes`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `comprobantes`
--

CREATE TABLE `comprobantes` (
  `id` int(11) NOT NULL,
  `ruc_emisor` varchar(13) NOT NULL,
  `razon_social_emisor` varchar(100) NOT NULL,
  `tipo_comprobante` varchar(50) NOT NULL,
  `serie_comprobante` varchar(50) NOT NULL,
  `clave_acceso` varchar(49) NOT NULL,
  `fecha_autorizacion` timestamp NULL DEFAULT NULL,
  `fecha_emision` date DEFAULT NULL,
  `identificacion_receptor` varchar(20) NOT NULL,
  `valor_sin_impuestos` double NOT NULL,
  `iva` double NOT NULL,
  `importe_total` double NOT NULL,
  `numero_documento_modificado` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `ruc` varchar(13) NOT NULL,
  `password` varchar(50) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `fecha_nacimiento` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `ruc`, `password`, `nombre`, `apellido`, `email`, `fecha_nacimiento`) VALUES
(2, '1750000349001', 'joel123', 'Joel', 'Caza', 'joelcaza@hotmail.com', '2002-10-12'),
(4, '1705250155001', '123', 'joel', 'asd', 'joelcaza@hotmail.com', '1998-10-12');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `comprobantes`
--
ALTER TABLE `comprobantes`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `comprobantes`
--
ALTER TABLE `comprobantes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=347;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
