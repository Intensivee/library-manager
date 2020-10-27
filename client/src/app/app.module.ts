import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AsideMenuComponent } from './components/aside-menu/aside-menu.component';
import { HttpClientModule } from '@angular/common/http';
import { BookListComponent } from './components/book-list/book-list.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { SearchComponent } from './components/search/search.component';
import { BookDetailsComponent } from './components/book-details/book-details.component';
import { AuthorDetailsComponent } from './components/author-details/author-details.component';
import { UserListComponent } from './components/user-list/user-list.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatTableModule } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSortModule } from '@angular/material/sort';
import { UserDetailsComponent } from './components/user-details/user-details.component';
import { AbsoluteValuePipe } from './pipes/absolute-value.pipe';

const MAT_MODULES = [
  MatTableModule,
  MatFormFieldModule,
  MatInputModule,
  MatSortModule
];

@NgModule({
  declarations: [
    AppComponent,
    AsideMenuComponent,
    BookListComponent,
    NavBarComponent,
    SearchComponent,
    BookDetailsComponent,
    AuthorDetailsComponent,
    UserListComponent,
    UserDetailsComponent,
    AbsoluteValuePipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgbModule,
    BrowserAnimationsModule,
    MAT_MODULES
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
