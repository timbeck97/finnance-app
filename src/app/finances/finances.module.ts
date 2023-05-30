import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FinancesComponent } from './finances.component';
import { ModalComponent } from '../modal/modal.component';
import { FormReactiveComponent } from '../study/components/form-reactive/form-reactive.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FormatterDirective } from '../util/formatter.directive';
import { CadatroContaComponent } from './cadatro-conta/cadatro-conta.component';



@NgModule({
  declarations: [
    FinancesComponent,
    ModalComponent,
    FormatterDirective,
    CadatroContaComponent
  ],
  exports: [],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
  ]
})
export class FinancesModule { }
