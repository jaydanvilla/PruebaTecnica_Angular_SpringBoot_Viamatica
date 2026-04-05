package com.cine.Back_Cine.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "pelicula_sala_cine")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeliculaSalaCine {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pelicula_sala")
    private Integer idPeliculaSala;
    
    @Column(name = "fecha_publicacion", nullable = false)
    private LocalDate fechaPublicacion;
    
    @Column(name = "fecha_fin")
    private LocalDate fechaFin;
    
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
    
    @JsonIgnoreProperties({"peliculaSalaCines", "hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pelicula", nullable = false)
    private Pelicula pelicula;
    
    @JsonIgnoreProperties({"peliculaSalaCines", "hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sala_cine", nullable = false)
    private SalaCine salaCine;
}