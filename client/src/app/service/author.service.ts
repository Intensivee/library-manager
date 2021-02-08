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

  getAll(): Observable<Author[]> {
    const url = `${API_URL}/authors`;
    return this.http.get<UnwrapAuthors>(url).pipe(
      map(response => response._embedded.authors));
  }

  getByName(name: string): Observable<Author[]> {
    const url = `${API_URL}/authors/search/findByName?name=${name}`;
    return this.http.get<UnwrapAuthors>(url).pipe(
      map(response => response._embedded.authors)
    );
  }

  getById(id: number): Observable<Author> {
    const url = `${API_URL}/authors/${id}`;
    return this.http.get<Author>(url);
  }

  create(author: Author): Observable<number> {
    const url = `${API_URL}/authors`;
    return this.http.post<any>(url, author).pipe(
      map(response => response.id)
    );
  }

  deleteById(authorId: number): Observable<any> {
    const url = `${API_URL}/authors/${authorId}`;
    return this.http.delete<any>(url);
  }

}

interface UnwrapAuthors {
  _embedded: {
    authors: Author[];
  };
}
