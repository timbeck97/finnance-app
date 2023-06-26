import { Component } from '@angular/core';
import { AuthenticationService } from './authentication/authentication.service';
import { User } from './authentication/model/User';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'angular-study';
  valorContadorPai:number=0;
  ngIfShowHide:boolean=false;
  menuSelecionado:string='STUDY';

  constructor(private auth:AuthenticationService, private router:Router){

  }
  onMudaValor(evento:any){
    this.valorContadorPai=evento.novoValor;
  }
  onShowHide(){
    this.ngIfShowHide=!this.ngIfShowHide;
  }
  logado(){
    return this.auth.isLogged();
  }
  logout(){
    this.auth.logout();
  }
  login(){
    this.router.navigate(['/login'])
  }
  getUserName():string{
     return this.auth.getUser()?this.auth.getUser().name:'';
  }

}
