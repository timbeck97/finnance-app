import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CustomPipe } from './custom.pipe';
import { PaginationComponent } from './pagination/pagination.component';
import { ConfirmComponent } from './confirm/confirm.component';
import { AutocompleteComponent } from './autocomplete/autocomplete.component';
import { NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';
import { InputCompetenciaComponent } from './input-competencia/input-competencia.component';
import { FormsModule } from '@angular/forms';




@NgModule({
  declarations: [
    CustomPipe,
    PaginationComponent,
    ConfirmComponent,
    AutocompleteComponent,
    InputCompetenciaComponent
  ],
  imports: [
    CommonModule,
    NgbTooltipModule,
    FormsModule
    
  ],
  exports:[
    CustomPipe,
    PaginationComponent,
    AutocompleteComponent,
    InputCompetenciaComponent
  ]
})
export class UtilModule { }
