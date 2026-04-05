package com.cine.Back_Cine.services;

import com.cine.Back_Cine.entities.SalaCine;
import com.cine.Back_Cine.repositories.SalaCineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SalacineService {
    
    @Autowired
    private SalaCineRepository salaCineRepository;
    
    // READ - Listar todas las salas activas
    public List<SalaCine> listarSalas() {
        return salaCineRepository.findByActivoTrue();
    }
    
    // READ - Obtener sala por ID
    public Optional<SalaCine> obtenerSalaPorId(Integer id) {
        return salaCineRepository.findByIdSalaAndActivoTrue(id);
    }
    
    // Buscar por nombre (ignorando mayúsculas)
    public Optional<SalaCine> buscarPorNombre(String nombre) {
        return salaCineRepository.findByNombreIgnoreCaseAndActivoTrue(nombre);
    }
    
    // CREATE - Crear nueva sala
    public SalaCine crearSala(SalaCine sala) {
        sala.setActivo(true);
        return salaCineRepository.save(sala);
    }
    
    // UPDATE - Actualizar sala
    public Optional<SalaCine> actualizarSala(Integer id, SalaCine sala) {
        return salaCineRepository.findById(id).map(existente -> {
            existente.setNombre(sala.getNombre());
            existente.setEstado(sala.getEstado());
            existente.setActivo(sala.getActivo());
            return salaCineRepository.save(existente);
        });
    }
    
    // DELETE - Eliminar sala (físico)
    public boolean eliminarSala(Integer id) {
        if (salaCineRepository.existsById(id)) {
            salaCineRepository.deleteById(id);
            return true;
        }
        return false;
    }
}