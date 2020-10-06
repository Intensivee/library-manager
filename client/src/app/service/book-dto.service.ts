import { BookDto } from '../models/book-dto';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_URL } from '../app.constants';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  constructor(private httpClient: HttpClient) { }

  getDtoBooks(): Observable<UnwrapResponse> {
    return this.httpClient.get<UnwrapResponse>(`${API_URL}/dtoBooks`);
  }

  getDtoBook(id: number): Observable<BookDto> {
    return this.httpClient.get<BookDto>(`${API_URL}/dtoBooks/${id}`);
  }

  getDtoBooksPaginated(page: number, pageSize: number): Observable<UnwrapPagedResponse> {
    const url = `${API_URL}/dtoBooks/paged?page=${page}&size=${pageSize}`;
    return this.httpClient.get<UnwrapPagedResponse>(url);
  }

  getDtoBooksByCategoryId(page: number,
                          pageSize: number,
                          categoryId: number): Observable<UnwrapPagedResponse> {
    const url = `${API_URL}/dtoBooks/search/findByCategoryId/${categoryId}?page=${page}&size=${pageSize}`;
    return this.httpClient.get<UnwrapPagedResponse>(url);
  }

  getDtoBooksByTitle(title: string): Observable<UnwrapResponse> {
    const url = `${API_URL}/dtoBooks/search/findByTitle/${title}`;
    return this.httpClient.get<UnwrapResponse>(url);
  }
}

interface UnwrapResponse {
  _embedded: {
    tupleBackedMaps: BookDto[];
  };
}

interface UnwrapPagedResponse {
  content: BookDto[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}


