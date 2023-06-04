import { Component } from '@angular/core';

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

  onMudaValor(evento:any){
    this.valorContadorPai=evento.novoValor;
  }
  onShowHide(){
    this.ngIfShowHide=!this.ngIfShowHide;
  }

}
