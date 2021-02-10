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

  getAll(): Observable<Category[]> {
    return this.httpClient.get<ResponseUnwrap>(`${API_URL}/categories`).pipe(
      map(response => response._embedded.categoryDtoes));
  }

  getByBookId(id: number): Observable<Category[]> {
    const url = `${API_URL}/categories/search/findByBookId?id=${id}`;
    return this.httpClient.get<ResponseUnwrap>(url).pipe(
      map(response => response._embedded.categoryDtoes));
  }

  isEmpty(id: number): Observable<any> {
    const url = `${API_URL}/categories/search/isEmpty/${id}`;
    return this.httpClient.get(url).pipe(
      map(response => response as boolean)
    );
  }

  create(category: Category): Observable<any> {
    const url = `${API_URL}/categories`;
    return this.httpClient.post(url, category);
  }

  deleteById(id: number): Observable<any> {
    const url = `${API_URL}/categories/${id}`;
    return this.httpClient.delete(url);
  }
}

interface ResponseUnwrap {
  _embedded: {
    categoryDtoes: Category[];
  };
}

