import { Observable } from 'rxjs';
import { API_URL } from '../app.constants';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Book } from '../models/book';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  constructor(private http: HttpClient) { }

  getBooksByAuthorId(id: number): Observable<Book[]> {
    const url = `${API_URL}/books/search/findByAuthorId?id=${id}`;
    return this.http.get<UnwrapResponse>(url).pipe(
      map(response => response._embedded.books));
  }

}

interface UnwrapResponse {
  _embedded: {
    books: Book[];
  };
}
