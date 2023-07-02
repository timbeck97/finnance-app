import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CustomPipe } from './custom.pipe';
import { PaginationComponent } from './pagination/pagination.component';
import { ConfirmComponent } from './confirm/confirm.component';



@NgModule({
  declarations: [
    CustomPipe,
    PaginationComponent,
    ConfirmComponent
  ],
  imports: [
    CommonModule
  ],
  exports:[
    CustomPipe,
    PaginationComponent
  ]
})
export class UtilModule { }
