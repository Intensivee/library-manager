import { API_URL } from './../app.constants';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Author } from '../models/author';

@Injectable({
  providedIn: 'root'
})
export class AuthorService {

  constructor(private http: HttpClient) { }


  getAuthorsByName(page: number, size: number, name: string) {
    const url = `${API_URL}/authors/search/findByName?name=${name}`;
    return this.http.get<UnwrapPagedAuthors>(url);
  }

}

interface UnwrapPagedAuthors {
  _embeded: {
    authors: Author[];
    page: {
      size: number;
      totalElements: number;
      totalPages: number;
      number: number;
    }
  }
}
