import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CadatroContaComponent } from '../cadatro-conta/cadatro-conta.component';

@Component({
  selector: 'app-table-gasto',
  templateUrl: './table-gasto.component.html',
  styleUrls: ['./table-gasto.component.css']
})
export class TableGastoComponent {

    //referencia a outro componeente
    @ViewChild(CadatroContaComponent)
    cadastroConta:CadatroContaComponent | undefined;

  formFiltro: FormGroup;

  titulo: string = '';

  gastos: any[] = [
    {descricao:'Xis campeiro', categoria:'LASER', valor: 25.00, formaPagamento:'CARTAO'},
    {descricao:'Cerveja BDL', categoria:'LASER', valor: 40.0, formaPagamento:'CARTAO'},
    {descricao:'Gasolina', categoria:'GASOLINA', valor: 80.00, formaPagamento:'CARTAO'},
    {descricao:'Kabum 1/4', categoria:'OUTROS', valor: 129.57, formaPagamento:'CARTAO'},
    {descricao:'Spotify', categoria:'LASER', valor: 33.50, formaPagamento:'PIX'},
    {descricao:'Whisky Aniver', categoria:'LASER', valor: 45.50, formaPagamento:'PIX'},
    {descricao:'Cerveja BDL', categoria:'LASER', valor: 30.00, formaPagamento:'PIX'},
  ]

  gasto:{};

  isCollapsed = true;

  meses: any = [
    { nome: 'Janeiro', valor: '01' },
    { nome: 'Fevereiro', valor: '02' },
    { nome: 'Março', valor: '03' },
    { nome: 'Abril', valor: '04' },
    { nome: 'Maio', valor: '05' },
    { nome: 'Junho', valor: '06' },
    { nome: 'Julho', valor: '07' },
    { nome: 'Agosto', valor: '08' },
    { nome: 'Setembro', valor: '09' },
    { nome: 'Outubro', valor: '10' },
    { nome: 'Novembro', valor: '11' },
    { nome: 'Dezembro', valor: '12' }

  ]
  anos: any = []

  constructor() {
    for (let i = 2020; i <= new Date().getFullYear(); i++) {
      this.anos.push(String(i));
    }
    let mes = new Date().getMonth() + 1;
    let mesString = mes < 10 ? '0' + mes : String(mes);
    this.formFiltro = new FormGroup({
      ano: new FormControl(new Date().getFullYear(), Validators.required),
      mes: new FormControl(mesString, Validators.required),

    })
  }
  onRowClick(row: any) {
    this.gasto=row;
    this.cadastroConta?.openCadastro();
  }
  adicionaConta(gasto:any){
    this.gastos.push(gasto);
  }
  editConta(conta:any){
    this.gasto= conta;
    this.cadastroConta?.openCadastro();
  }
  openModalAdicionalConta(){
    this.gasto={
      descricao:'',
      valor:0,
      categoria:'LASER',
      classificacao:'CARTAO'
    };
    this.cadastroConta?.openCadastro()
  }
}
