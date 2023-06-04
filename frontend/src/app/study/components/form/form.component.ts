import { Component } from '@angular/core';


@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent {

  pessoa: any = {
    nome: null,
    salario: 1000,
    email:'joao@hotmail.com',
    cpfCnpj:'88104328000107'
  }
  constructor(){

  }
  onSubmit(form:any) {
    console.log(form)
   
  }
}
