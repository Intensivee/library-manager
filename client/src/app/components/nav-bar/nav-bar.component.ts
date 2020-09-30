import { BookListComponent } from './../book-list/book-list.component';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {

  constructor(private route: Router) { }

  ngOnInit(): void {
  }

  searchFor(key: string): void {
    this.route.navigate(['search', key]);
  }

}
