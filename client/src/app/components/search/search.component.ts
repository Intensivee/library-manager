import { AuthorService } from './../../service/author.service';
import { ActivatedRoute } from '@angular/router';
import { BookService } from './../../service/book.service';
import { Component, OnInit } from '@angular/core';
import { Book } from 'src/app/models/book';
import { Author } from 'src/app/models/author';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  searchForBooks = true;
  books: Book[];
  authors: Author[];
  currentKey: string;

  pageNumber = 1;
  pageSize = 5;
  totalElements: number;

  constructor(private bookService: BookService,
              private authorService: AuthorService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => this.search());
  }

  buttonPress(searchForBooks: boolean): void {
    console.log(searchForBooks);
    this.searchForBooks = searchForBooks;
    this.pageNumber = 1;
    this.search();
  }

  search(): void {
    if (this.route.snapshot.paramMap.has('key')) {
      const key = this.route.snapshot.paramMap.get('key');
      if (key !== this.currentKey) {
        this.pageNumber = 1;
      }
      this.currentKey = key;
      if (this.searchForBooks) {
        this.bookService.getBooksByTitle(this.pageNumber - 1, this.pageSize, this.currentKey)
          .subscribe(this.processBookResoult());
      } else {
        this.authorService.getAuthorsByName(this.pageNumber - 1, this.pageSize, this.currentKey)
        .subscribe(this.processAuthorResoult);
      }
    }
  }


  processBookResoult() {
    return data => {
      this.books = data.content;
      this.pageNumber = data.number + 1;
      this.pageSize = data.size;
      this.totalElements = data.totalElements;
    };
  }

  processAuthorResoult() {
    return data => {
      this.authors = data._embeded.authors;
      this.pageNumber = data._embeded.page.number;
      this.pageSize = data._embeded.page.size;
      this.totalElements = data._embeded.page.totalElements;
    };
  }

  updatePageSize(pageSize: number) {
    this.pageSize = pageSize;
    this.pageNumber = 1;
    this.search();
  }

}
