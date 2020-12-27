import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { JwtHelperService } from '@auth0/angular-jwt';
import {Observable} from 'rxjs';
import { API_URL } from 'src/app/app.constants';
import { UserService } from '../user.service';

export const JWT_TOKEN = 'token';
export const AUTHENTICATED_USER_ID = 'authenticatedUserId';
export const CURRENT_ROLE = 'currentRole';


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http: HttpClient,
              private userService: UserService,
              private router: Router) { }


  public authenticateCredentials(email: string, password: string): Observable<any> {

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
            );
            return response;
          }
        )
      );
  }

  public registerUser(email: string, password: string, firstName: string, lastName: string): Observable<any> {
    return this.http.post<any>(`${API_URL}/authentication/register`,
      {firstName, lastName, email, password});
  }

  isAuthenticated(): boolean {
    const jwtHelper = new JwtHelperService();

    if ( !this.getAuthenticatedToken() || !this.getAuthenticatedUserId()){
      return false;
    }
    return !jwtHelper.isTokenExpired(sessionStorage.getItem(JWT_TOKEN));
  }

  getAuthenticatedUserId(): number {
    return +sessionStorage.getItem(AUTHENTICATED_USER_ID);
  }

  getAuthenticatedToken(): string {

    return sessionStorage.getItem(JWT_TOKEN);
  }

  getUserRoleId(): number {
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
    sessionStorage.removeItem(CURRENT_ROLE);
    this.router.navigate(['books']);
  }

}
