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
      map(response => response._embedded.categories)
    );
  }
}

interface ResponseUnwrap {
  _embedded: {
    categories: Category[];
  };
}

