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
import { finalize } from 'rxjs/operators';
import { AngularFireStorage } from '@angular/fire/storage';
import { MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';

@Component({
  selector: 'app-book-add',
  templateUrl: './book-add.component.html',
  styleUrls: ['./book-add.component.css']
})
export class BookAddComponent implements OnInit {

  isValid = true;
  book: Book = new Book();
  author: Author;

  imageUrl: string;
  imageSrc: string;
  selectedImage: any = null;

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
    private categoryService: CategoryService,
    private fireStorage: AngularFireStorage,
    private router: Router,
    private dialogRef: MatDialogRef<BookAddComponent>) { }

  ngOnInit(): void {
    this.authorService.getAuthors().subscribe(authors => {
      this.authors = authors;
      this.authorCtrl.setValue(this.authors[0]);
      this.filteredAuthors.next(this.authors.slice());
      this.authorFilterCtrl.valueChanges
        .subscribe(() => this.filterAuthors());
    });

    this.categoryService.getCategories().subscribe(categories => {
      this.categories = categories;
      this.filteredCategories.next(this.categories.slice());
      this.categoryFilterCtrl.valueChanges
        .subscribe(() => this.filterCategories());
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



  onSubmit(): void {
    if (this.validateData()) {
      // adding current time to avoid duplicate names + deleting file extension
      const filePath = `image/book/${this.selectedImage.name.split('.').slice(0, -1).join('.')}_${new Date().getTime()}`;
      const fileRef = this.fireStorage.ref(filePath);
      this.fireStorage.upload(filePath, this.selectedImage)
            .snapshotChanges()
            .pipe(
              finalize(() => {
                fileRef.getDownloadURL().subscribe((url) => {
                  this.imageUrl = url;
                  this.book.imageUrl = url;
                  this.book.categories = this.categoryCtrl.value;
                  this.book.authorId = this.authorCtrl.value;
                  this.bookService.addBook(this.book).subscribe(
                    bookId => {
                        this.router.navigate(['/books', bookId]);
                        this.dialogRef.close();
                    },  error => {
                        // deleting photo if server didn't accept book (not very sufficient..)
                        this.fireStorage.refFromURL(url).delete();
                        this.isValid = false;
                    }
                  );
                });
              } )
            )
            .subscribe();
    }
  }

  validateData(): boolean {
    this.isValid = true;
    return this.isValid;
  }

  showImg(event: any): void {
    if (event.target.files && event.target.files[0]) {
      const reader = new FileReader();
      reader.onload = (e: any) => this.imageSrc = e.target.result;
      reader.readAsDataURL(event.target.files[0]);
      this.selectedImage = event.target.files[0];
    }
  }

}
