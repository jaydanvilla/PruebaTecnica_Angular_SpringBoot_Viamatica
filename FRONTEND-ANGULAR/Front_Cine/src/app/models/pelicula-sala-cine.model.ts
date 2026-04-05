import { Pelicula } from './pelicula.model';
import { SalaCine } from './sala-cine.model';

export interface PeliculaSalaCine {
  idPeliculaSala?: number;
  fechaPublicacion: string;
  fechaFin?: string;
  activo?: boolean;
  pelicula: Pelicula;
  salaCine: SalaCine;
}