import { CopyService } from '../../service/copy.service';
import { Copy } from '../../models/copy';
import { BookService } from '../../service/book.service';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { Book } from 'src/app/models/book';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { CopyComponent } from '../copy/copy.component';

@Component({
  selector: 'app-book-details',
  templateUrl: './book-details.component.html',
  styleUrls: ['./book-details.component.css']
})
export class BookDetailsComponent implements OnInit {

  book: Book = new Book();
  copies: Copy[] = [];

  constructor(private bookService: BookService,
              private copyService: CopyService,
              private route: ActivatedRoute,
              private dialog: MatDialog) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => this.getBookDetails());
  }

  getBookDetails(): void {
    if (this.route.snapshot.paramMap.has('id')) {
      const id = +this.route.snapshot.paramMap.get('id');
      this.bookService.getBook(id).subscribe(data => this.book = data);
      this.copyService.getCopiesByBookId(id).subscribe(data => this.copies = data);
    }
  }

  addCopy(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    const book = this.book;
    dialogConfig.data = { book };
    this.dialog.open(CopyComponent, dialogConfig)
      .afterClosed()
      .subscribe(() => this.ngOnInit());
  }



}
