
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router } from '@angular/router';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { LoginComponent } from '../../components/login/login.component';
import { AuthenticationService } from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class RoleGuardService {

  constructor(private authenticationService: AuthenticationService,
              private router: Router,
              private dialog: MatDialog) { }


  canActivate(route: ActivatedRouteSnapshot): boolean {

    // check's sessions storage and token expiration
    if(!this.authenticationService.isAuthenticated()){
      this.openLoginDialog();
      return false;
    }
    const hasAuthorization = this.authenticationService
                                  .getAuthenticatedUserAuthorities()
                                    .includes(route.data.expectedRole);

    if(hasAuthorization){
      return true;
    }

    this.router.navigate(['books']);
    return false;
  }

  openLoginDialog(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    this.dialog.open(LoginComponent, dialogConfig);
  }
}
