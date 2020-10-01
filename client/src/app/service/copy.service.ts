import { API_URL } from './../app.constants';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Copy } from './../models/copy';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CopyService {

  constructor(private http: HttpClient) { }


  getCopiesByBookId(id: number): Observable<UnwrapResponse> {
    const url = `${API_URL}/copies/search/findByBookId?id=${id}`;
    return this.http.get<UnwrapResponse>(url);
  }
}


interface UnwrapResponse {
  _embedded: {
    copies: Copy[];
  };
}