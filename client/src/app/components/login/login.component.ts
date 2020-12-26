import { RegisterComponent } from '../register/register.component';
import { AuthenticationService } from '../../service/authentication.service';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MatDialogConfig } from '@angular/material/dialog';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  email: string;
  password: string;
  errorMessage = 'invalid Credentials!';
  invalidCredentials = false;

  constructor(private router: Router,
              private authenticationService: AuthenticationService,
              private dialog: MatDialog,
              public dialogRef: MatDialogRef<LoginComponent>) { }

  ngOnInit(): void {
  }

  handleLogin(): void {
    this.authenticationService.authenticateCredentials(this.email, this.password).subscribe(
      successful => {
        this.router.navigate(['books']);
        this.invalidCredentials = false;
        this.dialogRef.close();
      }, error => {
        this.invalidCredentials = true;
      }
    );
  }

  openRegisterDialog(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    this.dialog.open(RegisterComponent, dialogConfig);
  }

}
