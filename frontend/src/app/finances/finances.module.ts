import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FinancesComponent } from './finances.component';
import { FormReactiveComponent } from '../study/components/form-reactive/form-reactive.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FormatterDirective } from '../util/formatter.directive';
import { CadatroContaComponent } from './cadatro-conta/cadatro-conta.component';
import { CardGastoComponent } from './card-gasto/card-gasto.component';
import { TableGastoComponent } from './table-gasto/table-gasto.component';
import { NgbCollapseModule, NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';
import { CurrencyMaskModule } from 'ng2-currency-mask';
import { CustomPipe } from '../util/custom.pipe';
import { EntradaContaComponent } from './entrada-conta/entrada-conta.component';
import { FiltroGastoComponent } from './filtro-gasto/filtro-gasto.component';
import { CadastroEntradaContaComponent } from './cadastro-entrada-conta/cadastro-entrada-conta.component';
import { PaginationComponent } from '../util/pagination/pagination.component';
import { UtilModule } from '../util/util.module';
import { ConfiguracoesComponent } from './configuracoes/configuracoes.component';



@NgModule({
  declarations: [
    FinancesComponent,
    FormatterDirective,
    CadatroContaComponent,
    CardGastoComponent,
    TableGastoComponent,

    EntradaContaComponent,
    FiltroGastoComponent,
    CadastroEntradaContaComponent,
    ConfiguracoesComponent,
  ],
  exports: [],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NgbCollapseModule,
    CurrencyMaskModule,
    NgbTooltipModule,
    UtilModule
    
    
  ]
})
export class FinancesModule { }
