package com.cine.Back_Cine.repositories;

import com.cine.Back_Cine.entities.SalaCine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SalaCineRepository extends JpaRepository<SalaCine, Integer> {
    
    // Buscar solo salas activas
    Optional<SalaCine> findByIdSalaAndActivoTrue(Integer id);
    
    // Listar solo salas activas
    List<SalaCine> findByActivoTrue();
    
    // Buscar por nombre (ignorando mayúsculas)
    Optional<SalaCine> findByNombreIgnoreCaseAndActivoTrue(String nombre);
    
    // Usar función de BD para contar películas por sala
    @Query(value = "SELECT contar_peliculas_por_sala(:idSala)", nativeQuery = true)
    Integer contarPeliculasPorSala(@Param("idSala") Integer idSala);
}