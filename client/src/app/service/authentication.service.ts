import { API_URL } from '../app.constants';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';

export const JWT_TOKEN = 'token';
export const AUTHENTICATED_USER = 'authenticatedUser';
export const AUTHORIZATION_HEADER = 'Authorization';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http: HttpClient) { }


  public authenticateCredentials(email: string, password: string) {

    console.log('xd');
    return this.http.post<any>(`${API_URL}/login`, { email, password })
      .pipe(
        map(
          response => {

            sessionStorage.setItem(AUTHENTICATED_USER, email);
            sessionStorage.setItem(JWT_TOKEN, response.token);
            return response;
          }, error => console.log('error')
        )
      );
  }

  getAuthenticatedUser() {
    return sessionStorage.getItem(AUTHENTICATED_USER);
  }

  getAuthenticatedToken() {
    if (this.getAuthenticatedUser()) {
      return sessionStorage.getItem(JWT_TOKEN);
    }
    return null;
  }

  logout() {
    sessionStorage.removeItem(AUTHENTICATED_USER);
    sessionStorage.removeItem(JWT_TOKEN);
  }

}
