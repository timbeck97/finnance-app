import { Component } from '@angular/core';
import { AuthenticationService } from '../authentication/authentication.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  
  username: string='';
  password: string='';

  loginScreen:boolean=true;
  formulario: FormGroup;
  constructor(private auth:AuthenticationService) { 
    this.formulario = new FormGroup({
      name: new FormControl('', Validators.required),
      email: new FormControl('', Validators.required),
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
    })
  }

  login() {
    this.auth.login(this.username, this.password)
  }
  signIn(event:any){
    event.preventDefault();
    this.loginScreen=false;
  }
  verificaValidTouched(campo: string) {
    return !this.formulario.get(campo)?.valid && this.formulario.get(campo)?.touched;
  }
  confirmCreateUser(){
    if(!this.formulario.valid){
      alert('Preencha todos os campos');
    }else{
      this.auth.createUser(this.formulario.value);
    }
  }
}
