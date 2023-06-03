import { Component, TemplateRef } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { FormatterDirective } from '../util/formatter.directive';
@Component({
  selector: 'app-finances',
  templateUrl: './finances.component.html',
  styleUrls: ['./finances.component.css']
})
export class FinancesComponent {

  formulario:FormGroup;
  conta:any;

  contas:any[] = [
    {descricao:'Xis campeiro', categoria:'Laser', valor: 25.00, formaPagamento:'CARTAO'},
    {descricao:'Cerveja BDL', categoria:'Laser', valor: 40.0, formaPagamento:'CARTAO'},
    {descricao:'Gasolina', categoria:'Gasolina', valor: 80.00, formaPagamento:'CARTAO'},
    {descricao:'Kabum 1/4', categoria:'Outros', valor: 129.57, formaPagamento:'CARTAO'},
    {descricao:'Spotify', categoria:'Laser', valor: 33.50, formaPagamento:'PIX'},
    {descricao:'Whisky Aniver', categoria:'Laser', valor: 45.50, formaPagamento:'PIX'},
    {descricao:'Cerveja BDL', categoria:'Laser', valor: 30.00, formaPagamento:'PIX'},
  ]

  constructor() {
    this.formulario = new FormGroup({
      descricao: new FormControl(null,Validators.required),
      valor: new FormControl(null,Validators.required),
      formaPagamento: new FormControl('CARTAO',Validators.required),
    })
  }
  verificaValidTouched(campo:string){
    return !this.formulario.get(campo)?.valid && this.formulario.get(campo)?.touched;
  }
  setConta(conta:any){
    this.conta = conta;
  }
}
