import { Observable } from 'rxjs';
import { BookService } from './../../service/book.service';
import { CategoryService } from './../../service/category.service';
import { Component, OnInit } from '@angular/core';
import { Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Category } from 'src/app/models/category';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css']
})
export class CategoriesComponent implements OnInit {

  categories: CategoryObject[] = [];

  isValid = true;
  name: string;

  // popup window stuff
  popoverTitle = 'Dialog usunięcia';
  popoverMessage = 'Czy jesteś pewien że chcesz usunąć tę notatkę?';

  constructor(
    private categoryService: CategoryService,
    private bookService: BookService,
    private dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public data,
    public dialogRef: MatDialogRef<CategoriesComponent>) { }

  ngOnInit(): void {
    this.categoryService.getCategories().subscribe(categories => {
      categories.forEach(cat => {
        this.categoryService.isEmpty(cat.id).subscribe(isEmpty => {
          this.categories.push(new CategoryObject(cat, isEmpty));
        }
        );
      });
    });
  }


  onSubmit(): void {
    if (this.validateData()) {
      console.log('works');
    }
  }

  validateData(): boolean {
    if (this.name == null || this.name.length < 2) {
      this.isValid = false;
    }
    else {
      this.isValid = true;
    }
    return this.isValid;
  }

  deleteCategory(id: number): void {
    console.log('xd');
  }

}


class CategoryObject {
  constructor(public category: Category,
    public isEmpty: boolean) { }
}
