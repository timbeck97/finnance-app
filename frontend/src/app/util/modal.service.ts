import { Injectable } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AlertComponent } from './alert/alert.component';

@Injectable({
  providedIn: 'root'
})
export class ModalService {

  constructor(private modalService: NgbModal) { }

  async openModal(title:string, text:string, callback?:()=>void) {
    this.modalService.dismissAll();
    const modalRef=this.modalService.open(AlertComponent);
    modalRef.componentInstance.titleText=title
    modalRef.componentInstance.bodyText=text
    if(callback){
      modalRef.dismissed.subscribe(callback)
    }
  }
}
