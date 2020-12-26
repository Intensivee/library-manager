import { UserService } from './user.service';
import { HttpClient } from '@angular/common/http';
import { Injectable, Pipe } from '@angular/core';
import { map } from 'rxjs/operators';
import { JwtHelperService } from '@auth0/angular-jwt';
import { API_URL } from '../app.constants';

export const JWT_TOKEN = 'token';
export const AUTHENTICATED_USER_ID = 'authenticatedUserId';
export const AUTHORIZATION_HEADER = 'Authorization';
export const CURRENT_ROLE = 'currentRole';


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
            this.userService.getUser(token.sub).subscribe(( user => {
                sessionStorage.setItem(CURRENT_ROLE, String(user.role));
              })
            )
            return response;
          }
        )
      );
  }

  isAuthenticated(): boolean {
    const jwtHelper = new JwtHelperService();

    if( !this.getAuthenticatedToken() || !this.getAuthenticatedUserId()){
      return false;
    }

    return !jwtHelper.isTokenExpired(sessionStorage.getItem(JWT_TOKEN))
  }

  getAuthenticatedUserId() {
    return +sessionStorage.getItem(AUTHENTICATED_USER_ID);
  }

  getAuthenticatedToken() {

    return sessionStorage.getItem(JWT_TOKEN);

  }

  getUserRole(): number {
    if (this.isAuthenticated()) {

      return +sessionStorage.getItem(CURRENT_ROLE);
    }
    return -1;
  }

  getAuthenticatedUserAuthorities(): string[] {
    const token = this.getAuthenticatedToken();
    const jwtHelper = new JwtHelperService();
    const authoritiesDictionary = jwtHelper.decodeToken(token).authorities;

    const authorities = [];

    authoritiesDictionary.forEach(element => {
      authorities.push(element.authority);
    });
    return authorities;
  }

  logout(): void {
    sessionStorage.removeItem(AUTHENTICATED_USER_ID);
    sessionStorage.removeItem(JWT_TOKEN);
  }

}
