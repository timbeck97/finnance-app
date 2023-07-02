import { HttpClient } from '@angular/common/http';
import { Component, ElementRef, EventEmitter, Input, OnDestroy, OnInit, Output, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Util } from 'src/app/util/util';
import { Gasto } from '../model/Gasto';
import { Observable, take } from 'rxjs';
import { URL } from 'src/app/util/environment';
import { ETipoGasto } from '../model/ETipoGasto';

@Component({
  selector: 'app-cadatro-conta',
  templateUrl: './cadatro-conta.component.html',
  styleUrls: ['./cadatro-conta.component.css']
})
export class CadatroContaComponent{

  formulario: FormGroup;

  @Input() onSave:()=>void | null

  @Input()
  gasto: any;

  @Input()
  tipoGasto:ETipoGasto=ETipoGasto.VARIAVEL;
  

  constructor(private modalRef: NgbActiveModal, private http: HttpClient) {
    this.formulario = new FormGroup({
      descricao: new FormControl(null, Validators.required),
      valor: new FormControl(null, Validators.required),
      formaPagamento: new FormControl('CARTAO', Validators.required),
      categoria: new FormControl('LASER', Validators.required),
      tipoGasto: new FormControl(this.tipoGasto, Validators.required),
      data: new FormControl(Util.getDate(new Date()), Validators.required),
    })


  }
  ngOnInit(): void {
    if(this.gasto){
      this.formulario.patchValue(this.gasto);
    }else{
      this.formulario.get('tipoGasto')?.setValue(this.tipoGasto);
    }
  
  }
  saveGasto(){
    const url=URL;
    let request:Observable<Gasto> | null =null;
    if(this.gasto && this.gasto.id){
      request=this.http.put<Gasto>(url+'/gastos/'+this.gasto.id,this.formulario.value);
    }else{
      request=this.http.post<Gasto>(url+'/gastos',this.formulario.value);
    }
    request
    .pipe(take(1))
    .subscribe(result=>{
      this.formulario.reset();
      this.modalRef.close();
      console.log('on save: ',this.onSave);
      
      this.onSave();
 
      
    });
  }

  onSubmit() {
    if (this.formulario.valid) {
      this.saveGasto();

    } else {
      for(let x in this.formulario.controls){
        this.formulario.get(x)?.markAsTouched();
        this.formulario.get(x)?.markAsDirty(); 
        
      }
    }
  }
  close(){

    this.modalRef.dismiss();
  }
  verificaValidTouched(campo: string) {
    return !this.formulario.get(campo)?.valid && this.formulario.get(campo)?.touched;
  }

  isGastoVariavel(){
    return this.tipoGasto===ETipoGasto.VARIAVEL;
  }
}
