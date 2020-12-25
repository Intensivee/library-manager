import { UserService } from './user.service';
import { API_URL } from '../app.constants';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { JwtHelperService } from '@auth0/angular-jwt';
import { User } from '../models/user';
import { Observable } from 'rxjs';

export const JWT_TOKEN = 'token';
export const AUTHENTICATED_USER_ID = 'authenticatedUserId';
export const AUTHORIZATION_HEADER = 'Authorization';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http: HttpClient,
              private userService: UserService) { }


  public authenticateCredentials(email: string, password: string) {

    const jwtHelper = new JwtHelperService();

    return this.http.post<any>(`${API_URL}/authentication/login`, { email, password })
      .pipe(
        map(
          response => {
            const token = jwtHelper.decodeToken(response.token);
            sessionStorage.setItem(AUTHENTICATED_USER_ID, token.sub);
            sessionStorage.setItem(JWT_TOKEN, response.token);
            return response;
          }
        )
      );
  }

  getAuthenticatedUser(): Observable<User> {
      return this.userService.getUser(this.getAuthenticatedUserId())
  }

  getAuthenticatedUserId() {
    return +sessionStorage.getItem(AUTHENTICATED_USER_ID);
  }

  getAuthenticatedToken() {
    if (this.getAuthenticatedUserId()) {
      return sessionStorage.getItem(JWT_TOKEN);
    }
    return null;
  }

  logout() {
    sessionStorage.removeItem(AUTHENTICATED_USER_ID);
    sessionStorage.removeItem(JWT_TOKEN);
  }

}
