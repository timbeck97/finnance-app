import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StylebindingComponent } from './components/stylebinding/stylebinding.component';
import { EventbindingComponent } from './components/eventbinding/eventbinding.component';
import { ContadorComponent } from './components/contador/contador.component';
import { NgswitchcaseComponent } from './components/diretives/ngswitchcase/ngswitchcase.component';
import { NgforComponent } from './components/diretives/ngfor/ngfor.component';
import { NgclassComponent } from './components/diretives/ngclass/ngclass.component';
import { NgcontentComponent } from './components/diretives/ngcontent/ngcontent.component';
import { BackgroundcolorDirective } from './components/diretives/backgroudcolor/backgroundcolor.directive';
import { PipesComponent } from './components/pipes/pipes.component';
import { CamelCasePipe } from './components/pipes/camel-case.pipe';
import { StudyComponent } from './study.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FormComponent } from './components/form/form.component';
import { FormReactiveComponent } from './components/form-reactive/form-reactive.component';
import { FormatterDirective } from '../util/formatter.directive';



@NgModule({
  declarations: [
    StylebindingComponent,
    EventbindingComponent,
    ContadorComponent,
    NgswitchcaseComponent,
    NgforComponent,
    NgclassComponent,
    NgcontentComponent,
    BackgroundcolorDirective,
    PipesComponent,
    CamelCasePipe,
    StudyComponent,
    FormComponent,
    FormReactiveComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
   
  ]
})
export class StudyModule { }
