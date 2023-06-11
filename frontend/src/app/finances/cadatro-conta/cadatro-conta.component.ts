import { Component, ElementRef, EventEmitter, Input, OnDestroy, OnInit, Output, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { ModalComponent } from 'src/app/modal/modal.component';

@Component({
  selector: 'app-cadatro-conta',
  templateUrl: './cadatro-conta.component.html',
  styleUrls: ['./cadatro-conta.component.css']
})
export class CadatroContaComponent implements OnInit, OnDestroy {

  formulario: FormGroup;


  @Output()
  save = new EventEmitter();

  @Input()
  gasto: any;
  constructor(private modalRef: NgbActiveModal) {
    this.formulario = new FormGroup({
      descricao: new FormControl(null, Validators.required),
      valor: new FormControl(null, Validators.required),
      formaPagamento: new FormControl('CARTAO', Validators.required),
      categoria: new FormControl('LASER', Validators.required),
    })


  }
  ngOnInit(): void {
    if(this.gasto){
      this.formulario.patchValue(this.gasto);
    }
    console.log('iniciou componente cadastro de conta')
  }
  ngOnDestroy(): void {
    console.log('cadastro de conta destruiu')
  }

  onSubmit() {
    if (this.formulario.valid) {
      this.save.emit(this.formulario.value);
      this.formulario.reset();
      this.modalRef.close();


    } else {
      //this.verificaValidacoesForm(this.formulario);
    }
  }
  close(){
    console.log('closing')
    this.modalRef.dismiss();
  }
  verificaValidTouched(campo: string) {
    return !this.formulario.get(campo)?.valid && this.formulario.get(campo)?.touched;
  }

}
