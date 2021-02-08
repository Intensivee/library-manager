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
    return this.httpClient.get<UnwrapResponse>(`${API_URL}/users`).pipe(
      map(data => data._embedded.userDtoes)
    );
  }

  update(user: User): Observable<User> {
    return this.httpClient.put<User>(`${API_URL}/users/${user.id}`, user);
  }

  deleteById(user: User): Observable<any> {
    return this.httpClient.delete(`${API_URL}/users/${user.id}`);
  }

  getById(id: number): Observable<User> {
    return this.httpClient.get<User>(`${API_URL}/users/${id}`);
  }

  getByCopyId(id: number): Observable<User> {
    return this.httpClient.get<User>(`${API_URL}/users/search/findByCopyId/${id}`);
  }
}

interface UnwrapResponse {
  _embedded: {
    userDtoes: User[];
  };
}
