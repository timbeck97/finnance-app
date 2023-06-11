import { Injectable } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CadatroContaComponent } from './cadatro-conta.component';

@Injectable({
  providedIn: 'root'
})
export class CadatroContaService {

  constructor(private modalService: NgbModal) { }

  


  openModal(gasto:any|null){
    const modalRef=this.modalService.open(CadatroContaComponent);
    modalRef.componentInstance.gasto=gasto;
    modalRef.componentInstance.save.subscribe((e:any)=>console.log(e))

  }
}
