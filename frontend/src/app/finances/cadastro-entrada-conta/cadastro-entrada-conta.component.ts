import { HttpClient } from '@angular/common/http';
import { Component, Input } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Util } from 'src/app/util/util';
import { GastoEntrada } from '../model/GastoEntrada';
import { Observable, take } from 'rxjs';
import { URL } from 'src/app/util/environment';
import { SelectOptions } from 'src/app/util/model/SelectOptions';

@Component({
  selector: 'app-cadastro-entrada-conta',
  templateUrl: './cadastro-entrada-conta.component.html',
  styleUrls: ['./cadastro-entrada-conta.component.css']
})
export class CadastroEntradaContaComponent {

  formulario: FormGroup;
  @Input() onSave:()=>void | null

  @Input()
  deposito: any;
  competencias:SelectOptions[]=[];

  constructor(private modalRef: NgbActiveModal, private http: HttpClient){
    this.formulario = new FormGroup({
      descricao: new FormControl(null, Validators.required),
      valor: new FormControl(null, Validators.required),
      data: new FormControl(Util.getCompetenciaAtual(), Validators.required),
      gastoVinculado:new FormControl(null)
    })
    this.competencias=Util.getCompetencia();
  }
  ngOnInit(): void {
    if(this.deposito){
      this.formulario.patchValue(this.deposito);
    }
  
  }
  saveDeposito(){
    const url=URL;
    let request:Observable<GastoEntrada> | null =null;
    if(this.deposito && this.deposito.id){
      request=this.http.put<GastoEntrada>(url+'/depositos/'+this.deposito.id,this.formulario.value);
    }else{
      request=this.http.post<GastoEntrada>(url+'/depositos',this.formulario.value);
    }
    request
    .pipe(take(1))
    .subscribe(result=>{
      this.formulario.reset();
      this.modalRef.close();
      this.onSave();
 
    });
  }
  onSubmit() {
    if (this.formulario.valid) {
      this.saveDeposito();
    } else {
      for(let x in this.formulario.controls){
        this.formulario.get(x)?.markAsTouched();
        this.formulario.get(x)?.markAsDirty(); 
        
      }
    }
  }
  getFormDataValue(){
    return this.formulario.get('data')?.value;
  }
  onAutoCompleteEvent(value:any){
    this.formulario.patchValue({
      gastoVinculado:value
    })
  }
  close(){

    this.modalRef.dismiss();
  }
  verificaValidTouched(campo: string) {
    return !this.formulario.get(campo)?.valid && this.formulario.get(campo)?.touched;
  }
  getGastoVinculado(){
    return this.formulario.get('gastoVinculado')?.value;
  }
}
