import { Component, OnInit, ChangeDetectorRef, NgZone } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { PeliculaSalaCineService } from '../../../services/pelicula-sala-cine.service';
import { PeliculaSalaCine } from '../../../models/pelicula-sala-cine.model';
import { MenuComponent } from '../../menu/menu.component';

@Component({
  selector: 'app-asignacion-list',
  standalone: true,
  imports: [CommonModule, RouterLink, MenuComponent],
  templateUrl: './asignacion-list.component.html',
  styleUrls: ['./asignacion-list.component.css']
})
export class AsignacionListComponent implements OnInit {
  asignaciones: PeliculaSalaCine[] = [];
  loading: boolean = true;
  errorMessage: string = '';

  constructor(
    private asignacionService: PeliculaSalaCineService,
    private cdr: ChangeDetectorRef,
    private ngZone: NgZone
  ) {}

  ngOnInit(): void {
    this.cargarAsignaciones();
  }

  cargarAsignaciones(): void {
    this.loading = true;
    
    this.asignacionService.getAll().subscribe({
      next: (data) => {
        this.ngZone.run(() => {
          this.asignaciones = data;
          this.loading = false;
          this.cdr.detectChanges();
        });
      },
      error: (error) => {
        this.ngZone.run(() => {
          this.errorMessage = 'Error al cargar las asignaciones';
          this.loading = false;
          this.cdr.detectChanges();
        });
      }
    });
  }

  eliminarAsignacion(id: number | undefined): void {
    if (!id) {
      console.error('ID no válido');
      return;
    }
    
    if (confirm('¿Estás seguro de eliminar esta asignación?')) {
      this.asignacionService.delete(id).subscribe({
        next: () => {
          this.ngZone.run(() => {
            // Recargar la lista después de eliminar
            this.cargarAsignaciones();
            this.cdr.detectChanges();
          });
        },
        error: (error) => {
          this.ngZone.run(() => {
            console.error('Error al eliminar:', error);
            this.errorMessage = 'Error al eliminar la asignación';
            this.cdr.detectChanges();
          });
        }
      });
    }
  }
}