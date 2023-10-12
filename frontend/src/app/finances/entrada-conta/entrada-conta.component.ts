import { Component, Input } from '@angular/core';
import { CadastroEntradaContaService } from '../cadastro-entrada-conta/cadastro-entrada-conta.service';
import { Filtro } from '../model/Filtro';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Deposito } from '../model/Deposito';
import { take } from 'rxjs';
import { URL } from 'src/app/util/environment';

@Component({
  selector: 'app-entrada-conta',
  templateUrl: './entrada-conta.component.html',
  styleUrls: ['./entrada-conta.component.css']
})
export class EntradaContaComponent {


  @Input()
  filtro: Filtro

  depositos: Deposito[] | null = []

  loading:boolean=true;

  constructor(private service:CadastroEntradaContaService,private http: HttpClient){

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
    this.findDepositos()
  }
  ngOnChanges(param: any) {
    let anterior=param.filtro.previousValue;
    let atual=param.filtro.currentValue;
    if (anterior!==undefined && (anterior.ano!==atual.ano || anterior.mes!==atual.mes)) {
      this.findDepositos()
    }
  }
  findDepositos() {
    this.loading=true;
    let params = new HttpParams({
      fromObject: {
        anoMes: this.filtro.ano + this.filtro.mes,
        pageSize: this.filtro.pageSize,
        pageNumber: this.filtro.pageNumber
      }
    })
    this.http.get<Deposito[]>(URL + '/depositos', { params: params, observe: 'response' })
      .pipe(
        take(1)
      )
      .subscribe((result) => {
        this.depositos = result.body
        if(this.loading){
          this.loading=false;
        }
      })
  }
  adicionarConta(){
    this.service.openModal(null,this.findDepositos.bind(this));
  }
  onRowClick(row: any) {
    this.service.openModal(row, this.findDepositos.bind(this));
  }
}



