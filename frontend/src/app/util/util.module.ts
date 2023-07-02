import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CustomPipe } from './custom.pipe';
import { PaginationComponent } from './pagination/pagination.component';
import { ConfirmComponent } from './confirm/confirm.component';
import { AutocompleteComponent } from './autocomplete/autocomplete.component';
import { NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';



@NgModule({
  declarations: [
    CustomPipe,
    PaginationComponent,
    ConfirmComponent,
    AutocompleteComponent
  ],
  imports: [
    CommonModule,
    NgbTooltipModule,
    
  ],
  exports:[
    CustomPipe,
    PaginationComponent,
    AutocompleteComponent
  ]
})
export class UtilModule { }
