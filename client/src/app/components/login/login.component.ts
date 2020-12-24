import { AuthenticationService } from '../../service/authentication.service';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';

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
              private authenticationService: AuthenticationService) { }

  ngOnInit(): void {
  }

  handleLogin() {
    this.authenticationService.authenticateCredentials(this.email, this.password).subscribe(
      successful => {
        this.router.navigate['books'];
        this.invalidCredentials = false;
      }, error => {
        this.invalidCredentials = true;
        console.log('error 2', error);
      }
    )
  }

}
