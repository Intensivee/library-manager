import { CategoryService } from './../../service/category.service';
import { Component, OnInit } from '@angular/core';
import { Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Category } from 'src/app/models/category';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css']
})
export class CategoriesComponent implements OnInit {

  categories: Category[];

  isValid = true;
  name: string;

  constructor(
    private categoryService: CategoryService,
    @Inject(MAT_DIALOG_DATA) public data,
    public dialogRef: MatDialogRef<CategoriesComponent>) { }

  ngOnInit(): void {
    this.categoryService.getCategories().subscribe( data => this. categories = data);
  }


  onSubmit(): void {
    if (this.validateData()) {
      console.log('works');
    }
  }

  validateData(): boolean {
    if (this.name == null || this.name.length < 1){
      this.isValid = false;
    }
    else {
      this.isValid = true;
    }

    return this.isValid;
  }

}
