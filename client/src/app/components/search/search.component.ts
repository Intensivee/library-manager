import { AuthorService } from '../../service/author.service';
import { ActivatedRoute } from '@angular/router';
import { BookService } from '../../service/book-dto.service';
import { Component, OnInit } from '@angular/core';
import { BookDto } from 'src/app/models/book-dto';
import { Author } from 'src/app/models/author';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  searchForBooks = true;
  books: BookDto[];
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
        this.bookService.getDtoBooksByTitle(this.currentKey)
          .subscribe(data => {
            this.books = data;
            console.log(data);
          });
      } else {
        this.authorService.getAuthorsByName(this.currentKey)
          .subscribe(data => {
            this.authors = data._embedded.authors;
          });
      }
    }
  }

}
