import { Author } from '../../models/author';
import { AuthorService } from '../../service/author.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-author',
  templateUrl: './author.component.html',
  styleUrls: ['./author.component.css']
})
export class AuthorComponent implements OnInit {

  isValid = true;
  author: Author;

  constructor(
    private authorService: AuthorService) { }

  ngOnInit(): void {
  }


  onSubmit(): void {
    if (this.validateData()) {

    }
  }

  validateData(): boolean {
    this.isValid = false;
    return this.isValid;
  }

  uploadPhoto(): void {

  }
}
