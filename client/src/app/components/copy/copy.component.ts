import { CopyService } from '../../service/copy.service';
import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Copy } from 'src/app/models/copy';

@Component({
  selector: 'app-copy',
  templateUrl: './copy.component.html',
  styleUrls: ['./copy.component.css']
})
export class CopyComponent implements OnInit {

  isValid = true;

  pages: number;
  quantity: number;

  constructor(
    private copyService: CopyService,
    @Inject(MAT_DIALOG_DATA) public data,
    public dialogRef: MatDialogRef<CopyComponent>) {}

  ngOnInit(): void {
  }

  onSubmit(): void {
    if (this.validateData()){
      const copy = new Copy();
      copy.id = null;
      copy.pages = this.pages;
      copy.borrowed = false;
      copy.borrowDate = null;
      copy.returnDate = null;
      copy.userId = null;
      copy.bookId = this.data.book.id;
      this.copyService.createCopies(copy, this.quantity).subscribe();
      this.dialogRef.close();
    }
  }

  validateData(): boolean{
    if (this.pages === null || this.pages < 1){
      this.isValid = false;
    }
    else if ( this.quantity == null || this.quantity < 1 || this.quantity > 5){
      this.isValid = false;
    }
    else {
      this.isValid = true;
    }
    return this.isValid;
  }
}
