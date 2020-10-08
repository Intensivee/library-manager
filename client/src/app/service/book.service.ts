import { CategoryService } from './category.service';
import { forkJoin, Observable } from 'rxjs';
import { API_URL } from '../app.constants';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Book } from '../models/book';
import { map, mergeMap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  constructor(private http: HttpClient,
              private categoryService: CategoryService) { }

  getBooksByAuthorId(id: number): Observable<Book[]> {
    const url = `${API_URL}/books/search/findByAuthorId?id=${id}`;
    return this.http.get<UnwrapResponse>(url).pipe(
      map(response => response._embedded.books)).pipe(
        mergeMap( books => {
          return forkJoin(
            books.map(book => {
              return this.categoryService.getCategoriesByBookId(book.id)
              .pipe(
                map(categories => {
                  book.categories = categories;
                  return book;
                })
              );
            })
          );
        })
      );
  }

}

interface UnwrapResponse {
  _embedded: {
    books: Book[];
  };
}
