import { Component, OnInit, NgZone, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { SalaCineService } from '../../../services/sala-cine.service';
import { SalaCine } from '../../../models/sala-cine.model';
import { MenuComponent } from '../../menu/menu.component';

@Component({
  selector: 'app-sala-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, MenuComponent],
  templateUrl: './sala-form.component.html',
  styleUrls: ['./sala-form.component.css']
})
export class SalaFormComponent implements OnInit {
  sala: SalaCine = {
    nombre: '',
    estado: 'disponible',
    activo: true
  };
  
  isEditMode: boolean = false;
  loading: boolean = false;
  errorMessage: string = '';

  constructor(
    private salaService: SalaCineService,
    private route: ActivatedRoute,
    private router: Router,
    private ngZone: NgZone,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.params['id'];
    if (id) {
      this.isEditMode = true;
      this.cargarSala(id);
    }
  }

  cargarSala(id: number): void {
    this.loading = true;
    this.salaService.getById(id).subscribe({
      next: (data) => {
        this.ngZone.run(() => {
          this.sala = data;
          this.loading = false;
          this.cdr.detectChanges();
        });
      },
      error: (error) => {
        this.ngZone.run(() => {
          this.errorMessage = 'Error al cargar la sala';
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
    
    if (this.isEditMode && this.sala.idSala) {
      this.salaService.update(this.sala.idSala, this.sala).subscribe({
        next: () => {
          this.ngZone.run(() => {
            this.router.navigate(['/salas']);
          });
        },
        error: (error) => {
          this.ngZone.run(() => {
            this.errorMessage = 'Error al actualizar la sala';
            this.loading = false;
            this.cdr.detectChanges();
          });
        }
      });
    } else {
      this.salaService.create(this.sala).subscribe({
        next: () => {
          this.ngZone.run(() => {
            this.router.navigate(['/salas']);
          });
        },
        error: (error) => {
          this.ngZone.run(() => {
            this.errorMessage = 'Error al crear la sala';
            this.loading = false;
            this.cdr.detectChanges();
          });
        }
      });
    }
  }

  validarFormulario(): boolean {
    if (!this.sala.nombre?.trim()) {
      this.errorMessage = 'El nombre de la sala es requerido';
      return false;
    }
    if (!this.sala.estado) {
      this.errorMessage = 'El estado es requerido';
      return false;
    }
    this.errorMessage = '';
    return true;
  }
}