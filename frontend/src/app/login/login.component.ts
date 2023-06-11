import { Component } from '@angular/core';
import { AuthenticationService } from '../authentication/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {


  username: string='';
  password: string='';

  constructor(private auth:AuthenticationService) { }

  login() {
    this.auth.login(this.username, this.password)
    .subscribe(resp=>console.log(resp))
  }
}
