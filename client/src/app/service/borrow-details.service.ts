import { Book } from 'src/app/models/book';
import { BorrowDetails } from '../models/borrow-details';
import { BookService } from './book.service';
import { Injectable } from '@angular/core';
import { Copy } from '../models/copy';

@Injectable({
  providedIn: 'root'
})
export class BorrowDetailsService {

  borrowDetails: BorrowDetails[] = [];

  constructor(private bookService: BookService) { }

  clear(): void {
    if (this.borrowDetails.length > 0){
      this.borrowDetails.length = 0; // clearing array
    }
  }


  getBorrowDetailsByCopies(copies: Copy[]): BorrowDetails[] {
    this.clear();
    copies.forEach(copy => {
      this.bookService.getBook(copy.bookId).subscribe(book => {
        this.borrowDetails.push(this.create(copy, book));
        this.borrowDetails.sort((n1, n2) => new Date(n1.returnDate).getTime() - new Date(n2.returnDate).getTime());
        // TODO: ^ find more efficient way than sorting with each subscribe (.pipe(finallize(..)), .add(..) - doesnt work)
      });
    });
    return this.borrowDetails;
}

  create(copy: Copy, book: Book): BorrowDetails{
    return new BorrowDetails(copy.id,
                              book.id,
                             book.title,
                             copy.pages,
                             copy.borrowDate,
                             copy.returnDate);
  }

}
