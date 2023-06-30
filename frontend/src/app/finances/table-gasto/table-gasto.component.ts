import { Component, EventEmitter, Input, OnDestroy, Output, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CadatroContaService } from '../cadatro-conta/cadatro-conta.service';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Gasto } from '../model/Gasto';
import { take } from 'rxjs';
import { URL } from 'src/app/util/environment';
import { Util } from 'src/app/util/util';
import { Filtro } from '../model/Filtro';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AlertComponent } from 'src/app/util/alert/alert.component';
import { ETipoGasto } from '../model/ETipoGasto';

@Component({
  selector: 'app-table-gasto',
  templateUrl: './table-gasto.component.html',
  styleUrls: ['./table-gasto.component.css']
})
export class TableGastoComponent implements OnDestroy {

  gastos: Gasto[] | null = []

  @Input()
  filtro: Filtro

  @Input()
  titulo:string='Gastos Variáveis';

  @Input()
  tipoGasto:ETipoGasto=ETipoGasto.VARIAVEL;

  loading:boolean=true;

  url: string = URL;

  constructor(private cadastroContaService: CadatroContaService, private http: HttpClient, private modalService: NgbModal) {
    if(this.tipoGasto===ETipoGasto.FIXO){
      this.url+='/gastos/fixos'
    }else{
      this.url+='/gastos'
    }
  }
  ngOnInit() {
    // let mes = new Date().getMonth() + 1;
    // let mesString = mes < 10 ? '0' + mes : String(mes);
    // this.filtro = {
    //   pageNumber: 1,
    //   pageSize: 10,
    //   maxPages: 1,
    //   ano: String(new Date().getFullYear()),
    //   mes: mesString
    // }
    if(this.filtro!==undefined){
      this.findGastos()
    }
   
    
  }
  ngOnDestroy(): void {
    console.log('DESTROY')
  }
  ngOnChanges(param: any) {
    if (param.filtro.currentValue) {
      this.findGastos()
    }
  }
  findGastos() {
    this.loading=true;
    let params = new HttpParams({
      fromObject: {
        anoMes: this.filtro.ano + this.filtro.mes,
        pageSize: this.isGastoVariavel()?this.filtro.pageSize:99999,
        pageNumber: this.isGastoVariavel()?this.filtro.pageNumber:1,
        tipoGasto:this.tipoGasto
      }
    })
    this.http.get<Gasto[]>(URL + '/gastos', { params: params, observe: 'response' })
      .pipe(
        take(1)
      )
      .subscribe((result) => {
        this.filtro.maxPages = Math.ceil(Number(result.headers.get('X-Total-Count')) / this.filtro.pageSize);
        this.gastos = result.body
        if(this.loading){
          this.loading=false;
        }
      })
  }
  onRowClick(row: any) {
    this.cadastroContaService.openModal(row, this.tipoGasto, this.findGastos.bind(this))
  }
  adicionarConta() {
    this.cadastroContaService.openModal(null,this.tipoGasto, this.findGastos.bind(this))
  }
  generateReport() {
    const modalRef=this.modalService.open(AlertComponent);
    modalRef.componentInstance.titleText='Relatório';
    modalRef.componentInstance.bodyText='TODO: Generate report';
    modalRef.dismissed.subscribe(()=>{
      alert('DISMISSED')
    })
  }
  handleFiltro(filtro: Filtro) {
    this.filtro = filtro;
    this.findGastos();
  }
  isGastoVariavel(){
    return this.tipoGasto===ETipoGasto.VARIAVEL;
  }
}
