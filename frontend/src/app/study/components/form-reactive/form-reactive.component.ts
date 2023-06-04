import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-form-reactive',
  templateUrl: './form-reactive.component.html',
  styleUrls: ['./form-reactive.component.css']
})
export class FormReactiveComponent {

  formulario:FormGroup;

  constructor(){
    this.formulario = new FormGroup({
      nome: new FormControl(null,Validators.required),
      email: new FormControl(null,[Validators.required, Validators.email]),
      salario: new FormControl(null,Validators.required),
      cpfCnpj: new FormControl(null,Validators.required),
    })
    
  }
  
  onSubmit(){

  }
  verificaValidTouched(campo:string){
    return !this.formulario.get(campo)?.valid && this.formulario.get(campo)?.touched;
  }
}
