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
  author: Author = new Author();
  imageUrl: string;

  imageSrc: string;
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
      this.fireStorage.upload(filePath, this.selectedImage)
            .snapshotChanges()
            .pipe(
              finalize(() => {
                fileRef.getDownloadURL().subscribe((url) => {
                  this.imageUrl = url;
                  this.author.imageUrl = url;
                  this.authorService.addAuthor(this.author).subscribe(
                    authorId => {
                        console.log(authorId);
                        this.router.navigate(['/authors', authorId]);
                        this.dialogRef.close();
                    },  error => {
                        // deleting photo if server didn't accept author (not very sufficient..)
                        console.log(error);
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
