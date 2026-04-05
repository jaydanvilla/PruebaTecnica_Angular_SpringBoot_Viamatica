import { Component, OnInit, ChangeDetectorRef, NgZone } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { SalaCineService } from '../../../services/sala-cine.service';
import { SalaCine } from '../../../models/sala-cine.model';
import { MenuComponent } from '../../menu/menu.component';

@Component({
  selector: 'app-sala-list',
  standalone: true,
  imports: [CommonModule, RouterLink, MenuComponent],
  templateUrl: './sala-list.component.html',
  styleUrls: ['./sala-list.component.css']
})
export class SalaListComponent implements OnInit {
  salas: SalaCine[] = [];
  loading: boolean = true;
  errorMessage: string = '';

  constructor(
    private salaService: SalaCineService,
    private cdr: ChangeDetectorRef,
    private ngZone: NgZone
  ) {}

  ngOnInit(): void {
    this.cargarSalas();
  }

  cargarSalas(): void {
    this.loading = true;
    
    this.salaService.getAll().subscribe({
      next: (data) => {
        this.ngZone.run(() => {
          this.salas = data;
          this.loading = false;
          this.cdr.detectChanges();
        });
      },
      error: (error) => {
        this.ngZone.run(() => {
          this.errorMessage = 'Error al cargar las salas';
          this.loading = false;
          this.cdr.detectChanges();
        });
      }
    });
  }

  eliminarSala(id: number | undefined): void {
    if (!id) return;
    if (confirm('¿Estás seguro de eliminar esta sala?')) {
      this.salaService.delete(id).subscribe({
        next: () => {
          this.cargarSalas();
        }
      });
    }
  }
}