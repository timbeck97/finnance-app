import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-table-gasto',
  templateUrl: './table-gasto.component.html',
  styleUrls: ['./table-gasto.component.css']
})
export class TableGastoComponent {

  formFiltro: FormGroup;

  @Input() titulo: string = '';

  @Input() gastos: any[] = [];

  isCollapsed = true;

  @Output()
  clickRow = new EventEmitter();

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
    this.clickRow.emit(row);
  }
}
