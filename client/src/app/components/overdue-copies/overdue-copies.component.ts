import { UserService } from '../../service/user.service';
import { Router } from '@angular/router';
import { BookService } from '../../service/book.service';
import { BorrowDetailsService } from '../../service/borrow-details.service';
import { CopyService } from '../../service/copy.service';
import { BorrowDetails } from '../../models/borrow-details';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';

@Component({
  selector: 'app-overdue-copies',
  templateUrl: './overdue-copies.component.html',
  styleUrls: ['./overdue-copies.component.css']
})
export class OverdueCopiesComponent implements OnInit {

  @ViewChild(MatSort) sort: MatSort;

  copies: BorrowDetails[] = [];
  dataSource = new MatTableDataSource(this.copies);
  displayedColumns = ['copyId', 'bookTitle', 'status', 'pages', 'borrowDate', 'returnDate', 'actions'];

  constructor(private copyService: CopyService,
              private bookService: BookService,
              private userService: UserService,
              private borrowDetailsService: BorrowDetailsService,
              private route: Router) { }

  ngOnInit(): void {
    this.getDataSource();
  }

  getDataSource(): void {
    this.copyService.getBorrowedCopies().subscribe(copies => {
      copies.forEach(copy => {
        this.bookService.getBook(copy.bookId).subscribe(book => {
          this.copies.push(this.borrowDetailsService.create(copy, book));
          this.copies.sort((n1, n2) => new Date(n1.returnDate).getTime() - new Date(n2.returnDate).getTime());
          this.dataSource = new MatTableDataSource(this.copies);
          this.dataSource.sort = this.sort;
        });
      });
    });
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  dayDifference(value: Date): number {
    const currentDate = new Date();
    const returnDate = new Date(value);
    const difference = returnDate.getTime() - currentDate.getTime();
    return Math.ceil(difference / (1000 * 3600 * 24));
  }

  findUser(copyId: number): void {
    this.userService.getUserByCopyId(copyId).subscribe(user => {
      this.route.navigate(['/users', user.id]);
    });
  }

  returnCopy(copyId: number): void {
    // TODO:
  }
}

