import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FinancesComponent } from './finances.component';
import { ModalComponent } from '../modal/modal.component';
import { FormReactiveComponent } from '../study/components/form-reactive/form-reactive.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FormatterDirective } from '../util/formatter.directive';
import { CadatroContaComponent } from './cadatro-conta/cadatro-conta.component';
import { CardGastoComponent } from './card-gasto/card-gasto.component';
import { TableGastoComponent } from './table-gasto/table-gasto.component';
import { NgbCollapseModule } from '@ng-bootstrap/ng-bootstrap';
import { CurrencyMaskModule } from 'ng2-currency-mask';
import { CustomPipe } from '../util/custom.pipe';




@NgModule({
  declarations: [
    FinancesComponent,
    ModalComponent,
    FormatterDirective,
    CadatroContaComponent,
    CardGastoComponent,
    TableGastoComponent,
    CustomPipe
  ],
  exports: [],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NgbCollapseModule,
    CurrencyMaskModule
  ]
})
export class FinancesModule { }
