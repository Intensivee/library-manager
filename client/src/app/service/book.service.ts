import { Book } from './../models/book';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_URL } from '../app.constants';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  constructor(private httpClient: HttpClient) { }

  getBooks(): Observable<UnwrapResponse> {
    return this.httpClient.get<UnwrapResponse>(`${API_URL}/books`);
  }

  getBook(id: number): Observable<Book> {
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

  getBooksByTitle(title: string): Observable<UnwrapResponse> {
    const url = `${API_URL}/books/search/findByTitle/${title}`;
    return this.httpClient.get<UnwrapResponse>(url);
  }
}

interface UnwrapResponse {
  _embedded: {
    tupleBackedMaps: Book[];
  };
}

interface UnwrapPagedResponse {
  content: Book[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}


