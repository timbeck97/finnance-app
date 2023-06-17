import { Injectable } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CadastroEntradaContaComponent } from './cadastro-entrada-conta.component';

@Injectable({
  providedIn: 'root'
})
export class CadastroEntradaContaService {

  constructor(private modalService: NgbModal) { }

  


  openModal(deposito:any|null, callback:()=>void){
    const modalRef=this.modalService.open(CadastroEntradaContaComponent);
    modalRef.componentInstance.deposito=deposito;
    modalRef.componentInstance.onSave=callback

  }
}
