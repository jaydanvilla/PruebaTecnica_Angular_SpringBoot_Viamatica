-- ============================================
-- SISTEMA DE GESTIÓN DE CINE - BASE DE DATOS
-- ============================================
-- Autor: [Jay Villarreal]
-- Fecha: Abril 2026
-- Base de datos: PostgreSQL
-- ============================================

-- ============================================
-- 1. ELIMINAR Y CREAR BASE DE DATOS
-- ============================================

-- Ver todas las conexiones activas a la base de datos
SELECT pid, usename, application_name, client_addr, state 
FROM pg_stat_activity 
WHERE datname = 'cine_db';

-- Terminar TODAS las conexiones existentes
SELECT pg_terminate_backend(pid)
FROM pg_stat_activity 
WHERE datname = 'cine_db' 
  AND pid <> pg_backend_pid();

-- Eliminar base de datos si existe
DROP DATABASE IF EXISTS cine_db;

-- Crear nueva base de datos
CREATE DATABASE cine_db;

-- Conectar a la nueva base de datos (ejecutar por separado)
-- \c cine_db;

-- ============================================
-- 2. CREAR TABLAS
-- ============================================

-- Tabla: pelicula
-- Almacena información de las películas
CREATE TABLE pelicula (
    id_pelicula SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    duracion INT NOT NULL,
    activo BOOLEAN DEFAULT TRUE
);

-- Tabla: sala_cine
-- Almacena información de las salas de cine
CREATE TABLE sala_cine (
    id_sala SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    estado VARCHAR(50) NOT NULL,
    activo BOOLEAN DEFAULT TRUE
);

-- Tabla intermedia: pelicula_sala_cine
-- Relaciona películas con salas (asignaciones)
CREATE TABLE pelicula_sala_cine (
    id_pelicula_sala SERIAL PRIMARY KEY,
    id_pelicula INT NOT NULL,
    id_sala_cine INT NOT NULL,
    fecha_publicacion DATE NOT NULL,
    fecha_fin DATE,
    activo BOOLEAN DEFAULT TRUE,
    
    -- Llaves foráneas
    CONSTRAINT fk_pelicula 
        FOREIGN KEY (id_pelicula) 
        REFERENCES pelicula(id_pelicula)
        ON DELETE CASCADE,
    
    CONSTRAINT fk_sala_cine 
        FOREIGN KEY (id_sala_cine) 
        REFERENCES sala_cine(id_sala)
        ON DELETE CASCADE
);

-- ============================================
-- 3. FUNCIONES DE BASE DE DATOS
-- ============================================

-- Función 1: Contar películas asignadas a una sala específica
-- Uso: SELECT contar_peliculas_por_sala(1);
CREATE OR REPLACE FUNCTION contar_peliculas_por_sala(p_id_sala INT)
RETURNS INT AS $$
DECLARE
    total INT;
BEGIN
    SELECT COUNT(*) INTO total
    FROM pelicula_sala_cine
    WHERE id_sala_cine = p_id_sala 
      AND activo = TRUE;
    
    RETURN total;
END;
$$ LANGUAGE plpgsql;

-- Función 2: Obtener películas por fecha de publicación
-- Uso: SELECT * FROM obtener_peliculas_por_fecha('2024-01-15');
CREATE OR REPLACE FUNCTION obtener_peliculas_por_fecha(p_fecha DATE)
RETURNS TABLE(
    id_pelicula INT,
    nombre VARCHAR,
    duracion INT,
    fecha_publicacion DATE
) AS $$
BEGIN
    RETURN QUERY
    SELECT DISTINCT p.id_pelicula, p.nombre, p.duracion, ps.fecha_publicacion
    FROM pelicula p
    JOIN pelicula_sala_cine ps ON p.id_pelicula = ps.id_pelicula
    WHERE ps.fecha_publicacion = p_fecha
      AND p.activo = TRUE
      AND ps.activo = TRUE;
END;
$$ LANGUAGE plpgsql;

-- ============================================
-- 4. DATOS DE EJEMPLO (INSERT)
-- ============================================

-- Insertar películas
INSERT INTO pelicula (nombre, duracion, activo) VALUES 
('Inception', 148, TRUE),
('The Matrix', 136, TRUE),
('Interstellar', 169, TRUE),
('Parasite', 132, TRUE),
('El Laberinto del Fauno', 118, TRUE),
('NombrePelícula', 120, TRUE),
('Avatar', 162, TRUE),
('Avatar 2', 190, TRUE),
('Test Final', 100, TRUE);

-- Insertar salas de cine
INSERT INTO sala_cine (nombre, estado, activo) VALUES 
('Sala 1 - Premium', 'activa', TRUE),
('Sala 2 - 3D', 'activa', TRUE),
('Sala 3 - IMAX', 'activa', TRUE),
('Sala 4 - Tradicional', 'mantenimiento', TRUE),
('Sala 5 - VIP', 'activa', TRUE);

-- Insertar relaciones (película-sala)
INSERT INTO pelicula_sala_cine (id_pelicula, id_sala_cine, fecha_publicacion, fecha_fin, activo) VALUES 
(1, 1, '2024-01-15', '2024-02-15', TRUE),
(1, 3, '2024-01-20', '2024-02-20', TRUE),
(2, 2, '2024-01-10', '2024-02-10', TRUE),
(3, 3, '2024-02-01', '2024-03-01', TRUE),
(4, 4, '2024-01-25', '2024-02-25', TRUE),
(5, 5, '2024-01-18', '2024-02-18', TRUE),
(6, 1, '2024-07-21', NULL, TRUE),
(7, 2, '2024-07-21', NULL, TRUE);

