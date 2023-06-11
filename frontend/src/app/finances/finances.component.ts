import { Component, TemplateRef, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { FormatterDirective } from '../util/formatter.directive';
import { CadatroContaComponent } from './cadatro-conta/cadatro-conta.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CadatroContaService } from './cadatro-conta/cadatro-conta.service';
@Component({
  selector: 'app-finances',
  templateUrl: './finances.component.html',
  styleUrls: ['./finances.component.css']
})
export class FinancesComponent {

  formulario:FormGroup;
  conta:{};


  
  //referencia a outro componeente
  // @ViewChild(CadatroContaComponent)
  // cadastroConta:CadatroContaComponent | undefined;

 

  constructor(private service:CadatroContaService) {
    this.formulario = new FormGroup({
      descricao: new FormControl(null,Validators.required),
      valor: new FormControl(null,Validators.required),
      formaPagamento: new FormControl('CARTAO',Validators.required),
    })
  }
  verificaValidTouched(campo:string){
    return !this.formulario.get(campo)?.valid && this.formulario.get(campo)?.touched;
  }
 
  adicionaConta(){
   this.service.openModal(null); 
   
  }
}
