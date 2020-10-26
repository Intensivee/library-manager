import { API_URL } from '../app.constants';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Copy } from '../models/copy';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CopyService {

  constructor(private http: HttpClient) { }


  getCopiesByBookId(id: number): Observable<Copy[]> {
    const url = `${API_URL}/copies/search/findByBookId/${id}`;
    return this.http.get<UnwrapResponse>(url).pipe(
      map(data => data._embedded.copyDtoes)
    );
  }

  getCopiesByUserId(id: number): Observable<Copy[]> {
    const url = `${API_URL}/copies/search/findByUserId/${id}`;
    return this.http.get<UnwrapResponse>(url).pipe(
      map(data => data._embedded.copyDtoes)
    );
  }
}


interface UnwrapResponse {
  _embedded: {
    copyDtoes: Copy[];
  };
}
