package com.cine.Back_Cine.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PeliculaDTO {
    
    private Integer idPelicula;
    
    @NotBlank(message = "El nombre de la película es obligatorio")
    private String nombre;
    
    @NotNull(message = "La duración es obligatoria")
    @Positive(message = "La duración debe ser un número positivo")
    private Integer duracion;
    
    // Constructor vacío
    public PeliculaDTO() {}
    
    // Constructor con parámetros
    public PeliculaDTO(Integer idPelicula, String nombre, Integer duracion) {
        this.idPelicula = idPelicula;
        this.nombre = nombre;
        this.duracion = duracion;
    }
    
    // Getters y Setters
    public Integer getIdPelicula() {
        return idPelicula;
    }
    
    public void setIdPelicula(Integer idPelicula) {
        this.idPelicula = idPelicula;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Integer getDuracion() {
        return duracion;
    }
    
    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }
}