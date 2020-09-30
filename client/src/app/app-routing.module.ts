import { SearchComponent } from './components/search/search.component';
import { BookListComponent } from './components/book-list/book-list.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {path: 'books/:id', component: BookListComponent},
  {path: 'books', component: BookListComponent},
  {path: 'search/:key', component: SearchComponent},  // no path without arg (if nothing passed -> default books page)
  {path: '', redirectTo: '/books', pathMatch: 'full'},
  {path: '**', redirectTo: '/books', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
