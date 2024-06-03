import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { AuthenticationService } from 'src/app/authentication/authentication.service';
import { UserComplete } from 'src/app/authentication/model/UserComplete';
import { Util } from 'src/app/util/util';
import { Encerramentos, Pagamento } from '../model/Encerramentos';
import { take } from 'rxjs';
import { URL } from 'src/app/util/environment';

@Component({
  selector: 'app-registro-mensal',
  templateUrl: './registro-mensal.component.html',
  styleUrls: ['./registro-mensal.component.css']
})
export class RegistroMensalComponent {

  meses: any = Util.getMeses();
  anos: any = []
  ano: string;
  mes: string;

  gastosFixos: string = '';
  gastosVariaveis: string = '';

  pagamento: Pagamento = {
    valor: 0,
    data: ''
  };
  salario: number = 0;
  constructor(private http: HttpClient) {
    for (let i = 2020; i <= new Date().getFullYear(); i++) {
      this.anos.push(String(i));
    }
    let mes = new Date().getMonth() + 1;
    let mesString = mes < 10 ? '0' + mes : String(mes);
    this.ano = String(new Date().getFullYear()),
    this.mes = mesString

    
  }
  ngOnInit() {
    this.carregaEncerramentosMensais();
    this.carregarSalario();

  }
  carregarSalario() {
    const url = URL;
    const anoMes = this.ano + this.mes;
    this.http.get<Pagamento>(url + '/pagamentos/' + anoMes)
      .pipe(take(1))
      .subscribe(result => {
        if(result){
          this.pagamento = result;
        }
      });
  
  }
  salvarSalario(){
    if(this.pagamento && this.pagamento.id){
      this.updateSalario();
    }else{
      this.postSalario();
    
    }
  }
  updateSalario() {
    const url = URL;
    this.http.put<Pagamento>(url + '/pagamentos/' + this.pagamento?.id , { valor: this.pagamento.valor})
      .pipe(take(1))
      .subscribe(result => {
          this.pagamento = result;
      });
  }
  postSalario() {
    const url = URL;
    const anoMes = this.ano + this.mes;
    this.http.post<Pagamento>(url + '/pagamentos' , { valor: this.pagamento.valor, data: anoMes})
      .pipe(take(1))
      .subscribe(result => {
        this.pagamento = result;
      });
  }
  carregaEncerramentosMensais() {
    const url = URL;
    const anoMes = this.ano + this.mes;
    this.http.get<Encerramentos>(url + '/encerramentoMensal/' + anoMes)
      .pipe(take(1))
      .subscribe(result => {
        this.gastosFixos = result.fixo;
        this.gastosVariaveis = result.variavel;

      });
  }
  handleChecked(tipo: string) {
    console.log(tipo);
    const url = URL;
    const anoMes = this.ano + this.mes;
    this.http.put<Encerramentos>(url + '/encerramentoMensal/' + anoMes + '/' + tipo, {})
      .pipe(take(1))
      .subscribe(result => {
        this.gastosFixos = result.fixo;
        this.gastosVariaveis = result.variavel;
      });

  }
  handleAnoMes() {
    this.carregaEncerramentosMensais();

  }
  changeValor(event: any) {
    console.log(event);

  }
  getLabel(tipo: string) {
    let dado = tipo === 'FIXO' ? this.gastosFixos : this.gastosVariaveis;
    switch (dado) {
      case 'ENCERRADO': return 'Encerrado';
      case 'NAO_ENCERRADO': return 'Encerrar';
      default: return 'Sem Gastos Cadastrados';
    }
  }
}
