import { UNTRUSTED_USER_ROLE } from '../../app.constants';
import { UserService } from '../../service/user.service';
import { User } from 'src/app/models/user';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { TRUSTED_USER_ROLE } from 'src/app/app.constants';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  @ViewChild(MatSort) sort: MatSort;

  users: User[] = [];
  displayedColumns = ['firstName', 'lastName', 'username', 'email', 'role', 'copies', 'actions'];
  dataSource = new MatTableDataSource(this.users);


  // popup window stuff
  popoverTitle = 'Dialog usunięcia';
  popoverMessage = 'Czy jesteś pewien że chcesz usunąć tego użytkownika?';

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.getDataSource();
  }

  getDataSource(): void {
    this.userService.getUsers().subscribe(data => {
      this.users = data;
      this.dataSource = new MatTableDataSource(this.users);
      this.dataSource.sort = this.sort;
    });
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  activateUser(user: User): void {
    user.role = TRUSTED_USER_ROLE;
    this.userService.updateUser(user).subscribe(response => user = response);
  }

  deActivateUser(user: User): void {
    user.role = UNTRUSTED_USER_ROLE;
    this.userService.updateUser(user).subscribe(response => user = response);
  }

  deleteUser(user: User): void {
    this.userService.deleteUser(user).subscribe( () => this.getDataSource());
  }
}
