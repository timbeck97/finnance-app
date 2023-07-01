import { Pipe, PipeTransform } from '@angular/core';
import { ECategoria } from '../finances/model/ECategoria';
import { EPagamento } from '../finances/model/EPagamento';

@Pipe({
  name: 'CustomPipe'
})
export class CustomPipe implements PipeTransform {

  transform(value: any, ...args: unknown[]): any {
    if(args[0]=='categoria'){
      return this.formatCategoria(value);
    }else if(args[0]=='pagamento'){
      return this.formatPagameto(value);
    }else if(args[0]=='nomeUsuario'){
      return this.formatNomeIniciais(value);
    }
  }
  formatCategoria(categoria:ECategoria){
    let descricao;
    switch(categoria){
      case ECategoria.LASER: descricao='Laser'; break;
      case ECategoria.OUTROS: descricao='Outros';break;
      case ECategoria.GASOLINA: descricao='Gasolina';break;
      case ECategoria.SAUDE: descricao='Saúde';

    }
    return descricao;
  }
  formatPagameto(p:EPagamento){
    let descricao;
    switch(p){
      case EPagamento.CARTAO:descricao='Cartão'; break;
      case EPagamento.PIX: descricao='Pix'; break;
    }
    return descricao;
  }
  formatNomeIniciais(nome:string){
    let palavras=nome.split(' ');
    let novaPalavra='';
    for(let i=0;i<palavras.length;i++){
      let p=palavras[i];
      p=p.charAt(0).toUpperCase()+p.slice(1);
      novaPalavra=novaPalavra+" "+p;
    }
    return novaPalavra;
  }
}
