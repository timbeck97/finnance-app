import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-table-gasto',
  templateUrl: './table-gasto.component.html',
  styleUrls: ['./table-gasto.component.css']
})
export class TableGastoComponent {

  @Input() titulo: string = '';

  @Input() gastos: any[] = [];

  isCollapsed = true;
  constructor() { 
    console.log(this.gastos)
  }
}
