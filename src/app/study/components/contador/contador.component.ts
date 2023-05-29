import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'contador',
  templateUrl: './contador.component.html',
  styleUrls: ['./contador.component.css']
})
export class ContadorComponent implements OnInit {
 
  @Input()
  valor: any = 0;

  @Output()
  changeValue=new EventEmitter();
  ngOnInit(): void {
    console.log('criou componente');
    
  }
  add(){
    this.valor++;
    this.changeValue.emit({novoValor: this.valor});
  }
  sub(){
    this.valor--;
    this.changeValue.emit({novoValor: this.valor});
  }
  
}
