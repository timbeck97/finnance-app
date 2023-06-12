import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CadatroContaService } from '../cadatro-conta/cadatro-conta.service';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Gasto } from '../model/Gasto';
import { take } from 'rxjs';
import { URL } from 'src/app/util/environment';
import { Util } from 'src/app/util/util';

@Component({
  selector: 'app-table-gasto',
  templateUrl: './table-gasto.component.html',
  styleUrls: ['./table-gasto.component.css']
})
export class TableGastoComponent {

  formFiltro: FormGroup;

  titulo: string = '';

  gastos: Gasto[] | null = []

  gasto:{};

  pageNumber:number=1;
  pageSize:number=5;
  maxElements:number=1;
  maxPages:number=1;

  isCollapsed = true;

  meses: any = Util.getMeses();
  anos: any = []

  constructor(private cadastroContaService:CadatroContaService, private http:HttpClient) {
    for (let i = 2020; i <= new Date().getFullYear(); i++) {
      this.anos.push(String(i));
    }
    let mes = new Date().getMonth() + 1;
    let mesString = mes < 10 ? '0' + mes : String(mes);
    this.formFiltro = new FormGroup({
      ano: new FormControl(new Date().getFullYear(), Validators.required),
      mes: new FormControl(mesString, Validators.required),

    })
    console.log(this.formFiltro.get('ano'))
  }
  ngOnInit(){
    this.findGastos()
  }
  filtrar(){
    this.pageNumber=1;
    this.findGastos();
  }
  findGastos(){
    let params=new HttpParams({
      fromObject:{
        anoMes:this.formFiltro.get('ano')?.value+this.formFiltro.get('mes')?.value,
        pageSize:this.pageSize,
        pageNumber:this.pageNumber
      }
    })
    this.http.get<Gasto[]>(URL+'/gastos',{params:params, observe:'response'})
      .pipe(
        
        take(1)
      )
      .subscribe((result)=>{
        this.maxPages=Math.ceil(Number(result.headers.get('X-Total-Count'))/this.pageSize);
        this.gastos=result.body
      })
  }
  onRowClick(row: any) {
    this.gasto=row;
    this.cadastroContaService.openModal(row)
  }
  adicionarConta(){
    this.cadastroContaService.openModal(null)
  }
  handlePagination(next:boolean){
    if(next){
      if(this.pageNumber<this.maxPages){
        this.pageNumber++;
        this.findGastos();
      }
    }else{
      if(this.pageNumber>1){
        this.pageNumber--;
        this.findGastos();
      }
    }
  }
  selectPage(page:number){
    this.pageNumber=page;
    this.findGastos();
  }
  getPages(){
    let array=[];
    for(let i=1;i<=this.maxPages;i++){
      array.push(i)
    }
    return array;
  }

}
