import { BookService } from '../../service/book.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthorService } from '../../service/author.service';
import { Component, OnInit } from '@angular/core';
import { Author } from 'src/app/models/author';
import { Book } from 'src/app/models/book';

@Component({
  selector: 'app-author-details',
  templateUrl: './author-details.component.html',
  styleUrls: ['./author-details.component.css']
})
export class AuthorDetailsComponent implements OnInit {

  author: Author = new Author();
  books: Book[] = [];

    // popup window stuff
    popoverTitle = 'Delete dialog';
    popoverMessage = 'Are you sure?';

  constructor(private authorService: AuthorService,
              private bookService: BookService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit(): void {
    if (this.route.snapshot.paramMap.has('id')) {
      const authorId = +this.route.snapshot.paramMap.get('id');
      this.getAuthor(authorId);
      this.getBooksByAuthorId(authorId);
    }
  }

  getAuthor(authorId: number): void {
      this.authorService.getAuthorById(authorId)
      .subscribe(author => {
        this.author = author;
      }, () => {}
      );
  }

  getBooksByAuthorId(authorId: number): void {
    this.bookService.getBooksByAuthorId(authorId)
    .subscribe(books => {
      this.books = books;
    }, () => {}
    );
  }

  deleteAuthor(): void {
    this.authorService.deleteAuthor(this.author.id).subscribe(
      () => {
        this.router.navigate(['/books']);
      },
      () => this.ngOnInit()
    );
  }
}
