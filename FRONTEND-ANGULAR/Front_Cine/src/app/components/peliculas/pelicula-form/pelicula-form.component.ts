import { Component, OnInit, NgZone, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { PeliculaService } from '../../../services/pelicula.service';
import { Pelicula } from '../../../models/pelicula.model';
import { MenuComponent } from '../../menu/menu.component';

@Component({
  selector: 'app-pelicula-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, MenuComponent],
  templateUrl: './pelicula-form.component.html',
  styleUrls: ['./pelicula-form.component.css']
})
export class PeliculaFormComponent implements OnInit {
  pelicula: Pelicula = {
    nombre: '',
    duracion: 0,
    activo: true
  };
  
  isEditMode: boolean = false;
  loading: boolean = false;
  errorMessage: string = '';

  constructor(
    private peliculaService: PeliculaService,
    private route: ActivatedRoute,
    private router: Router,
    private ngZone: NgZone,
    private cdr: ChangeDetectorRef  // ← Agrega esto
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.params['id'];
    if (id) {
      this.isEditMode = true;
      this.cargarPelicula(id);
    }
  }

  cargarPelicula(id: number): void {
    this.loading = true;
    this.peliculaService.getById(id).subscribe({
      next: (data) => {
        this.ngZone.run(() => {
          this.pelicula = data;
          this.loading = false;
          this.cdr.detectChanges();
        });
      },
      error: (error) => {
        this.ngZone.run(() => {
          console.error('Error al cargar película:', error);
          this.errorMessage = 'Error al cargar la película';
          this.loading = false;
          this.cdr.detectChanges();
        });
      }
    });
  }

  onSubmit(): void {
    if (!this.validarFormulario()) {
      return;
    }

    this.loading = true;
    this.errorMessage = '';
    
    if (this.isEditMode && this.pelicula.idPelicula) {
      this.peliculaService.update(this.pelicula.idPelicula, this.pelicula).subscribe({
        next: () => {
          this.ngZone.run(() => {
            this.router.navigate(['/peliculas']);
          });
        },
        error: (error) => {
          this.ngZone.run(() => {
            console.error('Error al actualizar:', error);
            this.errorMessage = 'Error al actualizar la película';
            this.loading = false;
            this.cdr.detectChanges();
          });
        }
      });
    } else {
      this.peliculaService.create(this.pelicula).subscribe({
        next: () => {
          this.ngZone.run(() => {
            this.router.navigate(['/peliculas']);
          });
        },
        error: (error) => {
          this.ngZone.run(() => {
            console.error('Error al crear:', error);
            this.errorMessage = 'Error al crear la película';
            this.loading = false;
            this.cdr.detectChanges();
          });
        }
      });
    }
  }

  validarFormulario(): boolean {
    if (!this.pelicula.nombre?.trim()) {
      this.errorMessage = 'El nombre de la película es requerido';
      return false;
    }
    if (!this.pelicula.duracion || this.pelicula.duracion <= 0) {
      this.errorMessage = 'La duración debe ser mayor a 0';
      return false;
    }
    this.errorMessage = '';
    return true;
  }
}