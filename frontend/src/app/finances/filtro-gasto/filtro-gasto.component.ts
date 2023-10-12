import { Component, EventEmitter, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Util } from 'src/app/util/util';
import { Filtro } from '../model/Filtro';

@Component({
  selector: 'app-filtro-gasto',
  templateUrl: './filtro-gasto.component.html',
  styleUrls: ['./filtro-gasto.component.css']
})
export class FiltroGastoComponent {

  isCollapsed = true;
  meses: any = Util.getMeses();
  anos: any = []
  
  filtro:Filtro;

  @Output() eventFiltro = new EventEmitter<Filtro>();

  constructor() {
    
    
  }
  ngOnInit() {
    for (let i = 2020; i <= new Date().getFullYear(); i++) {
      this.anos.push(String(i));
    }
    let mes = new Date().getMonth() + 1;
    let mesString = mes < 10 ? '0' + mes : String(mes);
    
    this.filtro={
      pageNumber:1,
      pageSize:10,
      maxPages:1,
      ano:String(new Date().getFullYear()),
      mes:mesString
    }
    this.eventFiltro.emit(JSON.parse(JSON.stringify(this.filtro)));
    
  }
  filtrar(){
    this.filtro.pageNumber=1;
    this.eventFiltro.emit(JSON.parse(JSON.stringify(this.filtro)));
  }
}
