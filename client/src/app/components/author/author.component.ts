import { DEFAULT_IMG } from '../../app.constants';
import { Router } from '@angular/router';
import { Author } from '../../models/author';
import { AuthorService } from '../../service/author.service';
import { Component, OnInit } from '@angular/core';
import { AngularFireStorage } from '@angular/fire/storage';
import { finalize } from 'rxjs/operators';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-author',
  templateUrl: './author.component.html',
  styleUrls: ['./author.component.css']
})
export class AuthorComponent implements OnInit {

  isValid = true;
  errorMessage: string;
  author: Author = new Author();
  imageUrl: string;

  imageSrc = DEFAULT_IMG;
  selectedImage: any = null;

  constructor(
    private authorService: AuthorService,
    private fireStorage: AngularFireStorage,
    private router: Router,
    private dialogRef: MatDialogRef<AuthorComponent>) { }

  ngOnInit(): void {
  }


  onSubmit(): void {
    if (this.validateData()) {
      // adding current time to avoid duplicate names + deleting file extension
      const filePath = `image/author/${this.selectedImage.name.split('.').slice(0, -1).join('.')}_${new Date().getTime()}`;
      const fileRef = this.fireStorage.ref(filePath);
      this.fireStorage
        .upload(filePath, this.selectedImage)
        .snapshotChanges()
        .pipe(
          finalize(() => {
            this.createAuthor(fileRef);
          }))
        .subscribe();
    }
  }

  validateData(): boolean {
    this.isValid = true;
    this.errorMessage = null;
    if (!this.areInputsFilled()) {
      this.errorMessage = 'Not all inputs are filled!';
    }
    else if (!this.isFirstNameProperLength()) {
      this.errorMessage = 'first name must be between 2 and 20 characters!';
    }
    else if (!this.isLastNameProperLength()) {
      this.errorMessage = 'Last name must be between 2 and 30 characters!';
    }
    else if (!this.isMemoirProperLength()) {
      this.errorMessage = 'Memoir must be between 1 and 250 characters!';
    }
    else if (!this.isDataCorrect()) {
      this.errorMessage = 'Incorrect data!';
    }
    else if (!this.isImageValid()) {
      this.errorMessage = 'Provide proper image that weighs less than 2MB!';
    }
    if (this.errorMessage != null) {
      this.isValid = false;
    }
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

  createAuthor(fileRef) {
    fileRef.getDownloadURL().subscribe((url) => {
      this.imageUrl = url;
      this.author.imageUrl = url;

      this.authorService.addAuthor(this.author).subscribe(
        authorId => {

          this.navigateToAuthor(authorId);
        }, error => {
          this.deletePhoto(url);
          this.errorMessage = 'Could not add Author.';
          this.clearAllFields();
        }
      );
    });
  }

  navigateToAuthor(authorId: number): void {
    this.router.navigate(['/authors', authorId]);
    this.dialogRef.close();
  }

  deletePhoto(url: string): void {
    this.fireStorage.refFromURL(url).delete();
    this.imageSrc = DEFAULT_IMG;
    this.isValid = false;
  }

  areInputsFilled(): boolean {
    return !(this.author.firstName == null ||
      this.author.lastName == null ||
      this.author.memoir == null ||
      this.author.birthDate == null);
  }

  isFirstNameProperLength(): boolean {
    return this.author.firstName.length < 20 && this.author.firstName.length > 2;
  }

  isLastNameProperLength(): boolean {
    return this.author.lastName.length < 30 && this.author.lastName.length > 2;
  }

  isMemoirProperLength(): boolean {
    return this.author.memoir.length < 250 && this.author.memoir.length > 0;
  }

  isDataCorrect(): boolean {
    const currentDate = new Date();
    const birthDate = new Date(this.author.birthDate);
    return currentDate.getTime() > birthDate.getTime() + (1000 * 3600 * 24 * 365 * 10);
  }

  isImageValid(): boolean {
    const maxSizeInMB = 2;
    return this.selectedImage &&
      this.selectedImage.size / (1024 * 1024) < maxSizeInMB;
  }

  clearAllFields(): void {
    this.selectedImage = null;
    this.author.firstName = '';
    this.author.lastName = '';
    this.author.memoir = '';
    this.author.birthDate = null;
  }
}
