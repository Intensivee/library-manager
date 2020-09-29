import { BookService } from './../../service/book.service';
import { Book } from './../../models/book';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {

  books: Book[];
  currentCategoryId: number;

  pageNumber = 1;
  pageSize = 5;
  totalElements: number;

  constructor(private bookService: BookService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe( () => this.getBooks());
  }

  getBooks(): void {
    if (this.route.snapshot.paramMap.has('id')){
      const categoryId = +this.route.snapshot.paramMap.get('id');
      if (categoryId !== this.currentCategoryId){
        this.pageNumber = 1;
      }
      this.currentCategoryId = categoryId;
      this.bookService.getBooksPaginatedByCategoryId(this.pageNumber - 1, this.pageSize, this.currentCategoryId)
      .subscribe(this.processResoult());
    } else {

    // Spring enumerate pages from 0, while angular from 1
    this.bookService.getBooksPaginated(this.pageNumber - 1, this.pageSize)
      .subscribe(this.processResoult());
    }
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
    this.getBooks();
  }
}
