package com.cine.Back_Cine.repositories;

import com.cine.Back_Cine.entities.PeliculaSalaCine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PeliculaSalaCineRepository extends JpaRepository<PeliculaSalaCine, Integer> {
    
    // Buscar por sala (solo activas)
    List<PeliculaSalaCine> findBySalaCine_IdSalaAndActivoTrue(Integer idSala);
    
    // Buscar por película (solo activas)
    List<PeliculaSalaCine> findByPelicula_IdPeliculaAndActivoTrue(Integer idPelicula);
    
    // Listar solo asignaciones activas
    List<PeliculaSalaCine> findByActivoTrue();
}