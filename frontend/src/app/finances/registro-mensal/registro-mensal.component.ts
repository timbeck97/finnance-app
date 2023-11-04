import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { AuthenticationService } from 'src/app/authentication/authentication.service';
import { UserComplete } from 'src/app/authentication/model/UserComplete';
import { Util } from 'src/app/util/util';
import { Encerramentos } from '../model/Encerramentos';
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

  }
  salvarSalario(){
    // const url = URL;
    // this.http.put<Number>(url + '/salario',{})
    //   .pipe(take(1))
    //   .subscribe(result => {
    //     alert('Salario salvo')
    //   });
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
  getLabel(tipo: string) {
    let dado = tipo === 'FIXO' ? this.gastosFixos : this.gastosVariaveis;
    switch (dado) {
      case 'ENCERRADO': return 'Encerrado';
      case 'NAO_ENCERRADO': return 'Encerrar';
      default: return 'Sem Gastos Cadastrados';
    }
  }
}
