import { AuthenticationService } from './authentication.service';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class JwtHttpIntercepterService implements HttpInterceptor {

  constructor(private authenticationService: AuthenticationService) { }


  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    const token = this.authenticationService.getAuthenticatedToken();
    const user = this.authenticationService.getAuthenticatedUserId();

    if (user && token) {
      request = request.clone({
        setHeaders: {
          'Content-Type': 'application/json',
          Authorization: token
        }
      });
    }

    return next.handle(request);
  }
}
