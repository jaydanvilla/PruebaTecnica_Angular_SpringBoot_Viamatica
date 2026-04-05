import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PeliculaSalaCine } from '../models/pelicula-sala-cine.model';

@Injectable({
  providedIn: 'root'
})
export class PeliculaSalaCineService {
  private apiUrl = 'http://localhost:8090/api/pelicula-sala-cine';

  constructor(private http: HttpClient) { }

  getAll(): Observable<PeliculaSalaCine[]> {
    return this.http.get<PeliculaSalaCine[]>(this.apiUrl);
  }

  create(asignacion: PeliculaSalaCine): Observable<PeliculaSalaCine> {
    return this.http.post<PeliculaSalaCine>(this.apiUrl, asignacion);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}