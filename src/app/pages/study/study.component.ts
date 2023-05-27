import { Component } from '@angular/core';

@Component({
  selector: 'app-study',
  templateUrl: './study.component.html',
  styleUrls: ['./study.component.css']
})
export class StudyComponent {

  title = 'angular-study';
  valorContadorPai:number=0;
  ngIfShowHide:boolean=false;
  onMudaValor(evento:any){
    this.valorContadorPai=evento.novoValor;
  }
  onShowHide(){
    this.ngIfShowHide=!this.ngIfShowHide;
  }
}
