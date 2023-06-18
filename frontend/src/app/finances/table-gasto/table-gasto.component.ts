import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
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

@Component({
  selector: 'app-table-gasto',
  templateUrl: './table-gasto.component.html',
  styleUrls: ['./table-gasto.component.css']
})
export class TableGastoComponent {

  gastos: Gasto[] | null = []

  @Input()
  filtro: Filtro

  loading:boolean=true;

  constructor(private cadastroContaService: CadatroContaService, private http: HttpClient, private modalService: NgbModal) {

  }
  ngOnInit() {
    let mes = new Date().getMonth() + 1;
    let mesString = mes < 10 ? '0' + mes : String(mes);
    this.filtro = {
      pageNumber: 1,
      pageSize: 5,
      maxPages: 1,
      ano: String(new Date().getFullYear()),
      mes: mesString
    }
    this.findGastos()
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
        pageSize: this.filtro.pageSize,
        pageNumber: this.filtro.pageNumber
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
    this.cadastroContaService.openModal(row, this.findGastos.bind(this))
  }
  adicionarConta() {
    this.cadastroContaService.openModal(null, this.findGastos.bind(this))
  }
  handlePagination(next: boolean) {
    if (next) {
      if (this.filtro.pageNumber < this.filtro.maxPages) {
        this.filtro.pageNumber++;
        this.findGastos();
      }
    } else {
      if (this.filtro.pageNumber > 1) {
        this.filtro.pageNumber--;
        this.findGastos();
      }
    }
  }
  selectPage(page: number) {
    this.filtro.pageNumber = page;
    this.findGastos();
  }
  getPages() {
    let array = [];
    for (let i = 1; i <= this.filtro.maxPages; i++) {
      array.push(i)
    }
    return array;
  }
  handlePageSizeChange(add: boolean) {
    if (add) {
      this.filtro.pageSize++;
    } else {
      if (this.filtro.pageSize > 5) {
        this.filtro.pageSize--;
      }
    }
    this.filtro.pageNumber = 1;
    this.findGastos();

  }
  generateReport() {
    //alert('todo: GENERATE REPORT')
    const modalRef=this.modalService.open(AlertComponent);
    modalRef.componentInstance.titleText='Relatório';
    modalRef.componentInstance.bodyText='TODO: Generate report';
    modalRef.dismissed.subscribe(()=>{
      alert('DISMISSED')
    })
  }

}
