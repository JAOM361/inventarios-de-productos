CREATE DATABASE IF NOT EXISTS inventario_db;
USE inventario_db;

-- ============================================================
-- TABLA: productos
-- ============================================================
CREATE TABLE productos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(500),
    stock INT DEFAULT 0,
    precio DECIMAL(10,2) DEFAULT 0.00
);

-- ============================================================
-- TABLA: usuarios
-- ============================================================
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(150) UNIQUE NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    rol VARCHAR(50) NOT NULL
);

-- ============================================================
-- TABLA: movimientos
-- ============================================================
CREATE TABLE movimientos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo ENUM('ENTRADA','SALIDA') NOT NULL,
    cantidad INT NOT NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    producto_id BIGINT NOT NULL,
    CONSTRAINT fk_mov_producto FOREIGN KEY (producto_id)
        REFERENCES productos(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
ALTER TABLE movimientos 
ADD COLUMN id_producto INT,
ADD CONSTRAINT fk_movimiento_producto 
FOREIGN KEY (id_producto) REFERENCES productos(id);
