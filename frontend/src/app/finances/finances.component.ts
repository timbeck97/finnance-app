import { AfterViewInit, ChangeDetectorRef, Component, TemplateRef, ViewChild } from '@angular/core';
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
import { Util } from '../util/util';
import { media } from '../util/media';
@Component({
  selector: 'app-finances',
  templateUrl: './finances.component.html',
  styleUrls: ['./finances.component.css']
})
export class FinancesComponent implements AfterViewInit{

  formulario:FormGroup;
  conta:{};
  gastosFixos:ETipoGasto=ETipoGasto.FIXO;
  filtro:Filtro;

  gastosCartao:number=0;

  gastosPix:number=0;

  showGastosMensais:boolean=true;

  meses: any = Util.getMeses();
  anos: any = []
  
  isMobile:boolean=false;

  constructor(private service:CadatroContaService, private http: HttpClient,private cdRef: ChangeDetectorRef) {
    this.formulario = new FormGroup({
      descricao: new FormControl(null,Validators.required),
      valor: new FormControl(null,Validators.required),
      formaPagamento: new FormControl('CARTAO',Validators.required),
    })
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
  }
  ngAfterViewInit(): void {
      this.cdRef.detectChanges();
  }
  ngOnInit(){
    this.carregarGastosMensais();
    media('(max-width: 767px)').subscribe((matches) =>
      this.isMobile=matches
    );
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
  getAnoMes(){
    if(this.filtro){
      return this.filtro?.ano+'/'+this.filtro?.mes;
    }else{
      return '';
    }
  }
  onChange(value:any, tipo:string){
    switch(tipo){
      case 'ANO':
        this.filtro={
          ...this.filtro,
          ano:value
        };
        break;
      case 'MES':
        this.filtro={
          ...this.filtro,
          mes:value
        };
        break;
    }
    this.carregarGastosMensais();
  }
}
