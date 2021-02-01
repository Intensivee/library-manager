import { AuthorService } from '../../service/author.service';
import { ActivatedRoute } from '@angular/router';
import { BookService } from '../../service/book.service';
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


  constructor(private bookService: BookService,
              private authorService: AuthorService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => this.search());
  }

  buttonPress(searchForBooks: boolean): void {
    this.searchForBooks = searchForBooks;
    this.search();
  }

  search(): void {
    if (this.route.snapshot.paramMap.has('key')) {
      this.currentKey = this.route.snapshot.paramMap.get('key');
      if (this.searchForBooks) {
        this.bookService.getBooksByTitle(this.currentKey)
          .subscribe(data => {
            this.books = data;
            console.log(data);
          }, () => {});
      } else {
        this.authorService.getAuthorsByName(this.currentKey)
          .subscribe(data => {
            this.authors = data;
          }, () => {});
      }
    }
  }

}
