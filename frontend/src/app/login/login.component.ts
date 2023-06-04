import { Component } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {


  username: string='';
  password: string='';

  constructor() { }

  login() {
    // Lógica de autenticação aqui
    if (this.username === 'admin' && this.password === 'senha') {
      console.log('Login bem-sucedido!');
    } else {
      console.log('Login falhou!');
    }
  }
}
