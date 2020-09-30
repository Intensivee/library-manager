import { Observable } from 'rxjs';
import { API_URL } from './../app.constants';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Author } from '../models/author';

@Injectable({
  providedIn: 'root'
})
export class AuthorService {

  constructor(private http: HttpClient) { }


  getAuthorsByName(name: string): Observable<UnwrapAuthors> {
    const url = `${API_URL}/authors/search/findByName?name=${name}`;
    return this.http.get<UnwrapAuthors>(url);
  }

}

interface UnwrapAuthors {
  _embedded: {
    authors: Author[];
  };
}

interface UnwrapPagedAuthors {
  _embedded: {
    authors: Author[];
    page: {
      size: number;
      totalElements: number;
      totalPages: number;
      number: number;
    }
  };
}
