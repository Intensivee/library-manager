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

  getBooks(): Observable<Book[]> {
    return this.httpClient.get<UnwrapResponse>(`${API_URL}/books`).pipe(
      map(response => response._embedded.bookDtoes)
    );
  }

  getBook(id: number): Observable<Book> {
    return this.httpClient.get<Book>(`${API_URL}/books/${id}`);
  }

  getBookByCopyId(id: number): Observable<Book> {
    return this.httpClient.get<Book>(`${API_URL}/books/${id}`);
  }
  getBooksPaginated(page: number, pageSize: number): Observable<UnwrapPagedResponse> {
    const url = `${API_URL}/books/paged?page=${page}&size=${pageSize}`;
    return this.httpClient.get<UnwrapPagedResponse>(url);
  }

  getBooksByCategoryId(page: number,
                          pageSize: number,
                          categoryId: number): Observable<UnwrapPagedResponse> {
    const url = `${API_URL}/books/search/findByCategoryId/${categoryId}?page=${page}&size=${pageSize}`;
    return this.httpClient.get<UnwrapPagedResponse>(url);
  }

  getBooksByTitle(title: string): Observable<Book[]> {
    const url = `${API_URL}/books/search/findByTitle/${title}`;
    return this.httpClient.get<UnwrapResponse>(url).pipe(
      map(response => response._embedded.bookDtoes)
    );
  }

  getBooksByAuthorId(id: number): Observable<Book[]> {
    const url = `${API_URL}/books/search/findByAuthorId/${id}`;
    return this.httpClient.get<UnwrapResponse>(url).pipe(
      map(response => response._embedded.bookDtoes)
    );
  }

  addBook(book: Book): Observable<number> {
    const url = `${API_URL}/books`;
    return this.httpClient.post<any>(url, book).pipe(
      map(response => response.id)
    );
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


