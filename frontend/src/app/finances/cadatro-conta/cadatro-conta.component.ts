import { HttpClient } from '@angular/common/http';
import { Component, ElementRef, EventEmitter, Input, OnDestroy, OnInit, Output, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Util } from 'src/app/util/util';
import { Gasto } from '../model/Gasto';
import { take } from 'rxjs';
import { URL } from 'src/app/util/environment';

@Component({
  selector: 'app-cadatro-conta',
  templateUrl: './cadatro-conta.component.html',
  styleUrls: ['./cadatro-conta.component.css']
})
export class CadatroContaComponent{

  formulario: FormGroup;


  @Output()
  save = new EventEmitter();

  @Input()
  gasto: any;
  constructor(private modalRef: NgbActiveModal, private http: HttpClient) {
    this.formulario = new FormGroup({
      descricao: new FormControl(null, Validators.required),
      valor: new FormControl(null, Validators.required),
      formaPagamento: new FormControl('CARTAO', Validators.required),
      categoria: new FormControl('LASER', Validators.required),
      data: new FormControl(Util.getDate(new Date()), Validators.required),
    })


  }
  ngOnInit(): void {
    if(this.gasto){
      this.formulario.patchValue(this.gasto);
    }
  
  }
  saveGasto(){
    const url=URL;
     this.http.post<Gasto>(url+'/gastos',this.formulario.value)
    .pipe(take(1))
    .subscribe(result=>{
      this.formulario.reset();
      this.modalRef.close();
 
      
    });
  }

  onSubmit() {
    if (this.formulario.valid) {
      //this.save.emit(this.formulario.value);
      this.saveGasto();

    } else {
      //this.verificaValidacoesForm(this.formulario);
    }
  }
  close(){

    this.modalRef.dismiss();
  }
  verificaValidTouched(campo: string) {
    return !this.formulario.get(campo)?.valid && this.formulario.get(campo)?.touched;
  }

}
