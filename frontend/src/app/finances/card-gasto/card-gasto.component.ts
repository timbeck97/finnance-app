import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-card-gasto',
  templateUrl: './card-gasto.component.html',
  styleUrls: ['./card-gasto.component.css']
})
export class CardGastoComponent {

    @Input()
    titulo:string='';

    @Input()
    saldo:number = 0;

    @Input()
    infoRight:string;

  
    constructor() {
        
     }
}
