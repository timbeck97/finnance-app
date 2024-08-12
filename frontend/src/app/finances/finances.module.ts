import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FinancesComponent } from './finances.component';
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
import { RegistroMensalComponent } from './registro-mensal/registro-mensal.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { CardDashboardComponent } from './card-dashboard/card-dashboard.component';



@NgModule({
  declarations: [
    FinancesComponent,
    FormatterDirective,
    CadatroContaComponent,
    CardGastoComponent,
    TableGastoComponent,
    DashboardComponent,
    EntradaContaComponent,
    FiltroGastoComponent,
    CadastroEntradaContaComponent,
    ConfiguracoesComponent,
    RegistroMensalComponent,
    DashboardComponent,
    CardDashboardComponent,
  ],
  exports: [],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NgbCollapseModule,
    CurrencyMaskModule,
    NgbTooltipModule,
    UtilModule,
    BrowserAnimationsModule,  
    NgxChartsModule  
    
  ]
})
export class FinancesModule { }
