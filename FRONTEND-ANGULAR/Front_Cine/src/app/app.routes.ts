import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { PeliculaListComponent } from './components/peliculas/pelicula-list/pelicula-list.component';
import { PeliculaFormComponent } from './components/peliculas/pelicula-form/pelicula-form.component';
import { SalaListComponent } from './components/salas/sala-list/sala-list.component';
import { SalaFormComponent } from './components/salas/sala-form/sala-form.component';
import { AsignacionListComponent } from './components/asignacion/asignacion-list/asignacion-list.component';
import { AsignacionFormComponent } from './components/asignacion/asignacion-form/asignacion-form.component';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'peliculas', component: PeliculaListComponent },
  { path: 'peliculas/nueva', component: PeliculaFormComponent },
  { path: 'peliculas/editar/:id', component: PeliculaFormComponent },
  { path: 'salas', component: SalaListComponent },
  { path: 'salas/nueva', component: SalaFormComponent },
  { path: 'salas/editar/:id', component: SalaFormComponent },
  { path: 'asignaciones', component: AsignacionListComponent },
  { path: 'asignaciones/nueva', component: AsignacionFormComponent },
  { path: '**', redirectTo: '/dashboard' },
];
