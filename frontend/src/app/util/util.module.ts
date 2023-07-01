import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CustomPipe } from './custom.pipe';
import { PaginationComponent } from './pagination/pagination.component';



@NgModule({
  declarations: [
    CustomPipe,
    PaginationComponent
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
