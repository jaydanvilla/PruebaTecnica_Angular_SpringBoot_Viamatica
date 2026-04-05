package com.cine.Back_Cine.controllers;

import com.cine.Back_Cine.entities.SalaCine;
import com.cine.Back_Cine.services.SalacineService;
import com.cine.Back_Cine.services.PeliculaSalaCineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/salas")
public class SalaCineController {
    
    @Autowired
    private SalacineService salacineService;
    
    @Autowired
    private PeliculaSalaCineService peliculaSalaCineService;
    
    // CREATE - Crear nueva sala
    @PostMapping
    public ResponseEntity<SalaCine> crearSala(@RequestBody SalaCine sala) {
        SalaCine nuevaSala = salacineService.crearSala(sala);
        return new ResponseEntity<>(nuevaSala, HttpStatus.CREATED);
    }
    
    // READ - Listar todas las salas activas
    @GetMapping
    public ResponseEntity<List<SalaCine>> listarSalas() {
        return ResponseEntity.ok(salacineService.listarSalas());
    }
    
    // READ - Obtener sala por ID
    @GetMapping("/{id}")
    public ResponseEntity<SalaCine> obtenerSalaPorId(@PathVariable Integer id) {
        Optional<SalaCine> sala = salacineService.obtenerSalaPorId(id);
        return sala.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // UPDATE - Actualizar sala
    @PutMapping("/{id}")
    public ResponseEntity<SalaCine> actualizarSala(@PathVariable Integer id, @RequestBody SalaCine sala) {
        Optional<SalaCine> salaActualizada = salacineService.actualizarSala(id, sala);
        return salaActualizada.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // DELETE - Eliminar sala
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSala(@PathVariable Integer id) {
        if (salacineService.eliminarSala(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    // REQUERIDO: Buscar por nombre de sala y presentar películas asignadas
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorNombreSala(@RequestParam String nombre) {
        Optional<SalaCine> salaOpt = salacineService.buscarPorNombre(nombre);
        
        if (salaOpt.isEmpty()) {
            return ResponseEntity.ok(new java.util.HashMap<String, String>() {{
                put("mensaje", "No se encontró la sala: " + nombre);
            }});
        }
        
        SalaCine sala = salaOpt.get();
        var peliculas = peliculaSalaCineService.obtenerPeliculasPorSala(sala.getIdSala());
        
        java.util.Map<String, Object> resultado = new java.util.HashMap<>();
        resultado.put("sala", sala);
        
        if (peliculas.isEmpty()) {
            resultado.put("mensaje", "La sala '" + sala.getNombre() + "' no tiene películas asignadas");
            resultado.put("peliculas", List.of());
        } else {
            resultado.put("mensaje", "La sala '" + sala.getNombre() + "' tiene " + peliculas.size() + " película(s) asignada(s)");
            resultado.put("peliculas", peliculas);
        }
        
        return ResponseEntity.ok(resultado);
    }
}