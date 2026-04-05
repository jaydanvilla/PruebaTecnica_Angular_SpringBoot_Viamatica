import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { PeliculaService } from '../../services/pelicula.service';
import { SalaCineService } from '../../services/sala-cine.service';
import { MenuComponent } from '../menu/menu.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink, MenuComponent],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  totalPeliculas: number = 0;
  totalSalas: number = 0;
  salasDisponibles: number = 0;
  loading: boolean = true;

  constructor(
    private peliculaService: PeliculaService,
    private salaService: SalaCineService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.cargarPeliculas();
    this.cargarSalas();
  }

  cargarPeliculas(): void {
    this.peliculaService.getAll().subscribe({
      next: (peliculas) => {
        this.totalPeliculas = peliculas.length;
        this.finalizarCarga();
      },
      error: (err) => {
        console.error('Error en películas:', err);
        this.finalizarCarga();
      }
    });
  }

  cargarSalas(): void {
    this.salaService.getAll().subscribe({
      next: (salas) => {
        this.totalSalas = salas.length;
        this.salasDisponibles = salas.filter(sala => 
          sala.estado === 'activa' && sala.activo === true
        ).length;
        this.finalizarCarga();
      },
      error: (err) => {
        console.error('Error en salas:', err);
        this.finalizarCarga();
      }
    });
  }

  finalizarCarga(): void {
    // Verificar si ya tenemos ambos valores
    if (this.totalPeliculas > 0 || this.totalSalas > 0) {
      // Esperar un poco para asegurar que ambos llegaron
      setTimeout(() => {
        this.loading = false;
        this.cdr.detectChanges();
        console.log('Dashboard actualizado:', {
          peliculas: this.totalPeliculas,
          salas: this.totalSalas,
          disponibles: this.salasDisponibles
        });
      }, 100);
    }
  }
}