-- ============================================
-- 5. CONSULTAS PRINCIPALES (CRUD)
-- ============================================

-- READ: Listar todas las películas activas
SELECT * FROM pelicula WHERE activo = TRUE;

-- READ: Obtener película por ID
SELECT * FROM pelicula WHERE id_pelicula = 1 AND activo = TRUE;

-- CREATE: Insertar nueva película
INSERT INTO pelicula (nombre, duracion, activo) 
VALUES ('Nueva Película', 120, TRUE);

-- UPDATE: Actualizar película
UPDATE pelicula 
SET nombre = 'Nombre Actualizado', duracion = 150 
WHERE id_pelicula = 1 AND activo = TRUE;

-- DELETE: Eliminación lógica (solo desactiva)
UPDATE pelicula SET activo = FALSE WHERE id_pelicula = 1;

-- ============================================
-- 6. CONSULTAS REQUERIDAS (PUNTO 2)
-- ============================================

-- Consulta 1: Buscar película por nombre e ID de sala
-- Reemplazar 'Inception' por el nombre y 1 por el ID de sala
SELECT p.id_pelicula, p.nombre, p.duracion, s.id_sala, s.nombre AS sala_nombre
FROM pelicula p
JOIN pelicula_sala_cine ps ON p.id_pelicula = ps.id_pelicula
JOIN sala_cine s ON ps.id_sala_cine = s.id_sala
WHERE p.nombre ILIKE '%Inception%'
  AND s.id_sala = 1
  AND p.activo = TRUE
  AND ps.activo = TRUE;

-- Consulta 2: Películas por fecha con cantidad
-- Reemplazar '2024-01-15' por la fecha deseada
WITH peliculas_fecha AS (
    SELECT DISTINCT p.id_pelicula, p.nombre, p.duracion, ps.fecha_publicacion
    FROM pelicula p
    JOIN pelicula_sala_cine ps ON p.id_pelicula = ps.id_pelicula
    WHERE ps.fecha_publicacion = '2024-01-15'
      AND p.activo = TRUE
      AND ps.activo = TRUE
)
SELECT *, (SELECT COUNT(*) FROM peliculas_fecha) AS total_peliculas
FROM peliculas_fecha;

-- Consulta 3: Buscar sala por nombre y mostrar películas asignadas
-- Reemplazar 'Premium' por el nombre de la sala
SELECT 
    s.id_sala,
    s.nombre AS sala_nombre,
    s.estado,
    COALESCE(
        string_agg(DISTINCT p.nombre, ', '),
        'No tiene películas asignadas'
    ) AS peliculas_asignadas,
    COUNT(DISTINCT p.id_pelicula) AS total_peliculas
FROM sala_cine s
LEFT JOIN pelicula_sala_cine ps ON s.id_sala = ps.id_sala_cine AND ps.activo = TRUE
LEFT JOIN pelicula p ON ps.id_pelicula = p.id_pelicula AND p.activo = TRUE
WHERE s.nombre ILIKE '%Premium%'
  AND s.activo = TRUE
GROUP BY s.id_sala, s.nombre, s.estado;

-- ============================================
-- 7. CONSULTAS DE VERIFICACIÓN
-- ============================================

-- Ver todas las tablas creadas
SELECT table_name 
FROM information_schema.tables 
WHERE table_schema = 'public'
ORDER BY table_name;

-- Ver todas las películas (activas e inactivas)
SELECT * FROM pelicula;

-- Ver todas las salas (activas e inactivas)
SELECT * FROM sala_cine;

-- Ver todas las relaciones (película-sala) activas
SELECT 
    p.nombre AS pelicula,
    p.duracion,
    s.nombre AS sala,
    s.estado,
    ps.fecha_publicacion,
    ps.fecha_fin
FROM pelicula_sala_cine ps
JOIN pelicula p ON ps.id_pelicula = p.id_pelicula AND p.activo = TRUE
JOIN sala_cine s ON ps.id_sala_cine = s.id_sala AND s.activo = TRUE
WHERE ps.activo = TRUE
ORDER BY ps.fecha_publicacion;

-- ============================================
-- 8. PROBAR FUNCIONES
-- ============================================

-- Probar función contar_peliculas_por_sala
-- Cuenta cuántas películas tiene la sala con ID 3
SELECT contar_peliculas_por_sala(3) AS total_peliculas_en_sala_IMAX;

-- Probar función obtener_peliculas_por_fecha
-- Muestra películas publicadas en una fecha específica
SELECT * FROM obtener_peliculas_por_fecha('2024-07-21');

-- ============================================
-- 9. ACTUALIZAR REGISTROS EXISTENTES
-- ============================================

-- Asegurar que todos los registros tengan activo = TRUE
UPDATE pelicula SET activo = TRUE WHERE activo IS NULL;
UPDATE sala_cine SET activo = TRUE WHERE activo IS NULL;
UPDATE pelicula_sala_cine SET activo = TRUE WHERE activo IS NULL;

-- ============================================
-- FIN DEL SCRIPT
-- ============================================