import { Component, Input, TemplateRef, ViewChild } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';


@Component({
  // eslint-disable-next-line @angular-eslint/component-selector
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.css']
})
export class ModalComponent {

  modalRef?: BsModalRef;
  
  @ViewChild('template') 
  template: TemplateRef<any> | string = '';

  @Input()
  title:string = 'Modal'

  constructor(private modalService: BsModalService) {

  }
  openModal() {
    this.modalRef = this.modalService.show(
      this.template,
      Object.assign({}, { class: 'gray modal-md' })
    );
  }
  closeModal() {
    this.modalRef?.hide();
  }
}
