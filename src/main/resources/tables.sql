SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `cronometro_db` DEFAULT CHARACTER SET latin1 ;
USE `cronometro_db` ;

-- -----------------------------------------------------
-- Table `cronometro_db`.`usuario`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cronometro_db`.`usuario` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `usuario` VARCHAR(50) NOT NULL ,
  `contrasena` VARCHAR(255) NOT NULL ,
  `correo` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `cronometro_db`.`sesion`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cronometro_db`.`sesion` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `nombre` VARCHAR(50) NOT NULL ,
  `usuario_id` INT(11) NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `sesion_ibfk_1`
    FOREIGN KEY (`usuario_id` )
    REFERENCES `cronometro_db`.`usuario` (`id` ))
ENGINE = InnoDB
AUTO_INCREMENT = 40
DEFAULT CHARACTER SET = latin1;

CREATE INDEX `usuario_id` ON `cronometro_db`.`sesion` (`usuario_id` ASC) ;


-- -----------------------------------------------------
-- Table `cronometro_db`.`tiempos`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cronometro_db`.`tiempos` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `tiempo` VARCHAR(50) NOT NULL ,
  `scramble` VARCHAR(255) NOT NULL ,
  `fecha` DATE NOT NULL ,
  `mas_dos` TINYINT(1) NULL DEFAULT '0' ,
  `dnf` TINYINT(1) NULL DEFAULT '0' ,
  `usuario_id` INT(11) NOT NULL ,
  `sesion_id` INT(11) NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `tiempos_ibfk_1`
    FOREIGN KEY (`usuario_id` )
    REFERENCES `cronometro_db`.`usuario` (`id` ),
  CONSTRAINT `tiempos_ibfk_2`
    FOREIGN KEY (`sesion_id` )
    REFERENCES `cronometro_db`.`sesion` (`id` ))
ENGINE = InnoDB
AUTO_INCREMENT = 223
DEFAULT CHARACTER SET = latin1;

CREATE INDEX `usuario_id` ON `cronometro_db`.`tiempos` (`usuario_id` ASC) ;

CREATE INDEX `sesion_id` ON `cronometro_db`.`tiempos` (`sesion_id` ASC) ;

USE `cronometro_db` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
