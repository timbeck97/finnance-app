import { Component, EventEmitter, Input, OnChanges, OnDestroy, Output, ViewChild } from '@angular/core';
import { CadatroContaService } from '../cadatro-conta/cadatro-conta.service';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Gasto } from '../model/Gasto';
import { take } from 'rxjs';
import { URL } from 'src/app/util/environment';
import { Filtro } from '../model/Filtro';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AlertComponent } from 'src/app/util/alert/alert.component';
import { ETipoGasto } from '../model/ETipoGasto';
import { ConfirmComponent } from 'src/app/util/confirm/confirm.component';
import { media } from 'src/app/util/media';

@Component({
  selector: 'app-table-gasto',
  templateUrl: './table-gasto.component.html',
  styleUrls: ['./table-gasto.component.css']
})
export class TableGastoComponent implements OnDestroy, OnChanges {

  gastos: Gasto[] | null = []

  @Input()
  filtro: Filtro

  @Input()
  titulo:string='Gastos Variáveis';

  @Input()
  tipoGasto:ETipoGasto=ETipoGasto.VARIAVEL;

  loading:boolean=true;

  url: string = URL;

  @Output()
  callback: EventEmitter<void> = new EventEmitter<void>();

  isMobile:boolean=false;

  constructor(private cadastroContaService: CadatroContaService, private http: HttpClient, private modalService: NgbModal) {
    if(this.tipoGasto===ETipoGasto.FIXO){
      this.url+='/gastos/fixos'
    }else{
      this.url+='/gastos'
    }
  }
  ngOnInit() {
    //n precisa chamar aqui pois ele ja faz a chamada no componente de paginação
    // if(this.filtro!==undefined){
    //   this.findGastos()
    // }
    if(this.tipoGasto===ETipoGasto.FIXO){
      this.findGastos();
    }
    media('(max-width: 767px)').subscribe((matches) =>
      this.isMobile=matches
    );
   
  }
  ngOnDestroy(): void {
  
  }
  ngOnChanges(param: any) {
    let anterior=param.filtro.previousValue;
    let atual=param.filtro.currentValue;
    console.log(anterior);
    console.log(atual);
    
    if (anterior!==undefined && (anterior.ano!==atual.ano || anterior.mes!==atual.mes)) {
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
        this.callback && this.callback.emit()
      })
  }
  onRowClick(row: any) {
    this.cadastroContaService.openModal(row, this.tipoGasto, this.findGastos.bind(this))
  }
  adicionarConta() {
    this.cadastroContaService.openModal(null,this.tipoGasto, this.findGastos.bind(this))
  }
  handleCopiar(){
     const modalRef=this.modalService.open(ConfirmComponent);
     modalRef.componentInstance.titleText='Confirmar';
     modalRef.componentInstance.msgConfirm='Deseja copiar todos os registros de gastos fixos do mês anterior?';
     modalRef.componentInstance.onSelect.subscribe((resp:boolean)=>{
      if(resp){
        this.copiarRegistros()
      }
    })  
  }
  copiarRegistros(){
    this.http.post(URL+'/gastos/fixos/copiar', {})
    .pipe(
      take(1)
    )
    .subscribe(result=>{
     this.findGastos()
    })
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
    console.log('handle filtro table component');
    
    this.findGastos();
  }
  isGastoVariavel(){
    return this.tipoGasto===ETipoGasto.VARIAVEL;
  }
}
