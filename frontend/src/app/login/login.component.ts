import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { AuthenticationService } from '../authentication/authentication.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  
  username: string='';
  password: string='';

  //criarConta:boolean=false;

  loginScreen:boolean=true;
  formulario: FormGroup;
  constructor(private auth:AuthenticationService, private route: ActivatedRoute, private router:Router) { 
    this.formulario = new FormGroup({
      name: new FormControl('', Validators.required),
      email: new FormControl('', Validators.required),
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
    })
  }
  ngOnInit(): void {
    this.loginScreen=this.route.snapshot.params['novo']?false:true;

  }

  login() {
    this.auth.login(this.username, this.password)
  }
  signIn(event:any){
    event.preventDefault();
    this.router.navigate(['/login', 'novo']);
  }
  voltar(){
    this.router.navigate(['/login']);
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
