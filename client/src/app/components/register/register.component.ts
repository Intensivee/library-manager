import { LoginComponent } from '../login/login.component';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../service/security/authentication.service';
import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';

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
  errorMessage: string;
  isValid = false;

  constructor(private authService: AuthenticationService,
              private router: Router,
              private dialog: MatDialog,
              public dialogRef: MatDialogRef<RegisterComponent>) { }

  ngOnInit(): void {
  }

  onSubmit(): void {
    if (this.validateData()){
      this.attemptToRegister();
    }
  }

  attemptToRegister(): void {
    this.authService.registerUser(this.email, this.password, this.firstName, this.lastName).subscribe(
      () => {
        this.handleSuccessfulRegistration();
      }, () => {
        this.handleFailedRegistration();
      }
    );
  }

  handleSuccessfulRegistration(): void {
    this.router.navigate(['books']);
    this.isValid = false;
    this.dialogRef.close();
    this.dialog.open(LoginComponent);
  }

  handleFailedRegistration(): void {
    this.isValid = false;
    this.errorMessage = 'email already exists.';
  }

  validateData(): boolean {
    this.isValid = true;
    this.errorMessage = null;

    if (!this.areInputsFilled()) {
      this.errorMessage = 'Not all inputs are filled!';
    }
    else if (!this.isFirstNameProperLength()) {
      this.errorMessage = 'first name must be between 2 and 20 characters!';
    }
    else if (!this.isLastNameProperLength()) {
      this.errorMessage = 'Last name must be between 2 and 30 characters!';
    }
    else if (!this.isEmailValid()) {
      this.errorMessage = 'Email is not valid!';
    }
    else if (!this.isPasswordProperLength()) {
      this.errorMessage = 'Password must be between 5 and 100 characters';
    }
    if (this.errorMessage != null) {
      this.isValid = false;
    }
    return this.isValid;
  }

  isFirstNameProperLength(): boolean {
    return this.firstName.length < 20 && this.firstName.length > 2;
  }

  isLastNameProperLength(): boolean {
    return this.lastName.length < 30 && this.lastName.length > 2;
  }

  isPasswordProperLength(): boolean {
    return this.password.length < 20 && this.password.length > 5;
  }

  areInputsFilled(): boolean {
    return !(this.email == null ||
      this.firstName == null ||
      this.lastName == null ||
      this.password == null);
  }

  isEmailValid(): boolean {
    const regex = new RegExp('^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$');
    return regex.test(this.email);
  }
}
