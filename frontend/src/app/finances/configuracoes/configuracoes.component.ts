import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthenticationService } from 'src/app/authentication/authentication.service';
import { take } from 'rxjs';
import { User } from 'src/app/authentication/model/User';
import { URL } from 'src/app/util/environment';
import { UserComplete } from 'src/app/authentication/model/UserComplete';
import { Util } from 'src/app/util/util';
import { Encerramentos } from '../model/Encerramentos';

@Component({
  selector: 'app-configuracoes',
  templateUrl: './configuracoes.component.html',
  styleUrls: ['./configuracoes.component.css']
})
export class ConfiguracoesComponent {

  formulario: FormGroup;
  formularioAlterarSenha: FormGroup;
  user: UserComplete;
  alterarSenha: boolean = false;

  meses: any = Util.getMeses();
  anos: any = []
  ano:string;
  mes:string;

  gastosFixos:string='';
  gastosVariaveis:string='';

  saldoContaCorrente: number = 0;

  constructor(private auth: AuthenticationService, private http: HttpClient) {
    this.formulario = new FormGroup({
      id: new FormControl('', Validators.required),
      name: new FormControl('', Validators.required),
      email: new FormControl('', Validators.required),
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
    })
    this.formularioAlterarSenha = new FormGroup({
      oldPassword: new FormControl('', Validators.required),
      newPassword: new FormControl('', Validators.required),
      newPassword2: new FormControl('', Validators.required),

    })
    for (let i = 2020; i <= new Date().getFullYear(); i++) {
      this.anos.push(String(i));
    }
    let mes = new Date().getMonth()+1;
    let mesString = mes < 10 ? '0' + mes : String(mes);
    this.ano=String(new Date().getFullYear()),
    this.mes=mesString

  }
  ngOnInit() {
    this.user = this.auth.getUser();
    this.formulario.patchValue(this.user);
    this.carregaEncerramentosMensais();
    this.carregarSaldoContacorrente();

  }

  salvarSenha() {
    if (this.formularioAlterarSenha.valid) {
      if (this.formularioAlterarSenha.get('newPassword')?.value !== this.formularioAlterarSenha.get('newPassword2')?.value) {
        alert('As novas senhas devem ser iguais')
      } else {
        this.auth.alterarSenha(this.formularioAlterarSenha.value)
      }
    } else {
      alert('Preencha todos campos')
    }
  }
  salvarUsuario() {
    const url = URL;
    this.http.put<UserComplete>(url + '/users', this.formulario.value)
      .pipe(take(1))
      .subscribe(result => {
        this.auth.setUser(result)
        alert('Dados alterados com sucesso')
      });

  }
  carregarSaldoContacorrente() {
    const url = URL;
    this.http.get<any>(url + '/encerramentoMensal/saldo')
      .pipe(take(1))
      .subscribe(result => {
        console.log('saldo: '+result);
        this.saldoContaCorrente = result;
      });
  }
  verificaValidTouched(campo: string) {
    return !this.formulario.get(campo)?.valid && this.formulario.get(campo)?.touched;
  }
  verificaValidTouchedChangePassword(campo: string) {
    return !this.formularioAlterarSenha.get(campo)?.valid && this.formularioAlterarSenha.get(campo)?.touched;
  }
  carregaEncerramentosMensais(){
    const url = URL;
    const anoMes=this.ano+this.mes;
    this.http.get<Encerramentos>(url + '/encerramentoMensal/'+anoMes)
      .pipe(take(1))
      .subscribe(result => {
        this.gastosFixos=result.fixo;
        this.gastosVariaveis=result.variavel;

      });
  }
  handleChecked(tipo:string){
    console.log(tipo);
    const url = URL;
    const anoMes=this.ano+this.mes;
    this.http.put<Encerramentos>(url + '/encerramentoMensal/'+anoMes+'/'+tipo,{})
      .pipe(take(1))
      .subscribe(result => {
        this.gastosFixos=result.fixo;
        this.gastosVariaveis=result.variavel;
        this.carregarSaldoContacorrente();
      });
    
  }
  handleAnoMes(){
    this.carregaEncerramentosMensais();
    
  }
  getLabel(tipo:string){
    let dado=tipo==='FIXO'?this.gastosFixos:this.gastosVariaveis;
    switch(dado){
      case 'ENCERRADO': return 'Encerrado';
      case 'NAO_ENCERRADO': return 'Encerrar';
      default: return 'Sem Gastos Cadastrados';
    }
  }
}
