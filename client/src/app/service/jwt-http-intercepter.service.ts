import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class JwtHttpIntercepterService implements HttpInterceptor {

  constructor() { }


  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    const token = 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYSIsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJib29rOnJlYWQifSx7ImF1dGhvcml0eSI6InVzZXI6cmVhZCJ9LHsiYXV0aG9yaXR5IjoidXNlcjp3cml0ZSJ9LHsiYXV0aG9yaXR5IjoiUk9MRV9BRE1JTiJ9LHsiYXV0aG9yaXR5IjoiYm9vazp3cml0ZSJ9XSwiaWF0IjoxNjA4NzE4NzA0LCJleHAiOjE2MDkyODI4MDB9.H_hDVphZSekSYDrptWVur4ylYImUS1wsOFkpjLJjAdY';

    request = request.clone({
      setHeaders: {
        Authorization: token
      }
    });

    // pass request with headers to next httpHandler (sth like filters in spring security)
    return next.handle(request);
  }
}
