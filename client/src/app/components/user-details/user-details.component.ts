import { BookService } from './../../service/book.service';
import { Copy } from './../../models/copy';
import { UserService } from '../../service/user.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/app/models/user';
import { Book } from 'src/app/models/book';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit {

  user: User = new User();
  borrowDetails: borrowDetails[] = [];


  constructor(private route: ActivatedRoute,
              private userService: UserService,
              private bookService: BookService) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      this.getUser();
    });
  }

  getUser(): void {

    if (this.route.snapshot.paramMap.has('id')){
      const id = +this.route.snapshot.paramMap.get('id');
      this.userService.getUser(id)
      .subscribe(data => {
        this.user = data;
        this.getBorrowDetails(this.user.copies);
      });
    }
  }

  getBorrowDetails(copies: Copy[]): void{
    copies.forEach(copy => {
      this.bookService.getBook(copy.id).subscribe(book => {
        this.borrowDetails.push(new borrowDetails(copy, book));
        this.borrowDetails.sort((n1, n2) => new Date(n1.copy.returnDate).getTime() - new Date(n2.copy.returnDate).getTime());
        // TODO: ^ find more efficient way than sorting with each subscribe (.pipe(finallize(..)), .add(..) - doesnt work)
      });
    });
  }

  dayDifference(value: Date): number {
    const currentDate = new Date();
    const returnDate = new Date(value);
    const difference = returnDate.getTime() - currentDate.getTime();
    return Math.ceil( difference / (1000 * 3600 * 24));
  }

}

class borrowDetails {
  constructor(public copy: Copy, public book: Book){}
}

