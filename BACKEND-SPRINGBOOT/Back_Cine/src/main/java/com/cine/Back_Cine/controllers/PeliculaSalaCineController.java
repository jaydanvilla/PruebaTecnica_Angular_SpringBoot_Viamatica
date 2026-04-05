package com.cine.Back_Cine.controllers;

import com.cine.Back_Cine.entities.PeliculaSalaCine;
import com.cine.Back_Cine.services.PeliculaSalaCineService;
import com.cine.Back_Cine.repositories.PeliculaSalaCineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/pelicula-sala-cine")
public class PeliculaSalaCineController {
    
    @Autowired
    private PeliculaSalaCineRepository peliculaSalaCineRepository;
    
    @Autowired
    private PeliculaSalaCineService peliculaSalaCineService;
    
    // READ - Listar solo asignaciones activas
    @GetMapping
    public ResponseEntity<List<PeliculaSalaCine>> listarAsignaciones() {
        List<PeliculaSalaCine> asignaciones = peliculaSalaCineRepository.findByActivoTrue();
        return ResponseEntity.ok(asignaciones);
    }
    
    // CREATE - Crear nueva asignación
    @PostMapping
    public ResponseEntity<PeliculaSalaCine> crearAsignacion(@RequestBody PeliculaSalaCine asignacion) {
        asignacion.setActivo(true);
        PeliculaSalaCine nuevaAsignacion = peliculaSalaCineRepository.save(asignacion);
        return new ResponseEntity<>(nuevaAsignacion, HttpStatus.CREATED);
    }
    
    // DELETE - Eliminar asignación (lógico)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAsignacion(@PathVariable Integer id) {
        java.util.Optional<PeliculaSalaCine> asignacion = peliculaSalaCineRepository.findById(id);
        if (asignacion.isPresent()) {
            PeliculaSalaCine existente = asignacion.get();
            existente.setActivo(false);
            peliculaSalaCineRepository.save(existente);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    // READ - Obtener asignaciones por sala (solo activas)
    @GetMapping("/sala/{idSala}")
    public ResponseEntity<List<PeliculaSalaCine>> obtenerPorSala(@PathVariable Integer idSala) {
        List<PeliculaSalaCine> asignaciones = peliculaSalaCineService.obtenerPeliculasPorSala(idSala);
        return ResponseEntity.ok(asignaciones);
    }
}