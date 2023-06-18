import { Component, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.css']
})
export class AlertComponent {

  @Input()
  titleText:string

  @Input()
  bodyText:string

  constructor(private modalRef: NgbActiveModal){

  }

  close(){
    this.modalRef.dismiss();
    
  }
}
