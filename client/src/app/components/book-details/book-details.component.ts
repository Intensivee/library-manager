import { BookService } from './../../service/book.service';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { Book } from 'src/app/models/book';

@Component({
  selector: 'app-book-details',
  templateUrl: './book-details.component.html',
  styleUrls: ['./book-details.component.css']
})
export class BookDetailsComponent implements OnInit {

  book: Book;
  copies: Copy[];

  constructor(private bookService: BookService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe( () => this.getBookDetails())
  }

  getBookDetails(): void{
    if (this.route.snapshot.paramMap.has('id')){
      const id = +this.route.snapshot.paramMap.get('id');
      this.bookService.getBook(id).subscribe(data => {
        this.book = data;
        console.log(this.book);
        });
  }
  }

}