import { Component, EventEmitter, Input, Output } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-confirm',
  templateUrl: './confirm.component.html',
  styleUrls: ['./confirm.component.css']
})
export class ConfirmComponent {

  @Input()
  titleText:string

  @Input()
  msgConfirm:string

  @Output()
  onSelect:EventEmitter<boolean>=new EventEmitter<boolean>;


  constructor(private modalRef: NgbActiveModal){

  }
  onHandleSelect(option:boolean){
    this.onSelect.emit(option);
    this.modalRef.dismiss();
  }
  close(){
    this.modalRef.dismiss();
  }
}
