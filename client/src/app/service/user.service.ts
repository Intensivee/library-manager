import { API_URL } from '../app.constants';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) { }

  getAll(): Observable<User[]> {
    const url = `${API_URL}/users`;
    return this.httpClient.get<UnwrapResponse>(url).pipe(
      map(data => data._embedded.userDtoes)
    );
  }

  update(user: User): Observable<User> {
    const url = `${API_URL}/users/${user.id}`;
    return this.httpClient.put<User>(url, user);
  }

  deleteById(user: User): Observable<any> {
    const url = `${API_URL}/users/${user.id}`;
    return this.httpClient.delete(url);
  }

  getById(id: number): Observable<User> {
    const url = `${API_URL}/users/${id}`;
    return this.httpClient.get<User>(url);
  }

  getByCopyId(id: number): Observable<User> {
    const url = `${API_URL}/users/search/findByCopyId/${id}`;
    return this.httpClient.get<User>(url);
  }
}

interface UnwrapResponse {
  _embedded: {
    userDtoes: User[];
  };
}
