import {BorrowDetailsService} from '../../service/borrow-details.service';
import {BorrowDetails} from '../../models/borrow-details';
import {UserService} from '../../service/user.service';
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {User} from 'src/app/models/user';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit {

  user: User = new User();
  borrowDetails: BorrowDetails[] = [];


  constructor(private route: ActivatedRoute,
              private userService: UserService,
              private borrowDetailsService: BorrowDetailsService) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      this.getUserData();
    });
  }

  getUserData(): void {
    if (!this.route.snapshot.paramMap.has('id')) {
      return;
    }

    const id = +this.route.snapshot.paramMap.get('id');
    this.userService.getById(id).subscribe(data => {
      this.user = data;
      this.borrowDetails = this.borrowDetailsService.getByCopies(this.user.copies);
    });

  }

  countDaysToReturn(value: Date): number {
    const currentDate = new Date();
    const returnDate = new Date(value);
    const difference = returnDate.getTime() - currentDate.getTime();
    return Math.ceil(difference / (1000 * 3600 * 24));
  }

}
