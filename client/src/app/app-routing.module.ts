import { UserListComponent } from './components/user-list/user-list.component';
import { AuthorDetailsComponent } from './components/author-details/author-details.component';
import { BookDetailsComponent } from './components/book-details/book-details.component';
import { SearchComponent } from './components/search/search.component';
import { BookListComponent } from './components/book-list/book-list.component';
import { NgModule, Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  { path: 'book/:id', component: BookDetailsComponent },
  { path: 'books/:id', component: BookListComponent },
  { path: 'books', component: BookListComponent },
  { path: 'author/:id', component: AuthorDetailsComponent},
  { path: 'search/:key', component: SearchComponent },
  { path: 'users', component: UserListComponent},
  { path: '', redirectTo: '/books', pathMatch: 'full' },
  { path: '**', redirectTo: '/books', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
