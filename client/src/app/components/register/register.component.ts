import { Router } from '@angular/router';
import { AuthenticationService } from '../../service/authentication.service';
import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['../login/login.component.css', './register.component.css']
})
export class RegisterComponent implements OnInit {

  email: string;
  firstName: string;
  lastName: string;
  password: string;
  errorMessage = 'Niepoprawne dane!';
  invalidCredentials = false;

  constructor(private authService: AuthenticationService,
              private router: Router,
              public dialogRef: MatDialogRef<RegisterComponent>) { }

  ngOnInit(): void {
  }

  handleRegistration(): void {

    this.authService.registerUser(this.email, this.password, this.firstName, this.lastName).subscribe(
      successful => {
        this.router.navigate(['books']);
        this.invalidCredentials = false;
        this.dialogRef.close();
      }, error => {
        this.invalidCredentials = true;
      }
    );
  }
}
