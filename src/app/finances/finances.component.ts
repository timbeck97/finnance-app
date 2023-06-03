import { Component, TemplateRef, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { FormatterDirective } from '../util/formatter.directive';
import { CadatroContaComponent } from './cadatro-conta/cadatro-conta.component';
@Component({
  selector: 'app-finances',
  templateUrl: './finances.component.html',
  styleUrls: ['./finances.component.css']
})
export class FinancesComponent {

  formulario:FormGroup;
  conta:any;


  
  //referencia a outro componeente
  @ViewChild(CadatroContaComponent)
  cadastroConta:CadatroContaComponent | undefined;

  contas:any[] = [
    {descricao:'Xis campeiro', categoria:'LASER', valor: 25.00, formaPagamento:'CARTAO'},
    {descricao:'Cerveja BDL', categoria:'LASER', valor: 40.0, formaPagamento:'CARTAO'},
    {descricao:'Gasolina', categoria:'GASOLINA', valor: 80.00, formaPagamento:'CARTAO'},
    {descricao:'Kabum 1/4', categoria:'OUTROS', valor: 129.57, formaPagamento:'CARTAO'},
    {descricao:'Spotify', categoria:'LASER', valor: 33.50, formaPagamento:'PIX'},
    {descricao:'Whisky Aniver', categoria:'LASER', valor: 45.50, formaPagamento:'PIX'},
    {descricao:'Cerveja BDL', categoria:'LASER', valor: 30.00, formaPagamento:'PIX'},
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
  adicionaConta(conta:any){
    this.contas.push(conta);
  }
  editConta(conta:any){
    this.conta= conta;
    this.cadastroConta?.openCadastro();
    
  }
}
