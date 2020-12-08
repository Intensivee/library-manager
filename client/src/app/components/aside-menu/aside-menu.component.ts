import { CategoryService } from './../../service/category.service';
import { Component, OnInit } from '@angular/core';
import { Category } from 'src/app/models/category';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { CategoriesComponent } from '../categories/categories.component';
import { AuthorComponent } from '../author/author.component';

@Component({
  selector: 'app-aside-menu',
  templateUrl: './aside-menu.component.html',
  styleUrls: ['./aside-menu.component.css']
})
export class AsideMenuComponent implements OnInit {

  categories: Category[];

  constructor(private categoryService: CategoryService,
              private dialog: MatDialog) { }

  ngOnInit(): void {
    this.getCategories();
  }

  openCategoryDialog(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    this.dialog.open(CategoriesComponent, dialogConfig)
      .afterClosed()
      .subscribe(() => this.ngOnInit());
  }

  openAuthorDialog(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    this.dialog.open(AuthorComponent, dialogConfig);
  }

  getCategories(): void {
    this.categoryService.getCategories().subscribe(
      data => {
        this.categories = data;
      }
    );
  }

}
