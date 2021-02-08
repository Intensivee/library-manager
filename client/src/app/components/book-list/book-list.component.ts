import { BookService } from '../../service/book.service';
import { Book } from '../../models/book';
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
      this.bookService.getAllByCategoryId(this.pageNumber - 1, this.pageSize, this.currentCategoryId)
      .subscribe(this.processResult());
    } else {

    // Spring enumerate pages from 0, while angular from 1
      this.bookService.getAllPaginated(this.pageNumber - 1, this.pageSize)
        .subscribe(this.processResult());
    }
  }

  processResult(): any {
    return data => {
      this.books = data._embedded.bookDtoes;
      this.pageNumber = data.page.number + 1;
      this.pageSize = data.page.size;
      this.totalElements = data.page.totalElements;
    };
  }

  updatePageSize(pageSize: number): void {
    this.pageSize = pageSize;
    this.pageNumber = 1;
    this.getBooks();
  }
}
