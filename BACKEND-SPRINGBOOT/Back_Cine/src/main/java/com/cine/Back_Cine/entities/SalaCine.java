package com.cine.Back_Cine.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "sala_cine")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SalaCine {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sala")
    private Integer idSala;
    
    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;
    
    @Column(name = "estado", nullable = false, length = 50)
    private String estado;
    
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
    
    // Relación con la tabla intermedia
    @JsonIgnore  
    @OneToMany(mappedBy = "salaCine", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PeliculaSalaCine> peliculaSalaCines;
}