import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FinancesComponent } from './finances.component';
import { ModalComponent } from '../modal/modal.component';
import { FormReactiveComponent } from '../study/components/form-reactive/form-reactive.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FormatterDirective } from '../util/formatter.directive';



@NgModule({
  declarations: [
    FinancesComponent,
    ModalComponent,
    FormatterDirective
  ],
  exports: [],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
  ]
})
export class FinancesModule { }
