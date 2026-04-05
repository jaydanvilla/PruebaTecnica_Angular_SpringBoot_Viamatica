package com.cine.Back_Cine.services;

import com.cine.Back_Cine.entities.Pelicula;
import com.cine.Back_Cine.repositories.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PeliculaService {
    
    @Autowired
    private PeliculaRepository peliculaRepository;
    
    // CREATE - Crear nueva película
    @Transactional
    public Pelicula crearPelicula(Pelicula pelicula) {
        pelicula.setActivo(true);
        return peliculaRepository.save(pelicula);
    }
    
    // READ - Listar todas las películas activas
    public List<Pelicula> listarPeliculas() {
        return peliculaRepository.findByActivoTrue();
    }
    
    // READ - Obtener película por ID
    public Optional<Pelicula> obtenerPeliculaPorId(Integer id) {
        return peliculaRepository.findByIdPeliculaAndActivoTrue(id);
    }
    
    // UPDATE - Actualizar película
    @Transactional
    public Optional<Pelicula> actualizarPelicula(Integer id, Pelicula peliculaActualizada) {
        return peliculaRepository.findByIdPeliculaAndActivoTrue(id).map(pelicula -> {
            pelicula.setNombre(peliculaActualizada.getNombre());
            pelicula.setDuracion(peliculaActualizada.getDuracion());
            return peliculaRepository.save(pelicula);
        });
    }
    
    // DELETE - Eliminación lógica
    @Transactional
    public boolean eliminarPeliculaLogico(Integer id) {
        return peliculaRepository.findByIdPeliculaAndActivoTrue(id).map(pelicula -> {
            pelicula.setActivo(false);
            peliculaRepository.save(pelicula);
            return true;
        }).orElse(false);
    }
}