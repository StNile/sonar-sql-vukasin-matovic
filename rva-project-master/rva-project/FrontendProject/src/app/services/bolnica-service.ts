import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Bolnica } from '../models/bolnica';

@Injectable({
  providedIn: 'root',
})
export class BolnicaService {
  constructor(private httpClient: HttpClient) {}
  public getAllBolnicas(): Observable<any> {
    return this.httpClient.get('http://localhost:8080/bolnicas');
  }
  public createBolnica(bolnica: Bolnica): Observable<any> {
    return this.httpClient.post('http://localhost:8080/bolnicas', bolnica);
  }
  public updateBolnica(bolnica: Bolnica): Observable<any> {
    return this.httpClient.put(`http://localhost:8080/bolnicas?id=${bolnica.id}`, bolnica);
  }
  public deleteBolnica(id: number): Observable<any> {
    return this.httpClient.delete(`http://localhost:8080/bolnicas?id=${id}`);
  }
}
