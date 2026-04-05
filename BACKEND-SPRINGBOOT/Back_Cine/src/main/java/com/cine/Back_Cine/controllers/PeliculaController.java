package com.cine.Back_Cine.controllers;

import com.cine.Back_Cine.entities.Pelicula;
import com.cine.Back_Cine.services.PeliculaService;
import com.cine.Back_Cine.services.PeliculaSalaCineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/peliculas")
public class PeliculaController {
    
    @Autowired
    private PeliculaService peliculaService;
    
    @Autowired
    private PeliculaSalaCineService peliculaSalaCineService;
    
    // CREATE - Crear nueva película
    @PostMapping
    public ResponseEntity<Pelicula> crearPelicula(@RequestBody Pelicula pelicula) {
        Pelicula nuevaPelicula = peliculaService.crearPelicula(pelicula);
        return new ResponseEntity<>(nuevaPelicula, HttpStatus.CREATED);
    }
    
    // READ - Listar todas las películas activas
    @GetMapping
    public ResponseEntity<List<Pelicula>> listarPeliculas() {
        return ResponseEntity.ok(peliculaService.listarPeliculas());
    }
    
    // READ - Obtener película por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pelicula> obtenerPeliculaPorId(@PathVariable Integer id) {
        Optional<Pelicula> pelicula = peliculaService.obtenerPeliculaPorId(id);
        return pelicula.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // UPDATE - Actualizar película
    @PutMapping("/{id}")
    public ResponseEntity<Pelicula> actualizarPelicula(@PathVariable Integer id, @RequestBody Pelicula pelicula) {
        Optional<Pelicula> peliculaActualizada = peliculaService.actualizarPelicula(id, pelicula);
        return peliculaActualizada.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // DELETE - Eliminación lógica
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPelicula(@PathVariable Integer id) {
        if (peliculaService.eliminarPeliculaLogico(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    // REQUERIDO: Buscar película por nombre y ID de sala
    @GetMapping("/buscar")
    public ResponseEntity<List<Pelicula>> buscarPorNombreYSala(
            @RequestParam String nombre,
            @RequestParam Integer idSala) {
        List<Pelicula> resultados = peliculaSalaCineService.buscarPeliculaPorNombreYSala(nombre, idSala);
        return ResponseEntity.ok(resultados);
    }
    
    // REQUERIDO: Películas por fecha de publicación (con cantidad)
    @GetMapping("/fecha")
    public ResponseEntity<?> obtenerPorFecha(@RequestParam String fecha) {
        List<Object[]> peliculas = peliculaSalaCineService.obtenerPeliculasPorFecha(fecha);
        long cantidad = peliculaSalaCineService.contarPeliculasPorFecha(fecha);
        
        return ResponseEntity.ok(new java.util.HashMap<String, Object>() {{
            put("cantidad", cantidad);
            put("peliculas", peliculas);
        }});
    }
}