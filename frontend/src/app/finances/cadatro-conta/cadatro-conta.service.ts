import { Injectable } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CadatroContaComponent } from './cadatro-conta.component';
import { ETipoGasto } from '../model/ETipoGasto';

@Injectable({
  providedIn: 'root'
})
export class CadatroContaService {

  constructor(private modalService: NgbModal) { }


  openModal(gasto:any|null,tipoGasto:ETipoGasto, callback:()=>void){
    const modalRef=this.modalService.open(CadatroContaComponent);
    modalRef.componentInstance.gasto=gasto;
    modalRef.componentInstance.onSave=callback
    modalRef.componentInstance.tipoGasto=tipoGasto;

  }
}
