# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.9)
# Database: ventanilla
# Generation Time: 2016-04-16 15:30:10 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table APP_USER
# ------------------------------------------------------------

DROP TABLE IF EXISTS `APP_USER`;

CREATE TABLE `APP_USER` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `account_expired` char(1) DEFAULT NULL,
  `account_locked` char(1) DEFAULT NULL,
  `address` varchar(150) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `country` varchar(100) DEFAULT NULL,
  `postal_code` varchar(15) DEFAULT NULL,
  `province` varchar(100) DEFAULT NULL,
  `credentials_expired` char(1) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `account_enabled` char(1) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `password_hint` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `version` decimal(10,0) DEFAULT NULL,
  `website` varchar(255) DEFAULT NULL,
  `co_empresa_afiliadora` int(11) DEFAULT NULL,
  `co_estacion` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table CLPB_EMPRESA_AFILIADORA
# ------------------------------------------------------------

DROP TABLE IF EXISTS `CLPB_EMPRESA_AFILIADORA`;

CREATE TABLE `CLPB_EMPRESA_AFILIADORA` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `co_usuario_creador` varchar(30) DEFAULT NULL,
  `co_usuario_modificador` varchar(30) DEFAULT NULL,
  `da_fecha_creacion` timestamp NULL DEFAULT NULL,
  `da_fecha_modificacion` timestamp NULL DEFAULT NULL,
  `de_empresa_afiliadora` varchar(100) DEFAULT NULL,
  `st_empresa_afiliadora` char(1) DEFAULT NULL,
  `co_programa` decimal(19,0) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table CLPB_ESTACION
# ------------------------------------------------------------

DROP TABLE IF EXISTS `CLPB_ESTACION`;

CREATE TABLE `CLPB_ESTACION` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `co_code_estacion_pgn` varchar(50) DEFAULT NULL,
  `co_codigo_cofide` varchar(50) DEFAULT NULL,
  `co_usuario_creador` varchar(30) DEFAULT NULL,
  `co_usuario_modificador` varchar(30) DEFAULT NULL,
  `da_fecha_creacion` timestamp NULL DEFAULT NULL,
  `da_fecha_modificacion` timestamp NULL DEFAULT NULL,
  `de_direccion` varchar(150) DEFAULT NULL,
  `de_estacion` varchar(150) DEFAULT NULL,
  `st_estacion` char(1) DEFAULT NULL,
  `co_ubigeo` int(11) DEFAULT NULL,
  `va_margen` decimal(19,15) DEFAULT NULL,
  `st_metropolitano` char(1) DEFAULT NULL,
  `de_url` varchar(250) DEFAULT NULL,
  `de_dealer` varchar(10) DEFAULT NULL,
  `da_fecha_inicio_ope` date DEFAULT NULL,
  `da_fecha_fin_ope` date DEFAULT NULL,
  `st_msj_estacion` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table CLPB_PROGRAMA
# ------------------------------------------------------------

DROP TABLE IF EXISTS `CLPB_PROGRAMA`;

CREATE TABLE `CLPB_PROGRAMA` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `co_usuario_creador` varchar(30) DEFAULT NULL,
  `co_usuario_modificador` varchar(30) DEFAULT NULL,
  `da_fecha_creacion` timestamp NULL DEFAULT NULL,
  `da_fecha_fin_vigencia` timestamp NULL DEFAULT NULL,
  `da_fecha_inicio_vigencia` timestamp NULL DEFAULT NULL,
  `de_programa` varchar(150) DEFAULT NULL,
  `nu_aviso1` decimal(19,0) DEFAULT NULL,
  `nu_aviso2` decimal(19,0) DEFAULT NULL,
  `st_disponible_pto_afiliacion` char(1) DEFAULT NULL,
  `st_imprime_mensaje` char(1) DEFAULT NULL,
  `st_programa` char(1) DEFAULT NULL,
  `co_unidad_medida` decimal(19,0) DEFAULT NULL,
  `st_acum_ptos` char(1) DEFAULT NULL,
  `st_afiliacion_por_defecto` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table CLPD_UNIDAD_MEDIDA
# ------------------------------------------------------------

DROP TABLE IF EXISTS `CLPD_UNIDAD_MEDIDA`;

CREATE TABLE `CLPD_UNIDAD_MEDIDA` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `co_usuario_creador` varchar(30) DEFAULT NULL,
  `co_usuario_modificador` varchar(30) DEFAULT NULL,
  `da_fecha_creacion` timestamp NULL DEFAULT NULL,
  `da_fecha_modificacion` timestamp NULL DEFAULT NULL,
  `de_simbolo` varchar(50) DEFAULT NULL,
  `de_unidad_medida` varchar(50) DEFAULT NULL,
  `st_unidad_medida` char(1) DEFAULT NULL,
  `st_indicador_puntos` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table CLPM_UBIGEO
# ------------------------------------------------------------

DROP TABLE IF EXISTS `CLPM_UBIGEO`;

CREATE TABLE `CLPM_UBIGEO` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `co_departamento` int(11) DEFAULT NULL,
  `co_distrito` int(11) DEFAULT NULL,
  `co_provincia` int(11) DEFAULT NULL,
  `co_usuario_creador` varchar(30) DEFAULT NULL,
  `co_usuario_modificador` varchar(30) DEFAULT NULL,
  `da_fecha_creacion` timestamp NULL DEFAULT NULL,
  `da_fecha_modificacion` timestamp NULL DEFAULT NULL,
  `de_departamento` varchar(50) DEFAULT NULL,
  `de_provincia` varchar(50) DEFAULT NULL,
  `st_ubigeo` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table OPCION_MENU
# ------------------------------------------------------------

DROP TABLE IF EXISTS `OPCION_MENU`;

CREATE TABLE `OPCION_MENU` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `co_opcion_menu_padre` int(11) DEFAULT NULL,
  `de_ancho` varchar(19) DEFAULT NULL,
  `de_menu_html` varchar(190) DEFAULT NULL,
  `de_opcion_menu` varchar(190) DEFAULT NULL,
  `nu_nivel` decimal(10,0) DEFAULT NULL,
  `nu_orden` decimal(10,0) DEFAULT NULL,
  `st_opcion_menu` char(1) DEFAULT NULL,
  `co_usuario_creador` varchar(30) DEFAULT NULL,
  `da_fecha_creacion` date DEFAULT NULL,
  `co_usuario_modificador` varchar(30) DEFAULT NULL,
  `da_fecha_modificacion` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table ROLE
# ------------------------------------------------------------

DROP TABLE IF EXISTS `ROLE`;

CREATE TABLE `ROLE` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `description` varchar(64) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table ROLE_OPCION_MENU
# ------------------------------------------------------------

DROP TABLE IF EXISTS `ROLE_OPCION_MENU`;

CREATE TABLE `ROLE_OPCION_MENU` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `co_role` int(11) DEFAULT NULL,
  `co_opcion_menu` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table user_estacion
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_estacion`;

CREATE TABLE `user_estacion` (
  `user_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `estacion_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table USER_ROLE
# ------------------------------------------------------------

DROP TABLE IF EXISTS `USER_ROLE`;

CREATE TABLE `USER_ROLE` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`),
  KEY `role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
