import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SalaCine } from '../models/sala-cine.model';

@Injectable({
  providedIn: 'root'
})
export class SalaCineService {
  private apiUrl = 'http://localhost:8090/api/salas';

  constructor(private http: HttpClient) { }

  getAll(): Observable<SalaCine[]> {
    return this.http.get<SalaCine[]>(this.apiUrl);
  }

  getById(id: number): Observable<SalaCine> {
    return this.http.get<SalaCine>(`${this.apiUrl}/${id}`);
  }

  create(sala: SalaCine): Observable<SalaCine> {
    return this.http.post<SalaCine>(this.apiUrl, sala);
  }

  update(id: number, sala: SalaCine): Observable<SalaCine> {
    return this.http.put<SalaCine>(`${this.apiUrl}/${id}`, sala);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}