package com.cine.Back_Cine.repositories;

import com.cine.Back_Cine.entities.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Integer> {
    
    // Buscar solo películas activas
    Optional<Pelicula> findByIdPeliculaAndActivoTrue(Integer id);
    
    // Listar solo películas activas
    List<Pelicula> findByActivoTrue();
    
    // Buscar por nombre (ignorando mayúsculas)
    List<Pelicula> findByNombreContainingIgnoreCaseAndActivoTrue(String nombre);
    
    // REQUERIDO: Buscar película por nombre y sala
    @Query("SELECT p FROM Pelicula p WHERE p.nombre ILIKE %:nombre% AND p.activo = true AND EXISTS " +
           "(SELECT ps FROM PeliculaSalaCine ps WHERE ps.pelicula.idPelicula = p.idPelicula " +
           "AND ps.salaCine.idSala = :idSala AND ps.activo = true)")
    List<Pelicula> findPeliculaByNombreAndSala(@Param("nombre") String nombre, @Param("idSala") Integer idSala);
    
    // Consulta nativa para usar la función de BD (por fecha)
    @Query(value = "SELECT * FROM obtener_peliculas_por_fecha(:fecha)", nativeQuery = true)
    List<Object[]> findPeliculasByFechaPublicacion(@Param("fecha") String fecha);
}