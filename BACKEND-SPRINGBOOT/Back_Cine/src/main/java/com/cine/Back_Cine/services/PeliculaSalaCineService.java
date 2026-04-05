package com.cine.Back_Cine.services;

import com.cine.Back_Cine.entities.Pelicula;
import com.cine.Back_Cine.entities.PeliculaSalaCine;
import com.cine.Back_Cine.repositories.PeliculaRepository;
import com.cine.Back_Cine.repositories.PeliculaSalaCineRepository;
import com.cine.Back_Cine.repositories.SalaCineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PeliculaSalaCineService {
    
    @Autowired
    private PeliculaSalaCineRepository peliculaSalaCineRepository;
    
    @Autowired
    private PeliculaRepository peliculaRepository;
    
    @Autowired
    private SalaCineRepository salaCineRepository;
    
    // REQUERIDO: Buscar película por nombre y ID de sala
    public List<Pelicula> buscarPeliculaPorNombreYSala(String nombre, Integer idSala) {
        return peliculaRepository.findPeliculaByNombreAndSala(nombre, idSala);
    }
    
    // REQUERIDO: Obtener películas por fecha de publicación
    public List<Object[]> obtenerPeliculasPorFecha(String fecha) {
        return peliculaRepository.findPeliculasByFechaPublicacion(fecha);
    }
    
    // REQUERIDO: Contar películas por fecha
    public long contarPeliculasPorFecha(String fecha) {
        List<Object[]> resultados = peliculaRepository.findPeliculasByFechaPublicacion(fecha);
        return resultados != null ? resultados.size() : 0;
    }
    
    // REQUERIDO: Obtener películas asignadas a una sala (solo activas)
    public List<PeliculaSalaCine> obtenerPeliculasPorSala(Integer idSala) {
        return peliculaSalaCineRepository.findBySalaCine_IdSalaAndActivoTrue(idSala);
    }
}