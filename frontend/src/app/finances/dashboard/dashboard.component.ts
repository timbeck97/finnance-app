import { Component } from '@angular/core';
import { multi, single } from './data';
import { media } from 'src/app/util/media';
import { Util } from 'src/app/util/util';
import { Periodo } from '../model/Periodo';
import { HttpClient } from '@angular/common/http';
import { DashboardMensal } from '../model/DashboardMensal';
import { take } from 'rxjs';
import { DashboardAnual } from '../model/DashboardAnual';
import { URL } from 'src/app/util/environment';
import { Serie } from '../model/Serie';
import { LinearChart } from '../model/LinearChart';
import { CustomPipe } from 'src/app/util/custom.pipe';
import { DecimalPipe } from '@angular/common';
import { PanelTotais } from '../model/PanelTotais';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
  barCharData: Serie[] = [];
  lineChartData: LinearChart[] = [];
  totalGasto: PanelTotais;
  totalReceita: PanelTotais;
  isMobile:boolean=false;
  tipoPeriodo:string='MENSAL';
  meses: any = Util.getMeses();
  anos: any = []
  filtro:Periodo;
  constructor(private http: HttpClient, private decimalPipe: DecimalPipe) {
    Object.assign(this, { single },{multi})
  }
  ngOnInit(){
    media('(max-width: 767px)').subscribe((matches) =>
      this.isMobile=matches
    );
    let mes = new Date().getMonth() + 1;
    let mesString = mes < 10 ? '0' + mes : String(mes);
    this.filtro={
      ano:String(new Date().getFullYear()),
      mes:mesString
    }
    for (let i = 2020; i <= new Date().getFullYear(); i++) {
      this.anos.push(String(i));
    }
    this.carregarDados();
  }
  carregarDados(){
    if(this.tipoPeriodo=='ANUAL'){
      this.http.get<DashboardAnual>(URL + `/dashboard/anual/${this.filtro.ano}`)
      .pipe(
        take(1)
      )
      .subscribe((result) => {
        this.barCharData = result.barChartData;
        this.lineChartData = result.lineChartDto;
        this.totalGasto = result.totalGasto;
        this.totalReceita = result.totalReceita;
      })
    }else{
      this.http.get<DashboardMensal>(URL + `/dashboard/mensal/${this.filtro.ano}/${this.filtro.mes}`)
      .pipe(
        take(1)
      )
      .subscribe((result) => {
        this.barCharData = result.barChartData;
        this.totalGasto = result.totalGasto;
        this.totalReceita = result.totalReceita;
      })
    }
    

  }
  onSelect(e:any){
    console.log(e);
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
    this.carregarDados();
  }
  changeTipoPeriodo(tipoPeriodo:string){
    this.tipoPeriodo=tipoPeriodo;
    this.carregarDados();
  }
  formatAnoMes(anoMes:string){
    return CustomPipe.prototype.transform(anoMes,'competencia');
  }
  formatDouble(value:number){
    return this.decimalPipe?this.decimalPipe.transform(value,'1.2-2'):value;
  }
}
