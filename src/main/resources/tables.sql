CREATE DATABASE IF NOT EXISTS cronometro_db DEFAULT CHARACTER SET latin1;

-- Usar la base de datos 'cronometro_db'
USE cronometro_db;

-- Tabla 'usuario'
CREATE TABLE IF NOT EXISTS usuario (
  id INT AUTO_INCREMENT PRIMARY KEY,
  usuario VARCHAR(50) NOT NULL,
  contrasena VARCHAR(255) NOT NULL,
  correo VARCHAR(255) DEFAULT NULL
);

-- Tabla 'sesion'
CREATE TABLE IF NOT EXISTS sesion (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL,
  usuario_id INT NOT NULL,
  FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

-- Tabla 'tiempos'
CREATE TABLE IF NOT EXISTS tiempos (
  id INT AUTO_INCREMENT PRIMARY KEY,
  tiempo VARCHAR(50) NOT NULL,
  scramble VARCHAR(255) NOT NULL,
  fecha DATE NOT NULL,
  mas_dos TINYINT(1) DEFAULT '0',
  dnf TINYINT(1) DEFAULT '0',
  usuario_id INT NOT NULL,
  sesion_id INT NOT NULL,
  FOREIGN KEY (usuario_id) REFERENCES usuario(id),
  FOREIGN KEY (sesion_id) REFERENCES sesion(id)
);

-- Tabla 'token'
CREATE TABLE IF NOT EXISTS token (
  token_id INT AUTO_INCREMENT PRIMARY KEY,
  uuid VARCHAR(36) NOT NULL,
  usuario_id INT NOT NULL,
  fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  caducado TINYINT DEFAULT 0,
  FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);