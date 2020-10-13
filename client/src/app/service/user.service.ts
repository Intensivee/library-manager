import { map } from 'rxjs/operators';
import { API_URL } from '../app.constants';
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
    console.log(`${API_URL}/users/${user.id}`);
    return this.httpClient.put<User>(`${API_URL}/users/${user.id}`, user);
  }
}

interface UnwrapResponse {
  _embedded: {
    userDtoes: User[];
  };
}
