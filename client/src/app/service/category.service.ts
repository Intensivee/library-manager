import { API_URL } from '../app.constants';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Category } from '../models/category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private httpClient: HttpClient) { }

  getCategories(): Observable<Category[]> {
    return this.httpClient.get<ResponseUnwrap>(`${API_URL}/categories`).pipe(
      map(response => response._embedded.categories));
  }

  getCategoriesByBookId(id: number): Observable<Category[]> {
    const url = `${API_URL}/categories/search/findByBookId?id=${id}`;
    return this.httpClient.get<ResponseUnwrap>(url).pipe(
      map(response => response._embedded.categories));
  }

  isEmpty(id: number) {
    return this.httpClient.get(`${API_URL}/categories/search/isEmpty/${id}`).pipe(
      map(response => response as boolean)
    );
  }
}

interface ResponseUnwrap {
  _embedded: {
    categories: Category[];
  };
}

