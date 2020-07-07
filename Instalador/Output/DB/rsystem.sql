-- MySQL dump 10.13  Distrib 5.6.24, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: rsystem
-- ------------------------------------------------------
-- Server version	5.6.26-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `caja`
--

DROP TABLE IF EXISTS `caja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `caja` (
  `idcaja` int(11) NOT NULL,
  `fechainicio` datetime DEFAULT NULL,
  `apertura` int(11) DEFAULT NULL,
  `entrada` int(11) DEFAULT NULL,
  `salida` int(11) DEFAULT NULL,
  `cierre` int(11) DEFAULT NULL,
  `cierrecaja` int(11) DEFAULT NULL,
  `diferenciacierre` int(11) DEFAULT NULL,
  `estado` int(11) DEFAULT NULL,
  `fechacierre` date DEFAULT NULL,
  `idempleado` int(11) NOT NULL,
  PRIMARY KEY (`idcaja`),
  KEY `fk_caja_empleado1` (`idempleado`),
  CONSTRAINT `fk_caja_empleado1` FOREIGN KEY (`idempleado`) REFERENCES `empleado` (`idempleado`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `caja`
--

LOCK TABLES `caja` WRITE;
/*!40000 ALTER TABLE `caja` DISABLE KEYS */;
INSERT INTO `caja` VALUES (1,'2016-11-19 00:00:00',100000,54000,0,0,0,0,0,'2016-11-19',3),(2,'2016-11-19 00:00:00',500000,608000,40000,1003000,1003000,0,0,'2016-11-19',1),(3,'2016-11-19 00:00:00',200000,912000,40000,1007000,1000000,7000,0,'2016-11-19',1),(4,'2016-11-19 00:00:00',300000,0,0,0,300000,-300000,0,'2016-11-22',1),(5,'2016-11-22 00:00:00',100000,0,0,0,80000,-80000,0,'2016-11-22',2),(6,'2016-11-22 00:00:00',500000,0,0,0,0,0,0,'2016-11-22',2),(7,'2016-11-22 00:00:00',100000,350000,0,0,900000,-900000,0,'2016-11-25',2),(8,'2016-11-25 00:00:00',100000,400000,0,0,300000,-300000,0,'2016-11-25',3),(9,'2016-11-25 00:00:00',50000,450000,0,0,450000,-450000,0,'2016-11-25',3),(10,'2016-11-25 00:00:00',50000,2400000,0,0,2450000,-2450000,0,'2016-11-27',3),(11,'2016-11-27 00:00:00',100000,990000,25000,965000,965000,0,0,'2016-12-14',1),(12,'2016-12-14 00:00:00',100000,2280000,25000,2255000,2255000,0,0,'2016-12-14',1),(13,'2016-12-14 00:00:00',200000,520000,3210000,2498000,2498000,0,0,'2016-12-15',3),(14,'2016-12-20 00:00:00',100000,0,0,0,0,0,1,'2016-12-20',1);
/*!40000 ALTER TABLE `caja` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categoria` (
  `idcategoria` int(11) NOT NULL,
  `descripcion` char(50) DEFAULT NULL,
  PRIMARY KEY (`idcategoria`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (1,'televisores'),(2,'placamadre'),(3,'speakers'),(4,'memorias'),(5,'placa de red'),(6,'router'),(7,'pilas'),(8,'mouse'),(9,'cartuchos'),(10,'Disco duro'),(11,'Pendrive'),(12,'cables'),(13,'teclado'),(14,'notebook');
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cliente` (
  `idcliente` int(11) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `apellido` varchar(45) DEFAULT NULL,
  `edad` int(11) DEFAULT NULL,
  `cinro` varchar(45) DEFAULT NULL,
  `telefono` varchar(45) DEFAULT NULL,
  `direccion` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idcliente`),
  UNIQUE KEY `cinro_UNIQUE` (`cinro`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,'jose','caballero',19,'23655','09745','nl','gyu@gmail.com'),(2,'marelo','benitez',22,'45211','0971542236','ykua','lalala@gmail.com'),(3,'juan','vazquez',26,'2133354','0976123456','villa del maestro','esti@gmail.com'),(5,'mario','lobos',23,'523144','09784512','villarrica','loslobos@hotmail.com'),(6,'alberto','samudio',49,'1336367-0','0981299536','coronel oviedo',''),(7,'lalo','monte',65,'523654-2','097145621','',''),(8,'asss','asdf',26,'555','0322145','',''),(9,'Cliente','Casual',30,'12345','000000','',''),(10,'widilfo ','valdez y cia srl',50,'80025231-4','09741115','',''),(11,'arnaldo','gutierrez',27,'1223547','0974521123','caaguazu',''),(12,'jhonny','aguilar',23,'5132423','0971262764','villarrica y facundo insfran','');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detallecaja`
--

DROP TABLE IF EXISTS `detallecaja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detallecaja` (
  `iddetallecaja` int(11) NOT NULL,
  `concepto` varchar(45) DEFAULT NULL,
  `entrada` int(11) DEFAULT NULL,
  `salida` int(11) DEFAULT NULL,
  `fechahora` datetime DEFAULT NULL,
  `nota` varchar(45) DEFAULT NULL,
  `idcaja` int(11) NOT NULL,
  PRIMARY KEY (`iddetallecaja`),
  KEY `fk_detallecaja_caja1` (`idcaja`),
  CONSTRAINT `fk_detallecaja_caja1` FOREIGN KEY (`idcaja`) REFERENCES `caja` (`idcaja`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detallecaja`
--

LOCK TABLES `detallecaja` WRITE;
/*!40000 ALTER TABLE `detallecaja` DISABLE KEYS */;
/*!40000 ALTER TABLE `detallecaja` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detallecompra`
--

DROP TABLE IF EXISTS `detallecompra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detallecompra` (
  `idproducto` int(11) NOT NULL,
  `idfacturacompra` int(11) NOT NULL,
  `preciodecompra` int(11) DEFAULT NULL,
  `cantidad` int(11) DEFAULT NULL,
  `iva10%` int(11) DEFAULT NULL,
  `iva5%` int(11) DEFAULT NULL,
  `exentas` int(11) DEFAULT NULL,
  KEY `fk_detallecompra_FacturaCompra1` (`idfacturacompra`),
  KEY `fk_detallecompra_productos1` (`idproducto`),
  CONSTRAINT `fk_detallecompra_FacturaCompra1` FOREIGN KEY (`idfacturacompra`) REFERENCES `facturacompra` (`idfacturacompra`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_detallecompra_productos1` FOREIGN KEY (`idproducto`) REFERENCES `productos` (`idproducto`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detallecompra`
--

LOCK TABLES `detallecompra` WRITE;
/*!40000 ALTER TABLE `detallecompra` DISABLE KEYS */;
INSERT INTO `detallecompra` VALUES (1,2,20000,2,0,0,36000),(4,3,60000,10,0,0,120000),(13,4,25000,1,0,0,50000),(2,5,30000,7,0,0,54000),(15,5,1500000,2,0,0,2100000);
/*!40000 ALTER TABLE `detallecompra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalleventa`
--

DROP TABLE IF EXISTS `detalleventa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detalleventa` (
  `idproducto` int(11) NOT NULL,
  `idfacturaventa` int(11) NOT NULL,
  `preciodeventa` int(11) DEFAULT NULL,
  `cantidad` int(11) DEFAULT NULL,
  `iva10%` int(11) DEFAULT NULL,
  `iva5%` int(11) DEFAULT NULL,
  `exentas` int(11) DEFAULT NULL,
  KEY `fk_productos_has_FacturaVenta_FacturaVenta1` (`idfacturaventa`),
  KEY `fk_productos_has_FacturaVenta_productos1` (`idproducto`),
  CONSTRAINT `fk_productos_has_FacturaVenta_FacturaVenta1` FOREIGN KEY (`idfacturaventa`) REFERENCES `facturaventa` (`idfacturaventa`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_productos_has_FacturaVenta_productos1` FOREIGN KEY (`idproducto`) REFERENCES `productos` (`idproducto`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalleventa`
--

LOCK TABLES `detalleventa` WRITE;
/*!40000 ALTER TABLE `detalleventa` DISABLE KEYS */;
INSERT INTO `detalleventa` VALUES (2,7,54000,1,54000,0,0),(5,8,130000,1,130000,0,0),(4,8,120000,1,120000,0,0),(3,9,66500,1,66500,0,0),(2,10,54000,1,54000,0,0),(9,10,110000,1,110000,0,0),(8,11,110000,1,110000,0,0),(2,11,54000,1,54000,0,0),(6,12,90000,1,90000,0,0),(2,12,54000,1,54000,0,0),(4,13,120000,1,120000,0,0),(4,14,120000,3,360000,0,0),(4,15,120000,4,480000,0,0),(7,16,90000,1,90000,0,0),(6,17,90000,1,90000,0,0),(13,18,50000,1,50000,0,0),(8,19,110000,1,110000,0,0),(9,19,110000,2,220000,0,0),(3,20,66500,1,66500,0,0),(1,20,36000,1,36000,0,0),(14,21,2600000,1,2600000,0,0),(5,22,130000,1,130000,0,0),(9,23,110000,1,110000,0,0),(2,24,54000,1,54000,0,0),(6,24,90000,1,90000,0,0),(8,24,110000,2,220000,0,0),(9,24,110000,1,110000,0,0),(4,25,120000,1,120000,0,0),(11,26,200,2,400,0,0),(8,26,110000,3,330000,0,0),(13,27,50000,2,100000,0,0),(1,27,36000,3,108000,0,0),(3,28,66500,1,66500,0,0),(9,29,110000,1,110000,0,0),(5,30,130000,1,130000,0,0),(6,31,90000,1,90000,0,0),(6,32,90000,1,90000,0,0),(5,33,130000,1,130000,0,0),(4,33,120000,4,480000,0,0),(15,34,2100000,1,2100000,0,0),(15,35,2100000,1,2100000,0,0),(15,36,2100000,1,2100000,0,0),(6,37,90000,1,90000,0,0),(8,37,110000,1,110000,0,0);
/*!40000 ALTER TABLE `detalleventa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empleado`
--

DROP TABLE IF EXISTS `empleado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `empleado` (
  `idempleado` int(11) NOT NULL,
  `nombre` char(25) DEFAULT NULL,
  `apellido` char(25) DEFAULT NULL,
  `cargo` char(25) DEFAULT NULL,
  `sueldo` int(11) DEFAULT NULL,
  `nivel` int(11) DEFAULT NULL,
  `usuario` varchar(45) DEFAULT NULL,
  `contraseña` varchar(45) DEFAULT NULL,
  `cinro` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idempleado`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empleado`
--

LOCK TABLES `empleado` WRITE;
/*!40000 ALTER TABLE `empleado` DISABLE KEYS */;
INSERT INTO `empleado` VALUES (1,'oscar','riveros','gerente',15000000,1,'oscar','12345','5099621'),(2,'jose','campos','empleado',5000000,0,'jose','campos','213365'),(3,'ivan','lopez','administrador',8000000,1,'ivan','12345','4121482'),(4,'javier','ojeda','asisten tecnico',3200000,0,'cater','cater123','5567097'),(5,'basilio','mendoza','asistente tecnico',4800000,0,'basilio','basilio123','32108108'),(6,'asdfg','sdfg','aaaa',2500000,0,'as','12345','23365');
/*!40000 ALTER TABLE `empleado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empresa`
--

DROP TABLE IF EXISTS `empresa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `empresa` (
  `nombre` char(25) DEFAULT NULL,
  `apellido` char(25) DEFAULT NULL,
  `telefono` char(25) DEFAULT NULL,
  `direccion` char(25) DEFAULT NULL,
  `empresa` char(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empresa`
--

LOCK TABLES `empresa` WRITE;
/*!40000 ALTER TABLE `empresa` DISABLE KEYS */;
/*!40000 ALTER TABLE `empresa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entregainicial`
--

DROP TABLE IF EXISTS `entregainicial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `entregainicial` (
  `idEntregaInicial` int(11) NOT NULL,
  `plazo` int(11) DEFAULT NULL,
  `saldo` int(11) DEFAULT NULL,
  `montocuota` int(11) DEFAULT NULL,
  `entregainicial` int(11) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  PRIMARY KEY (`idEntregaInicial`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entregainicial`
--

LOCK TABLES `entregainicial` WRITE;
/*!40000 ALTER TABLE `entregainicial` DISABLE KEYS */;
/*!40000 ALTER TABLE `entregainicial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `facturacompra`
--

DROP TABLE IF EXISTS `facturacompra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `facturacompra` (
  `idfacturacompra` int(11) NOT NULL,
  `numero` int(11) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `estado` char(30) DEFAULT NULL,
  `anulado` char(30) DEFAULT NULL,
  `idproveedor` int(11) NOT NULL,
  `ivatotal` int(11) DEFAULT NULL,
  `pagado` tinyint(4) DEFAULT NULL,
  `condiciondeventa` int(11) DEFAULT NULL,
  `idempleado` int(11) NOT NULL,
  `ivatotal10%` varchar(45) DEFAULT NULL,
  `ivatotal5%` int(11) DEFAULT NULL,
  `exentastotal` int(11) DEFAULT NULL,
  `plazo` int(11) DEFAULT NULL,
  `saldo` int(11) DEFAULT NULL,
  `montocuota` int(11) DEFAULT NULL,
  `entregainicial` int(11) DEFAULT NULL,
  `fechavence` datetime DEFAULT NULL,
  PRIMARY KEY (`idfacturacompra`),
  KEY `fk_FacturaCompra_empleado1` (`idempleado`),
  KEY `fk_FacturaCompra_proveedor1` (`idproveedor`),
  CONSTRAINT `fk_FacturaCompra_empleado1` FOREIGN KEY (`idempleado`) REFERENCES `empleado` (`idempleado`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_FacturaCompra_proveedor1` FOREIGN KEY (`idproveedor`) REFERENCES `proveedor` (`idproveedor`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facturacompra`
--

LOCK TABLES `facturacompra` WRITE;
/*!40000 ALTER TABLE `facturacompra` DISABLE KEYS */;
INSERT INTO `facturacompra` VALUES (1,1,'2016-11-09','0','0',2,22727,0,0,3,'250000.0',0,0,1,0,0,250000,'2016-11-09 00:00:00'),(2,2,'2016-11-19','0','0',3,3636,0,0,1,'40000.0',0,0,1,0,0,40000,'2016-11-19 00:00:00'),(3,3,'2016-11-20','0','0',5,54545,0,0,3,'600000.0',0,0,1,0,0,600000,'2016-11-20 00:00:00'),(4,4,'2016-12-14','0','0',3,2273,0,0,1,'25000.0',0,0,1,0,0,25000,'2016-12-14 00:00:00'),(5,5,'2016-12-15','0','0',5,291818,0,0,3,'3210000.0',0,0,1,0,0,3210000,'2016-12-15 00:00:00');
/*!40000 ALTER TABLE `facturacompra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `facturaventa`
--

DROP TABLE IF EXISTS `facturaventa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `facturaventa` (
  `idfacturaventa` int(11) NOT NULL AUTO_INCREMENT,
  `numerofactura` int(11) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `estado` varchar(45) DEFAULT NULL,
  `anulado` varchar(45) DEFAULT NULL,
  `idcliente` int(11) NOT NULL,
  `ivatotal` int(11) DEFAULT NULL,
  `condiciondeventa` char(45) DEFAULT NULL,
  `idempleado` int(11) NOT NULL,
  `ivatotal10%` int(11) DEFAULT NULL,
  `ivatotal5%` int(11) DEFAULT NULL,
  `exentastotal` int(11) DEFAULT NULL,
  `plazo` int(11) DEFAULT NULL,
  `saldo` int(11) DEFAULT NULL,
  `montocuota` int(11) DEFAULT NULL,
  `entregainicial` int(11) DEFAULT NULL,
  `fechavence` date DEFAULT NULL,
  PRIMARY KEY (`idfacturaventa`),
  KEY `fk_FacturaVenta_cliente1` (`idcliente`),
  KEY `fk_FacturaVenta_empleado1` (`idempleado`),
  CONSTRAINT `fk_FacturaVenta_cliente1` FOREIGN KEY (`idcliente`) REFERENCES `cliente` (`idcliente`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_FacturaVenta_empleado1` FOREIGN KEY (`idempleado`) REFERENCES `empleado` (`idempleado`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facturaventa`
--

LOCK TABLES `facturaventa` WRITE;
/*!40000 ALTER TABLE `facturaventa` DISABLE KEYS */;
INSERT INTO `facturaventa` VALUES (1,1,'2016-11-09','0','0',3,31818,'contado',3,350000,0,0,1,0,0,350000,'2016-11-09'),(2,2,'2016-11-09','pagado','si',5,31818,'Credito',3,350000,0,0,2,0,87500,175000,'2016-11-12'),(3,3,'2016-11-09','0','0',3,216364,'contado',3,2380000,0,0,1,0,0,2380000,'2016-11-09'),(4,4,'2016-11-09','0','0',3,11455,'Credito',3,126000,0,0,2,26000,13000,100000,'2016-11-13'),(5,5,'2016-11-12','pagado','si',3,432727,'Credito',3,4760000,0,0,4,0,940000,1000000,'2016-11-12'),(6,6,'2016-11-16','0','0',2,31818,'contado',1,350000,0,0,1,0,0,350000,'2016-11-16'),(7,7,'2016-11-19','0','0',1,4909,'contado',3,54000,0,0,1,0,0,54000,'2016-11-19'),(8,8,'2016-11-19','0','0',8,22727,'contado',1,250000,0,0,1,0,0,250000,'2016-11-19'),(9,9,'2016-11-23','0','0',1,6045,'contado',1,66500,0,0,1,0,0,66500,'2016-11-23'),(10,10,'2016-11-23','0','0',2,14909,'contado',1,164000,0,0,1,0,0,164000,'2016-11-23'),(11,11,'2016-11-23','0','0',5,14909,'contado',1,164000,0,0,1,0,0,164000,'2016-11-23'),(12,12,'2016-11-23','0','0',1,13091,'contado',1,144000,0,0,1,0,0,144000,'2016-11-23'),(13,13,'2016-11-23','0','0',8,10909,'contado',3,120000,0,0,1,0,0,120000,'2016-11-23'),(14,14,'2016-11-23','0','0',6,32727,'contado',1,360000,0,0,1,0,0,360000,'2016-11-23'),(15,15,'2016-11-23','0','0',8,43636,'contado',1,480000,0,0,1,0,0,480000,'2016-11-23'),(16,16,'2016-11-23','0','0',3,8182,'contado',1,90000,0,0,1,0,0,90000,'2016-11-23'),(17,17,'2016-11-23','0','0',3,8182,'contado',1,90000,0,0,1,0,0,90000,'2016-11-23'),(18,18,'2016-11-25','0','0',1,4545,'contado',3,50000,0,0,1,0,0,50000,'2016-11-25'),(19,19,'2016-11-25','0','0',3,30000,'contado',3,330000,0,0,1,0,0,330000,'2016-11-25'),(20,19,'2016-11-25','0','0',5,9318,'contado',3,102500,0,0,1,0,0,102500,'2016-11-25'),(21,20,'2016-11-25','0','0',5,236364,'Credito',3,2600000,0,0,4,1600000,400000,1000000,'2016-11-25'),(22,21,'2016-11-27','0','0',1,11818,'contado',3,130000,0,0,1,0,0,130000,'2016-11-27'),(23,22,'2016-11-27','0','0',8,10000,'contado',3,110000,0,0,1,0,0,110000,'2016-11-27'),(24,23,'2016-11-27','0','0',5,43091,'contado',3,474000,0,0,1,0,0,474000,'2016-11-27'),(25,24,'2016-11-29','0','0',1,10909,'contado',1,120000,0,0,1,0,0,120000,'2016-11-29'),(26,25,'2016-11-29','0','0',9,30036,'contado',1,330400,0,0,1,0,0,330400,'2016-11-29'),(27,26,'2016-11-29','0','0',9,18909,'contado',1,208000,0,0,1,0,0,208000,'2016-11-29'),(28,27,'2016-11-29','0','0',5,6045,'contado',1,66500,0,0,1,0,0,66500,'2016-11-29'),(29,28,'2016-11-29','0','0',9,10000,'contado',1,110000,0,0,1,0,0,110000,'2016-11-29'),(30,29,'2016-12-13','0','0',1,11818,'contado',3,130000,0,0,1,0,0,130000,'2016-12-13'),(31,30,'2016-12-13','0','0',2,8182,'contado',3,90000,0,0,1,0,0,90000,'2016-12-13'),(32,31,'2016-12-14','0','0',2,8182,'contado',1,90000,0,0,1,0,0,90000,'2016-12-14'),(33,32,'2016-12-14','0','0',2,55455,'Credito',1,610000,0,0,2,510000,255000,100000,'2016-12-18'),(34,33,'2016-12-14','pagado','si',12,190909,'Credito',3,2100000,0,0,4,0,400000,500000,'2016-12-14'),(35,34,'2016-12-14','0','0',1,190909,'Credito',3,2100000,0,0,3,1500000,500000,600000,'2016-12-14'),(36,35,'2016-12-14','0','0',1,190909,'Credito',3,2100000,0,0,3,1500000,500000,600000,'2016-12-14'),(37,36,'2016-12-15','0','0',12,18182,'Credito',1,200000,0,0,4,160000,40000,40000,'2016-12-15');
/*!40000 ALTER TABLE `facturaventa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gastos`
--

DROP TABLE IF EXISTS `gastos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gastos` (
  `idgastos` int(11) NOT NULL,
  `monto` int(11) DEFAULT NULL,
  `concepto` varchar(45) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `idempleado` int(11) NOT NULL,
  PRIMARY KEY (`idgastos`),
  KEY `fk_gastos_empleado1_idx` (`idempleado`),
  CONSTRAINT `fk_gastos_empleado1` FOREIGN KEY (`idempleado`) REFERENCES `empleado` (`idempleado`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gastos`
--

LOCK TABLES `gastos` WRITE;
/*!40000 ALTER TABLE `gastos` DISABLE KEYS */;
INSERT INTO `gastos` VALUES (1,15000,'yerba','2016-11-12',3),(2,25000,'resma oficio','2016-11-12',3),(3,15000,'yerba','2016-11-19',3),(4,50000,'se compro escoba','2016-11-19',1),(5,100000,'escoba','2016-12-14',1),(6,8000,'yerba','2016-12-15',3);
/*!40000 ALTER TABLE `gastos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `marca`
--

DROP TABLE IF EXISTS `marca`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `marca` (
  `idmarca` int(11) NOT NULL,
  `descripcion` char(50) DEFAULT NULL,
  PRIMARY KEY (`idmarca`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `marca`
--

LOCK TABLES `marca` WRITE;
/*!40000 ALTER TABLE `marca` DISABLE KEYS */;
INSERT INTO `marca` VALUES (1,'sony'),(2,'philips'),(3,'panasonic'),(4,'powerpack'),(5,'samsung'),(6,'motorolla'),(7,'Ecopower'),(8,'Tp-Link'),(9,'Hp'),(10,'toshiba'),(11,'satellite'),(12,'SandDisk'),(13,'Kingston'),(14,'Sate'),(15,'genesis'),(16,'verbatin');
/*!40000 ALTER TABLE `marca` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pagoscuotas`
--

DROP TABLE IF EXISTS `pagoscuotas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pagoscuotas` (
  `idpagocuota` int(11) NOT NULL,
  `nrocuota` int(11) DEFAULT NULL,
  `montocuota` int(11) DEFAULT NULL,
  `fechavence` date DEFAULT NULL,
  `fechapago` date DEFAULT NULL,
  `saldo` int(11) DEFAULT NULL,
  `montoabonado` int(11) NOT NULL DEFAULT '0',
  `cantcuotapagado` int(11) DEFAULT NULL,
  `idfacturaventa` int(11) NOT NULL,
  PRIMARY KEY (`idpagocuota`),
  KEY `fk_PagosCuotas_FacturaVenta1` (`idfacturaventa`),
  CONSTRAINT `fk_PagosCuotas_FacturaVenta1` FOREIGN KEY (`idfacturaventa`) REFERENCES `facturaventa` (`idfacturaventa`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pagoscuotas`
--

LOCK TABLES `pagoscuotas` WRITE;
/*!40000 ALTER TABLE `pagoscuotas` DISABLE KEYS */;
INSERT INTO `pagoscuotas` VALUES (1,1,13000,'2016-11-12','2016-11-09',0,26000,0,4),(2,3,13000,'2016-11-12','2016-11-12',39000,13000,2,4),(3,1,940000,'2016-12-12','2016-11-12',94000,94000,0,5),(4,2,940000,'2017-01-12','2016-12-12',188000,94000,0,5),(5,3,940000,'2016-11-12','2017-01-12',3760000,3572000,0,5),(6,1,87500,'2016-12-12','2016-11-09',87500,87500,0,2),(7,2,87500,'2016-11-12','2016-12-12',175000,87500,1,2),(8,3,13000,'2016-11-13','2016-11-12',52000,13000,3,4),(9,1,400000,'2017-12-14','2016-12-14',400000,400000,0,34),(10,2,400000,'2017-12-14','2017-12-14',800000,400000,1,34),(11,3,400000,'2017-02-10','2017-12-14',1200000,400000,2,34),(12,4,400000,'2016-12-14','2017-02-10',1600000,400000,3,34),(13,1,255000,'2016-12-18','2016-12-14',255000,255000,0,33);
/*!40000 ALTER TABLE `pagoscuotas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productos`
--

DROP TABLE IF EXISTS `productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `productos` (
  `idproducto` int(11) NOT NULL,
  `preciodecompra` int(11) DEFAULT NULL,
  `preciodeventa` int(11) DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `descripcion` varchar(45) DEFAULT NULL,
  `gananciaproducto` smallint(6) DEFAULT NULL,
  `idmarca` int(11) NOT NULL,
  `idcategoria` int(11) NOT NULL,
  `idproveedor` int(11) NOT NULL,
  `iva` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`idproducto`),
  KEY `fk_productos_marca` (`idmarca`),
  KEY `fk_productos_categoria1` (`idcategoria`),
  KEY `fk_productos_proveedor1` (`idproveedor`),
  CONSTRAINT `fk_productos_categoria1` FOREIGN KEY (`idcategoria`) REFERENCES `categoria` (`idcategoria`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_productos_marca` FOREIGN KEY (`idmarca`) REFERENCES `marca` (`idmarca`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_productos_proveedor1` FOREIGN KEY (`idproveedor`) REFERENCES `proveedor` (`idproveedor`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos`
--

LOCK TABLES `productos` WRITE;
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
INSERT INTO `productos` VALUES (1,20000,36000,2,'pendrive de 8 gb ',80,12,11,4,10),(2,30000,54000,7,'pendrive de 16 gb ',80,12,11,4,10),(3,35000,66500,1,'pendrive de 32 gb',90,12,11,4,10),(4,60000,120000,3,'cartucho 60 hp negro',100,9,9,2,10),(5,65000,130000,3,'cartucho color 60',100,9,9,2,10),(6,45000,90000,2,'cartucho 662 negro',100,9,9,2,10),(7,45000,90000,2,'cartucho 662 color ',100,9,9,2,10),(8,55000,110000,2,'cartucho 61 negro',100,9,9,2,10),(9,55000,110000,4,'cartucho 61 color',100,9,9,2,10),(10,75000,150000,4,'router simple 150 mbps',100,8,6,3,10),(11,100,200,2,'router doble antena 300 mbps',100,8,6,3,10),(12,1900000,2660000,1,'tv 42 \" smart ',40,10,1,6,10),(13,25000,50000,3,'teclado usb para pc',100,16,13,2,10),(14,2000000,2600000,2,'notebook hp 15\", core i5, hdd 1tb, 6 gb RAM',30,9,14,6,10),(15,1500000,2100000,2,'notebook hp de 15 \" 4 gb de ram 1 tb de hdd',40,9,14,4,10),(16,2000000,2600000,5,'televisor led 42 smart',30,1,1,2,10);
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proveedor`
--

DROP TABLE IF EXISTS `proveedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proveedor` (
  `idproveedor` int(11) NOT NULL,
  `nombre` char(30) DEFAULT NULL,
  `telefono` char(30) DEFAULT NULL,
  `direccion` char(40) DEFAULT NULL,
  `RUC` char(45) DEFAULT NULL,
  PRIMARY KEY (`idproveedor`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proveedor`
--

LOCK TABLES `proveedor` WRITE;
/*!40000 ALTER TABLE `proveedor` DISABLE KEYS */;
INSERT INTO `proveedor` VALUES (2,'micro expres','0541525547','greolandia','5214-9'),(3,'infocov','065541','cerro cora','665-9'),(4,'TCHELOCO','061523224','cde','33626-3'),(5,'denpa','061224585','cde',''),(6,'navenet','064521147','ciudad del este','999292'),(7,'ruler informatica','0521201203','coronel oviedo','4022239-0');
/*!40000 ALTER TABLE `proveedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recibodecompra`
--

DROP TABLE IF EXISTS `recibodecompra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recibodecompra` (
  `idrecibocompra` int(11) NOT NULL,
  `numero` int(11) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `monto` int(11) DEFAULT NULL,
  `idfacturacompra` int(11) NOT NULL,
  PRIMARY KEY (`idrecibocompra`),
  KEY `fk_recibodecompra_FacturaCompra1` (`idfacturacompra`),
  CONSTRAINT `fk_recibodecompra_FacturaCompra1` FOREIGN KEY (`idfacturacompra`) REFERENCES `facturacompra` (`idfacturacompra`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recibodecompra`
--

LOCK TABLES `recibodecompra` WRITE;
/*!40000 ALTER TABLE `recibodecompra` DISABLE KEYS */;
/*!40000 ALTER TABLE `recibodecompra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reciboventa`
--

DROP TABLE IF EXISTS `reciboventa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reciboventa` (
  `idreciboventa` int(11) NOT NULL,
  `numerofactura` int(11) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `monto` int(11) DEFAULT NULL,
  `idpagocuota` int(11) NOT NULL,
  PRIMARY KEY (`idreciboventa`),
  KEY `fk_ReciboVenta_PagosCuotas1` (`idpagocuota`),
  CONSTRAINT `fk_ReciboVenta_PagosCuotas1` FOREIGN KEY (`idpagocuota`) REFERENCES `pagoscuotas` (`idpagocuota`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reciboventa`
--

LOCK TABLES `reciboventa` WRITE;
/*!40000 ALTER TABLE `reciboventa` DISABLE KEYS */;
INSERT INTO `reciboventa` VALUES (1,4,'2016-11-12',26000,1),(2,4,'2016-11-12',13000,2),(3,5,'2016-12-12',94000,3),(4,5,'2017-01-12',94000,4),(5,5,'2016-11-12',3572000,5),(6,2,'2016-12-12',87500,6),(7,2,'2016-11-12',87500,7),(8,4,'2016-11-13',13000,8),(9,34,'2017-12-14',400000,9),(10,34,'2017-12-14',400000,10),(11,34,'2017-02-10',400000,11),(12,34,'2016-12-14',400000,12),(13,33,'2016-12-18',255000,13);
/*!40000 ALTER TABLE `reciboventa` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-12-20 12:07:15
