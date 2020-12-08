import { CategoryService } from '../../service/category.service';
import { Category } from 'src/app/models/category';
import { AuthorService } from '../../service/author.service';
import { Author } from '../../models/author';
import { BookService } from '../../service/book.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Book } from 'src/app/models/book';
import { FormControl } from '@angular/forms';
import { ReplaySubject } from 'rxjs';
import { MatSelect } from '@angular/material/select';
import { take } from 'rxjs/operators';

@Component({
  selector: 'app-book-add',
  templateUrl: './book-add.component.html',
  styleUrls: ['./book-add.component.css']
})
export class BookAddComponent implements OnInit {

  isValid = true;
  book: Book = new Book();
  author: Author;

  // Author mat-select-search stuff
  @ViewChild('singleSelect', { static: true }) singleSelect: MatSelect;
   authors: Author[] = [];
   authorCtrl: FormControl = new FormControl();
   authorFilterCtrl: FormControl = new FormControl();
   filteredAuthors: ReplaySubject<Author[]> = new ReplaySubject<Author[]>(1);

  // Categories mat-select-search stuff
  @ViewChild('multiSelect', { static: true }) multiSelect: MatSelect;
  protected categories: Category[] = [];
  public categoryCtrl: FormControl = new FormControl();
  public categoryFilterCtrl: FormControl = new FormControl();
  public filteredCategories: ReplaySubject<Category[]> = new ReplaySubject<Category[]>(1);

  constructor(
    private bookService: BookService,
    private authorService: AuthorService,
    private categoryService: CategoryService) { }

  ngOnInit(): void {
    this.authorService.getAuthors().subscribe(authors => {
      this.authors = authors;
      this.authorCtrl.setValue(this.authors[0]);
      this.filteredAuthors.next(this.authors.slice());
      this.authorFilterCtrl.valueChanges
        .subscribe(() => this.filterAuthors());
    });

    this.categoryService.getCategories().subscribe( categories => {
      this.categories = categories;
      this.filteredCategories.next(this.categories.slice());
      this.categoryFilterCtrl.valueChanges
      .subscribe( () => this.filterCategories());
    });
  }

  filterAuthors(): void {
    if (!this.authors) {
      return;
    }
    let search = this.authorFilterCtrl.value;
    if (!search) {
      this.filteredAuthors.next(this.authors.slice());
      return;
    } else {
      search = search.toLowerCase();
    }
    this.filteredAuthors.next(
      this.authors.filter(author => `${author.firstName} ${author.lastName}`.toLowerCase().indexOf(search) > -1)
    );
  }

  filterCategories(): void {
    if (!this.categories) {
      return;
    }
    let search = this.categoryFilterCtrl.value;
    if (!search) {
      this.filteredCategories.next(this.categories.slice());
      return;
    } else {
      search = search.toLowerCase();
    }
    this.filteredCategories.next(
      this.categories.filter(category => category.name.toLowerCase().indexOf(search) > -1)
    );
  }

  toggleSelectAll(selectAllValue: boolean): void {
    this.filteredCategories.pipe(take(1))
      .subscribe(val => {
        if (selectAllValue) {
          this.categoryCtrl.patchValue(val);
        } else {
          this.categoryCtrl.patchValue([]);
        }
      });
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
