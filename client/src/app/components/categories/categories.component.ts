
import { CategoryService } from '../../service/category.service';
import { Component, OnInit } from '@angular/core';
import { Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
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
    @Inject(MAT_DIALOG_DATA) public data) { }

  ngOnInit(): void {
    this.categories.length = 0;
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
      const cat = new Category();
      cat.id = null;
      cat.name = this.name;
      this.categoryService.addCategory(cat).subscribe(() => {
        this.ngOnInit();
      });
    }
  }

  validateData(): boolean {
    this.isValid = !(this.name == null || this.name.length < 1);
    return this.isValid;
  }

  deleteCategory(id: number): void {
    this.categoryService.deleteCategory(id).subscribe(() => this.ngOnInit());
  }

}


class CategoryObject {
  constructor(public category: Category,
              public isEmpty: boolean) { }
}
