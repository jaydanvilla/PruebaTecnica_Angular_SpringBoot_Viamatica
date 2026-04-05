import { Component, OnInit, NgZone, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { PeliculaService } from '../../../services/pelicula.service';
import { Pelicula } from '../../../models/pelicula.model';
import { MenuComponent } from '../../menu/menu.component';

@Component({
  selector: 'app-pelicula-list',
  standalone: true,
  imports: [CommonModule, RouterLink, MenuComponent],
  templateUrl: './pelicula-list.component.html',
  styleUrls: ['./pelicula-list.component.css']
})
export class PeliculaListComponent implements OnInit {
  peliculas: Pelicula[] = [];
  loading: boolean = true;
  errorMessage: string = '';

  constructor(
    private peliculaService: PeliculaService,
    private ngZone: NgZone,
    private cdr: ChangeDetectorRef
  ) {
    console.log('PeliculaListComponent constructor');
  }

  ngOnInit(): void {
    console.log('PeliculaListComponent ngOnInit');
    this.cargarPeliculas();
  }

  cargarPeliculas(): void {
    console.log('cargarPeliculas iniciado');
    this.loading = true;
    
    this.peliculaService.getAll().subscribe({
      next: (data) => {
        this.ngZone.run(() => {
          console.log('Datos recibidos:', data);
          this.peliculas = data;
          this.loading = false;
          this.cdr.detectChanges();
        });
      },
      error: (error) => {
        this.ngZone.run(() => {
          console.error('Error:', error);
          this.errorMessage = 'Error al cargar las películas';
          this.loading = false;
          this.cdr.detectChanges();
        });
      }
    });
  }

  eliminarPelicula(id: number | undefined): void {
    if (!id) return;
    if (confirm('¿Estás seguro de eliminar esta película?')) {
      this.peliculaService.delete(id).subscribe({
        next: () => {
          this.cargarPeliculas();
        }
      });
    }
  }
}