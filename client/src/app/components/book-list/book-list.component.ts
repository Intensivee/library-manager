import { Observable } from 'rxjs';
import { BookService } from './../../service/book.service';
import { Book } from './../../models/book';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {

  books: Book[];

  pageNumber = 1;
  pageSize = 5;
  totalElements: number;

  constructor(private bookService: BookService) { }

  ngOnInit(): void {
    this.getBooksPaginated();
  }

  getBooks() {
    this.bookService.getBooks().subscribe(
      data => {
        this.books = data._embedded.tupleBackedMaps;
        console.log(data);
      }
    );
  }

  // Spring enumerate pages from 0, while angular from 1
  getBooksPaginated(): void {
    console.log("xd");
    this.bookService.getBooksPaginated(this.pageNumber - 1, this.pageSize)
      .subscribe(this.processResoult());
  }

  processResoult() {
    return data => {
      this.books = data.content;
      this.pageNumber = data.number + 1;
      this.pageSize = data.size;
      this.totalElements = data.totalElements;
    };
  }


  updatePageSize(pageSize: number) {
    this.pageSize = pageSize;
    this.pageNumber = 1;
    this.getBooksPaginated();
  }

}
