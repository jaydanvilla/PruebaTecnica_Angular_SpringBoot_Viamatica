import { Component, OnInit, NgZone, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { PeliculaSalaCineService } from '../../../services/pelicula-sala-cine.service';
import { PeliculaService } from '../../../services/pelicula.service';
import { SalaCineService } from '../../../services/sala-cine.service';
import { PeliculaSalaCine } from '../../../models/pelicula-sala-cine.model';
import { Pelicula } from '../../../models/pelicula.model';
import { SalaCine } from '../../../models/sala-cine.model';
import { MenuComponent } from '../../menu/menu.component';

@Component({
  selector: 'app-asignacion-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, MenuComponent],
  templateUrl: './asignacion-form.component.html',
  styleUrls: ['./asignacion-form.component.css']
})
export class AsignacionFormComponent implements OnInit {
  asignacion: PeliculaSalaCine = {
    pelicula: { nombre: '', duracion: 0, activo: true },
    salaCine: { nombre: '', estado: '', activo: true },
    fechaPublicacion: new Date().toISOString().split('T')[0],
    activo: true
  };
  
  peliculas: Pelicula[] = [];
  salas: SalaCine[] = [];
  loading: boolean = false;
  errorMessage: string = '';

  constructor(
    private asignacionService: PeliculaSalaCineService,
    private peliculaService: PeliculaService,
    private salaService: SalaCineService,
    private router: Router,
    private ngZone: NgZone,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.cargarDatos();
  }

  cargarDatos(): void {
    this.loading = true;
    
    this.peliculaService.getAll().subscribe({
      next: (peliculasData) => {
        this.peliculas = peliculasData.filter(p => p.activo !== false);
        
        this.salaService.getAll().subscribe({
          next: (salasData) => {
            this.salas = salasData.filter(s => s.activo === true && s.estado === 'activa');
            this.loading = false;
            this.cdr.detectChanges();
          },
          error: (error) => {
            this.ngZone.run(() => {
              this.errorMessage = 'Error al cargar las salas';
              this.loading = false;
              this.cdr.detectChanges();
            });
          }
        });
      },
      error: (error) => {
        this.ngZone.run(() => {
          this.errorMessage = 'Error al cargar las películas';
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
    
    this.asignacionService.create(this.asignacion).subscribe({
      next: () => {
        this.ngZone.run(() => {
          this.router.navigate(['/asignaciones']);
        });
      },
      error: (error) => {
        this.ngZone.run(() => {
          this.errorMessage = 'Error al crear la asignación';
          this.loading = false;
          this.cdr.detectChanges();
        });
      }
    });
  }

  validarFormulario(): boolean {
    if (!this.asignacion.pelicula?.idPelicula) {
      this.errorMessage = 'Debe seleccionar una película';
      return false;
    }
    if (!this.asignacion.salaCine?.idSala) {
      this.errorMessage = 'Debe seleccionar una sala';
      return false;
    }
    if (!this.asignacion.fechaPublicacion) {
      this.errorMessage = 'Debe seleccionar una fecha';
      return false;
    }
    this.errorMessage = '';
    return true;
  }

  onPeliculaChange(event: any): void {
    const id = parseInt(event.target.value);
    const selected = this.peliculas.find(p => p.idPelicula === id);
    if (selected) {
      this.asignacion.pelicula = selected;
    }
  }

  onSalaChange(event: any): void {
    const id = parseInt(event.target.value);
    const selected = this.salas.find(s => s.idSala === id);
    if (selected) {
      this.asignacion.salaCine = selected;
    }
  }
}