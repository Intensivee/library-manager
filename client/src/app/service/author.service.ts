import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { API_URL } from '../app.constants';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Author } from '../models/author';

@Injectable({
  providedIn: 'root'
})
export class AuthorService {

  constructor(private http: HttpClient) { }

  getAuthors(): Observable<Author[]> {
    const url = `${API_URL}/authors`;
    return this.http.get<UnwrapAuthors>(url).pipe(map(response => response._embedded.authors));
  }

  getAuthorsByName(name: string): Observable<Author[]> {
    const url = `${API_URL}/authors/search/findByName?name=${name}`;
    return this.http.get<UnwrapAuthors>(url).pipe(
      map(response => response._embedded.authors)
    );
  }

  getAuthorById(id: number): Observable<Author> {
    const url = `${API_URL}/authors/${id}`;
    return this.http.get<Author>(url);
  }

}

interface UnwrapAuthors {
  _embedded: {
    authors: Author[];
  };
}
