import { UserDetailsComponent } from './components/user-details/user-details.component';
import { UserListComponent } from './components/user-list/user-list.component';
import { AuthorDetailsComponent } from './components/author-details/author-details.component';
import { BookDetailsComponent } from './components/book-details/book-details.component';
import { SearchComponent } from './components/search/search.component';
import { BookListComponent } from './components/book-list/book-list.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  { path: 'books/:id', component: BookDetailsComponent },
  { path: 'books/category/:id', component: BookListComponent },
  { path: 'books', component: BookListComponent },
  { path: 'users/:id', component: UserDetailsComponent},
  { path: 'users', component: UserListComponent},
  { path: 'author/:id', component: AuthorDetailsComponent},
  { path: 'search/:key', component: SearchComponent },
  { path: '', redirectTo: '/books', pathMatch: 'full' },
  { path: '**', redirectTo: '/books', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
