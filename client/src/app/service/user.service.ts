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

  getUsers(): Observable<User[]> {
    return this.httpClient.get<UnwrapResponse>(`${API_URL}/users`).pipe(
      map(data => data._embedded.userDtoes)
    );
  }

  updateUser(user: User): Observable<User> {
    return this.httpClient.put<User>(`${API_URL}/users/${user.id}`, user);
  }

  deleteUser(user: User) {
    return this.httpClient.delete(`${API_URL}/users/${user.id}`);
  }

  getUser(id: number): Observable<User> {
    return this.httpClient.get<User>(`${API_URL}/users/${id}`);
  }

  getUserByCopyId(id: number): Observable<User> {
    return this.httpClient.get<User>(`${API_URL}/users/search/findByCopyId/${id}`);
  }
}

interface UnwrapResponse {
  _embedded: {
    userDtoes: User[];
  };
}
