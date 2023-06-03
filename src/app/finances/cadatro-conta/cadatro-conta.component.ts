import { Component, ElementRef, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ModalComponent } from 'src/app/modal/modal.component';

@Component({
  selector: 'app-cadatro-conta',
  templateUrl: './cadatro-conta.component.html',
  styleUrls: ['./cadatro-conta.component.css']
})
export class CadatroContaComponent {

  formulario: FormGroup;

  @ViewChild(ModalComponent)
  element: ModalComponent | undefined;
  @Output()
  save = new EventEmitter();

  @Input()
  conta: any;
  constructor() {
    this.formulario = new FormGroup({
      descricao: new FormControl(null, Validators.required),
      valor: new FormControl(null, Validators.required),
      formaPagamento: new FormControl('CARTAO', Validators.required),
      categoria: new FormControl('LASER', Validators.required),
    })
  }
  ngOnChanges(): void {
    this.formulario.patchValue(this.conta);
  }
  onSubmit() {
    if (this.formulario.valid) {
      this.save.emit(this.formulario.value);
      this.formulario.reset();
      this.closeCadastro();
    } else {
      //this.verificaValidacoesForm(this.formulario);
    }
  }
  openCadastro() {
    this.element?.openModal();
  }
  closeCadastro() {
    this.element?.closeModal();
  }
  verificaValidTouched(campo: string) {
    return !this.formulario.get(campo)?.valid && this.formulario.get(campo)?.touched;
  }

}
