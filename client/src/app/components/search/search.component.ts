import {AuthorService} from '../../service/author.service';
import {ActivatedRoute} from '@angular/router';
import {BookService} from '../../service/book.service';
import {Component, OnInit} from '@angular/core';
import {Book} from 'src/app/models/book';
import {Author} from 'src/app/models/author';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  isSearchingForBooks = true;
  books: Book[];
  authors: Author[];


  constructor(private bookService: BookService,
              private authorService: AuthorService,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => this.search());
  }

  handleButtonClick(searchForBooks: boolean): void {
    this.isSearchingForBooks = searchForBooks;
    this.search();
  }

  search(): void {
    const searchKey = this.route.snapshot.paramMap.get('key');
    if (this.isSearchingForBooks) {
      this.getBookByTitle(searchKey);
    } else {
      this.getAuthorByName(searchKey);
    }
  }

  private getBookByTitle(title: string): void {
    this.bookService.getAllByTitle(title).subscribe(data => {
      this.books = data;
    }, () => {
    });
  }

  private getAuthorByName(name: string): void {
    this.authorService.getByName(name).subscribe(data => {
      this.authors = data;
    }, () => {
    });
  }
}
