import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { AuthenticationService } from 'src/app/authentication/authentication.service';
import { UserComplete } from 'src/app/authentication/model/UserComplete';
import { Util } from 'src/app/util/util';
import { Encerramentos, Pagamento } from '../model/Encerramentos';
import { forkJoin, take } from 'rxjs';
import { URL } from 'src/app/util/environment';
import { ToastrService } from 'ngx-toastr';

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

  
  constructor(private http: HttpClient,private toastr: ToastrService) {
    for (let i = 2020; i <= new Date().getFullYear(); i++) {
      this.anos.push(String(i));
    }
    let mes = new Date().getMonth() + 1;
    let mesString = mes < 10 ? '0' + mes : String(mes);
    this.ano = String(new Date().getFullYear()),
    this.mes = mesString

    
  }
  ngOnInit() {
    this.getData().subscribe(([encerramentos, salario])=>{
      this.gastosFixos = encerramentos.fixo;
      this.gastosVariaveis = encerramentos.variavel;
      if(salario){
        this.pagamento = salario;
      }
    })

  }
  getData(){
    const $dadosMensais=this.carregaEncerramentosMensais();
    const $salario=this.carregarSalario();
    return forkJoin([$dadosMensais, $salario]);
  }

  carregarSalario() {
    const url = URL;
    const anoMes = this.ano + this.mes;
    return this.http.get<Pagamento>(url + '/pagamentos/' + anoMes)
    
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
          this.toastr.success('Salário alterado com sucesso!', undefined, {
            timeOut: 3000,
            progressBar: true
          });
      });
  }
  postSalario() {
    const url = URL;
    const anoMes = this.ano + this.mes;
    this.http.post<Pagamento>(url + '/pagamentos' , { valor: this.pagamento.valor, data: anoMes})
      .pipe(take(1))
      .subscribe(result => {
        this.pagamento = result;
        this.toastr.success('Salário salvo com sucesso!', undefined, {
          timeOut: 3000,
          progressBar: true
        });
      });
  }

  carregaEncerramentosMensais() {
    const url = URL;
    const anoMes = this.ano + this.mes;
    return this.http.get<Encerramentos>(url + '/encerramentoMensal/' + anoMes)
     
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
    //this.carregaEncerramentosMensais();
    this.getData().subscribe(([encerramentos, salario])=>{
      this.gastosFixos = encerramentos.fixo;
      this.gastosVariaveis = encerramentos.variavel;
      if(salario){
        this.pagamento = salario;
      }
    })
  }
  changeValor(event: any) {
    console.log(event);

  }
  getLabel(tipo: string) {
    let dado = tipo === 'FIXO' ? this.gastosFixos : this.gastosVariaveis;
    switch (dado) {
      case 'ENCERRADO': return 'Encerrado';
      case 'NAO_ENCERRADO': return 'Encerrar Mês';
      default: return 'Sem Gastos Cadastrados';
    }
  }
  getIcon(tipo: string) {
    let dado = tipo === 'FIXO' ? this.gastosFixos : this.gastosVariaveis;
    switch (dado) {
      case 'ENCERRADO': return 'bi bi-check-circle text-success';
      case 'NAO_ENCERRADO': return 'bi bi-exclamation-octagon text-danger';
      default: return 'bi bi-info-circle';
    }
  }
  getText(tipo: string) {
    let dado = tipo === 'FIXO' ? this.gastosFixos : this.gastosVariaveis;
    switch (dado) {
      case 'ENCERRADO': return 'Nenhum valor pendente';
      case 'NAO_ENCERRADO': return 'Encerramento pendente';
      default: return 'Nenhum gasto cadastrado';
    }
  }
  getTextColor(tipo: string) {
    let dado = tipo === 'FIXO' ? this.gastosFixos : this.gastosVariaveis;
    switch (dado) {
      case 'ENCERRADO': return 'text-success';
      case 'NAO_ENCERRADO': return 'text-danger';
      default: return '';
    }
  }
 
}
