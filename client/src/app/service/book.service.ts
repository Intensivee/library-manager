import { Book } from '../models/book';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_URL } from '../app.constants';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  constructor(private httpClient: HttpClient) { }

  getById(id: number): Observable<Book> {
    return this.httpClient.get<Book>(`${API_URL}/books/${id}`);
  }

  getAllPaginated(page: number, pageSize: number): Observable<UnwrapPagedResponse> {
    const url = `${API_URL}/books/paged?page=${page}&size=${pageSize}`;
    return this.httpClient.get<UnwrapPagedResponse>(url);
  }

  getAllByCategoryId(page: number,
                     pageSize: number,
                     categoryId: number): Observable<UnwrapPagedResponse> {
    const url = `${API_URL}/books/search/findByCategoryId/${categoryId}?page=${page}&size=${pageSize}`;
    return this.httpClient.get<UnwrapPagedResponse>(url);
  }

  getAllByTitle(title: string): Observable<Book[]> {
    const url = `${API_URL}/books/search/findByTitle/${title}`;
    return this.httpClient.get<UnwrapResponse>(url).pipe(
      map(response => response._embedded.bookDtoes)
    );
  }

  getAllByAuthorId(id: number): Observable<Book[]> {
    const url = `${API_URL}/books/search/findByAuthorId/${id}`;
    return this.httpClient.get<UnwrapResponse>(url).pipe(
      map(response => response._embedded.bookDtoes)
    );
  }

  create(book: Book): Observable<number> {
    const url = `${API_URL}/books`;
    return this.httpClient.post<any>(url, book).pipe(
      map(response => response.id)
    );
  }

  deleteById(bookId: number): Observable<any> {
    return this.httpClient.delete(`${API_URL}/books/${bookId}`);
  }
}

interface UnwrapResponse {
  _embedded: {
    bookDtoes: Book[];
  };
}

interface UnwrapPagedResponse {
  _embedded: {
    bookDtoes: Book[];
  };
  page: {
    size: number;
    totalElements: number;
    totalPages: number;
    number: number;
  };
}
