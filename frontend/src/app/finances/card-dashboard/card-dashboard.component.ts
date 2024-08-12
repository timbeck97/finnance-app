import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-card-dashboard',
  templateUrl: './card-dashboard.component.html',
  styleUrls: ['./card-dashboard.component.css']
})
export class CardDashboardComponent {
  @Input()
  titulo:string='';

  @Input()
  saldo:number = 0;

  @Input()
  percentual:number = 0;

  @Input()
  periodo:string='';

  @Input()
  positivo:boolean;

  @Input()
  invertArrow:boolean=false;

  getPeriodo(){
    return this.periodo==='MENSAL'?'Mês':'Ano'
  }
  getArrow(){
    let obj={style:{}, classe:''}
    if(this.positivo){
      obj.style={'color':'#69ff24'}
      if(this.invertArrow){
        obj.classe='bi bi-arrow-down'
      }else{
        obj.classe='bi bi-arrow-up'
      }
    }else{
      obj.style={'color':'red'}
      if(this.invertArrow){
        obj.classe='bi bi-arrow-up'
      }else{
        obj.classe='bi bi-arrow-down'
      }
    }
    
    return obj;
  }
}
