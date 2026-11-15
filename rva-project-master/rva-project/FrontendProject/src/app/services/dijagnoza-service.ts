import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Dijagnoza } from '../models/dijagnoza';

@Injectable({
  providedIn: 'root',
})
export class DijagnozaService {
  constructor(private httpClient: HttpClient) {}
  public getAllDijagnozas(): Observable<any> {
    return this.httpClient.get('http://localhost:8080/dijagnozas');
  }
  public createDijagnoza(dijagnoza: Dijagnoza): Observable<any> {
    return this.httpClient.post('http://localhost:8080/dijagnozas', dijagnoza);
  }
  public updateDijagnoza(dijagnoza: Dijagnoza): Observable<any> {
    return this.httpClient.put(`http://localhost:8080/dijagnozas?id=${dijagnoza.id}`, dijagnoza);
  }
  public deteleDijagnoza(id: number): Observable<any> {
    return this.httpClient.delete(`http://localhost:8080/dijagnozas?id=${id}`);
  }
}
