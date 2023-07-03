import { Component, TemplateRef, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { FormatterDirective } from '../util/formatter.directive';
import { CadatroContaComponent } from './cadatro-conta/cadatro-conta.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CadatroContaService } from './cadatro-conta/cadatro-conta.service';
import { HttpClient } from '@angular/common/http';
import { URL } from '../util/environment';
import { take } from 'rxjs';
import { Gasto } from './model/Gasto';
import { Filtro } from './model/Filtro';
import { ETipoGasto } from './model/ETipoGasto';
@Component({
  selector: 'app-finances',
  templateUrl: './finances.component.html',
  styleUrls: ['./finances.component.css']
})
export class FinancesComponent{

  formulario:FormGroup;
  conta:{};
  gastosFixos:ETipoGasto=ETipoGasto.FIXO;
  filtro:Filtro;

  gastosCartao:number=0;

  gastosPix:number=0;

  showGastosMensais:boolean=true;

  constructor(private service:CadatroContaService, private http: HttpClient) {
    this.formulario = new FormGroup({
      descricao: new FormControl(null,Validators.required),
      valor: new FormControl(null,Validators.required),
      formaPagamento: new FormControl('CARTAO',Validators.required),
    })
  }
  ngOnInit(){
    
  }
  carregarGastosMensais(){
    let anoMes=this.filtro.ano+this.filtro.mes;
    console.log(anoMes);
    this.http.get<any>(URL + '/gastos/mensal?anoMes='+anoMes)
      .pipe(
        take(1)
      )
      .subscribe((result) => {
       this.gastosCartao=result.totalCartao?result.totalCartao:'0';
       this.gastosPix=result.totalPix?result.totalPix:'0';
      })
  }
  verificaValidTouched(campo:string){
    return !this.formulario.get(campo)?.valid && this.formulario.get(campo)?.touched;
  }
  filtrar(filtro:Filtro){
    this.filtro=filtro;
    this.carregarGastosMensais();
    
  }
  getDataFiltro(){
    if(this.filtro){
      return this.filtro?.ano+'/'+this.filtro?.mes;
    }else{
      return '';
    }
  }

}
